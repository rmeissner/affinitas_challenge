package de.thegerman.affinitaschallenge.login

import android.content.Context
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.repositories.UserRepository
import de.thegerman.affinitaschallenge.repositories.models.User
import de.thegerman.affinitaschallenge.utils.ImmediateSchedulersRule
import de.thegerman.affinitaschallenge.utils.TestObservable
import de.thegerman.onekay.core.base.ContextProxy
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import org.mockito.Mockito.`when` as given

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @JvmField
    @Rule
    val rule = ImmediateSchedulersRule()

    @Mock
    lateinit var proxy: ContextProxy

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var repository: UserRepository

    private val userObservable = TestObservable<User>()

    @Before
    fun setup() {
        given(repository.login(anyString(), anyString())).thenReturn(userObservable.get())
        given(context.getString(R.string.invalid_email)).thenReturn("Invalid email")
        given(context.getString(R.string.password_too_short)).thenReturn("Password too short")
        given(context.getString(R.string.unknown_error)).thenReturn("Unknown error")
    }

    @After
    fun tearDown() {
        userObservable.complete()
    }

    @Test
    fun loginInvalidUsername() {
        val loadingObserver = TestObserver.create<Boolean>()
        val notificationObserver = TestObserver.create<String>()

        val viewModel = LoginViewModel(proxy, context, repository)
        viewModel.observeNotifications().subscribe(notificationObserver)
        viewModel.observeLoading().subscribe(loadingObserver)

        viewModel.login("test@examp", "12345678")

        // We should show a notification that it is an invalid email
        notificationObserver.assertValue("Invalid email")

        // We should show the lading spinner while validating
        loadingObserver.assertValues(true, false)

        // We should only finish the activity when it was successful
        verifyNoMoreInteractions(proxy)
    }

    @Test
    fun loginPasswordTooShort() {
        val loadingObserver = TestObserver.create<Boolean>()
        val notificationObserver = TestObserver.create<String>()

        val viewModel = LoginViewModel(proxy, context, repository)
        viewModel.observeNotifications().subscribe(notificationObserver)
        viewModel.observeLoading().subscribe(loadingObserver)

        viewModel.login("test@example.com", "1234567")

        // We should show a notification that the password is too short
        notificationObserver.assertValue("Password too short")

        // We should show the lading spinner while validating
        loadingObserver.assertValues(true, false)

        // We should only finish the activity when it was successful
        verifyNoMoreInteractions(proxy)
    }

    @Test
    fun loginNetworkError() {
        val loadingObserver = TestObserver.create<Boolean>()
        val notificationObserver = TestObserver.create<String>()

        val viewModel = LoginViewModel(proxy, context, repository)
        viewModel.observeNotifications().subscribe(notificationObserver)
        viewModel.observeLoading().subscribe(loadingObserver)

        viewModel.login("test@example.com", "12345678")
        userObservable.error(Exception("Some exception"))

        // We should show a notification that there was an unexpected error
        notificationObserver.assertValue("Unknown error")

        // We should show the lading spinner while performing the request
        loadingObserver.assertValues(true, false)

        // We should only finish the activity when it was successful
        verifyNoMoreInteractions(proxy)
    }

    @Test
    fun loginSuccess() {
        val loadingObserver = TestObserver.create<Boolean>()
        val notificationObserver = TestObserver.create<String>()

        val viewModel = LoginViewModel(proxy, context, repository)
        viewModel.observeNotifications().subscribe(notificationObserver)
        viewModel.observeLoading().subscribe(loadingObserver)

        viewModel.login("test@example.com", "12345678")
        userObservable.accept(User("test_email@example.com", "test_name", "1, 2"))

        // There should be no error message
        notificationObserver.assertNoErrors().assertNoValues()

        // We should show the lading spinner while performing the request
        // The screen will be finished before the progress is hidden again
        loadingObserver.assertValues(true)

        // We should only finish the activity when it was successful
        verify(proxy).finish()
        verifyNoMoreInteractions(proxy)
    }

    @Test
    fun inactive() {
        val viewModel = LoginViewModel(proxy, context, repository)

        // Trigger login with valid email and password, to get a pending request
        viewModel.login("test@example.com", "12345678")

        // There should only be the login request and it should still be subscribed
        userObservable.assertCount(1).assertAllSubscribed()

        // Should cancel all requests
        viewModel.inactive()

        // Check that the requests are canceled
        userObservable.assertCount(1).assertAllCanceled()

    }

}