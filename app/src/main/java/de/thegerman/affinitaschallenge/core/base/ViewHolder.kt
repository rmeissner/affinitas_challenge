package de.thegerman.affinitaschallenge.core.base


interface ViewHolder<in B> {
    fun bind(binding: B)
    fun start()
    fun stop()
    fun unbind()
}