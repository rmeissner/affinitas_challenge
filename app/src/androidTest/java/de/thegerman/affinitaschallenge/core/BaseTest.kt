package de.thegerman.affinitaschallenge.core

import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import org.junit.Before
import javax.inject.Inject


abstract class BaseTest {

    @Inject
    lateinit var feedRepository: ReceiptsRepository

    @Before
    fun setupBase() {
        TestApplication.testComponent().inject(this)
    }
}