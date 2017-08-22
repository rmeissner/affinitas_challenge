package de.thegerman.affinitaschallenge.utils

import com.squareup.picasso.Transformation
import de.thegerman.affinitaschallenge.networking.ImageLoader
import org.mockito.Answers
import org.mockito.Mockito.*
import org.mockito.internal.creation.MockSettingsImpl
import org.mockito.internal.util.MockUtil
import org.mockito.Mockito.`when` as given


object MockUtils {

    fun requestBuilder(): ImageLoader.RequestBuilder {
        val mock = mock(ImageLoader.RequestBuilder::class.java)
        given(mock.transformation(anyObject(Transformation::class.java))).thenReturn(mock)
        given(mock.placeholder(anyInt())).thenReturn(mock)
        return mock
    }

    fun <T> anyObject(clzz: Class<T>): T {
        any<T>()
        return uncheckedMock(clzz)
    }

    private fun <T> uncheckedMock(clzz: Class<T>): T {
        val impl = MockSettingsImpl<T>().defaultAnswer(Answers.RETURNS_DEFAULTS) as MockSettingsImpl<T>
        val creationSettings = impl.confirm(clzz)
        return MockUtil.createMock(creationSettings)
    }
}