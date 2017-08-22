package de.thegerman.affinitaschallenge.networking.models

// We only use a minimal data model to demonstrate image loading and interactions with the entry
// For some fields we might want to use custom type adapters (e.g. the duration field, which could be parsed with Yoda time)
data class Receipt(
        val id: String,
        val name: String,
        val description: String,
        val image: String)