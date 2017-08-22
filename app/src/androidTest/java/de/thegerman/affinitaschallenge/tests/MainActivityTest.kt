package de.thegerman.affinitaschallenge.tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jakewharton.rxrelay2.PublishRelay
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.BaseTest
import de.thegerman.affinitaschallenge.core.TestFactory
import de.thegerman.affinitaschallenge.main.MainActivity
import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import de.thegerman.affinitaschallenge.core.TestAssertions
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when` as given


@RunWith(AndroidJUnit4::class)
class MainActivityTest: BaseTest() {
    @JvmField
    @Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    val favoriteRelay = PublishRelay.create<Boolean>()
    val ratingRelay = PublishRelay.create<Int>()

    @Before
    fun setup() {
        given(feedRepository.observeFavorite(anyString())).thenReturn(favoriteRelay.toFlowable(BackpressureStrategy.LATEST))
        given(feedRepository.observeRating(anyString())).thenReturn(ratingRelay.toFlowable(BackpressureStrategy.LATEST))
    }

    @Test
    fun updateItems() {
        val testItems = ArrayList<ReceiptEntry>()
        testItems.add(ReceiptEntry(TestFactory.createReceipt()))
        testItems.add(ReceiptEntry(TestFactory.createReceipt()))
        given(feedRepository.loadReceipts()).thenReturn(Observable.just(testItems))

        activityRule.launchActivity(null)

        // We returned 2 items, make sure that they are displayed
        onView(ViewMatchers.withId(R.id.list)).check(TestAssertions.hasItemsCount(2))

        // TODO: check content of entries
    }
}