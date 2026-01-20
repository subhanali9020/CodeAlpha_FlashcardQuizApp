package com.example.flashcardapp.model

data class Flashcard(
    val id: Long = System.currentTimeMillis(),
    val question: String,
    val answer: String
)