package de.thegerman.affinitaschallenge.core

import de.thegerman.affinitaschallenge.repositories.models.Receipt
import java.util.*


object TestFactory {
    // We set default values for every field so we only need to specify what we need for the test
    fun createReceipt(id: String = UUID.randomUUID().toString(),
                      name: String = id,
                      description: String = "Description for " + id,
                      image: String = "http://loremflickr.com/800/600/")
            = Receipt(id, name, description, image)
}