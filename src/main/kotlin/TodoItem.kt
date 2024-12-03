package org.example

import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(
    val id: ULong,
    val text: String,
    val completed: Boolean
)