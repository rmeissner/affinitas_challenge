package de.thegerman.affinitaschallenge.core

import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue


object TestAssertions {

    fun hasItemsCount(count: Int): ViewAssertion {
        return ViewAssertion { view, e ->
            if (view is RecyclerView) {
                assertEquals("hasItemsCount", count, view.adapter.itemCount)
                return@ViewAssertion
            }
            throw e
        }
    }

    fun isAtPosition(position: Int): ViewAssertion {
        return ViewAssertion { view, e ->
            if (view is RecyclerView) {
                (view.layoutManager as? LinearLayoutManager)?.let {
                    assertTrue("isAtPosition",
                            it.findFirstVisibleItemPosition() <= position && it.findLastVisibleItemPosition() >= position)
                }
                return@ViewAssertion
            }
            throw e
        }
    }
}