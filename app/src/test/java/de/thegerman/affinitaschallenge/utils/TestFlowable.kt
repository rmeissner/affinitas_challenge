package de.thegerman.affinitaschallenge.utils

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue


class TestFlowable<T> {
    private val emitterList = ArrayList<FlowableEmitter<T>>()

    fun get(): Flowable<T> {
        return Flowable.create({ emitterList.add(it) }, BackpressureStrategy.DROP)
    }

    fun assertCount(count: Int): TestFlowable<T> {
        assertEquals(String.format("Should have %s subscription!", count), count, emitterList.size)
        return this
    }

    fun assertAllSubscribed(): TestFlowable<T> {
        for (emitter in emitterList) {
            assertTrue("Should be subscribed!", !emitter.isCancelled)
        }
        return this
    }

    fun assertAllCanceled(): TestFlowable<T> {
        for (emitter in emitterList) {
            assertTrue("Should be disposed!", emitter.isCancelled)
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