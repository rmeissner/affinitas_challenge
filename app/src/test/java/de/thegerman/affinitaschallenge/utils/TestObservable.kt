package de.thegerman.affinitaschallenge.utils

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Assert

class TestObservable<T> {
    private val emitterList = ArrayList<ObservableEmitter<T>>()

    fun get(): Observable<T> {
        return Observable.create { emitterList.add(it) }
    }

    fun assertCount(count: Int): TestObservable<T> {
        Assert.assertEquals(String.format("Should have %s subscription!", count), count, emitterList.size)
        return this
    }

    fun assertAllSubscribed(): TestObservable<T> {
        for (emitter in emitterList) {
            Assert.assertTrue("Should be subscribed!", !emitter.isDisposed)
        }
        return this
    }

    fun assertAllCanceled(): TestObservable<T> {
        for (emitter in emitterList) {
            Assert.assertTrue("Should be disposed!", emitter.isDisposed)
        }
        return this
    }

    fun accept(t: T) {
        for (emitter in emitterList) {
            emitter.onNext(t)
        }
    }

    fun error(t: Throwable) {
        for (emitter in emitterList) {
            emitter.onError(t)
        }
        emitterList.clear()
    }

    fun complete() {
        for (emitter in emitterList) {
            emitter.onComplete()
        }
        emitterList.clear()
    }
}