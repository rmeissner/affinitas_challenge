package de.thegerman.affinitaschallenge.core.adapter

import java.util.*


class AdapterData<out T>(val uuid: String = UUID.randomUUID().toString(), val entries: List<T>)