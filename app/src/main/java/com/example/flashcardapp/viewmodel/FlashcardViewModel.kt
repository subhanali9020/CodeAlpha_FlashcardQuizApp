package com.example.flashcardapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.flashcardapp.model.Flashcard

class FlashcardViewModel : ViewModel() {
    // State list
    private val _flashcards = mutableStateListOf<Flashcard>(
        Flashcard(question = "What is Jetpack Compose?", answer = "Android's modern toolkit for UI."),
        Flashcard(question = "Explain MVVM.", answer = "Model-View-ViewModel architecture pattern.")
    )
    val flashcards: List<Flashcard> get() = _flashcards

    // UI States
    var currentIndex by mutableStateOf(0)
        private set // Bahar se koi directly change na kare

    var isAnswerVisible by mutableStateOf(false)
        private set

    // Actions
    fun nextCard() {
        if (currentIndex < _flashcards.size - 1) {
            currentIndex++
            isAnswerVisible = false
        }
    }

    fun previousCard() {
        if (currentIndex > 0) {
            currentIndex--
            isAnswerVisible = false
        }
    }

    fun toggleAnswer() {
        isAnswerVisible = !isAnswerVisible
    }

    fun addCard(question: String, answer: String) {
        _flashcards.add(Flashcard(question = question, answer = answer))
    }

    fun deleteCurrentCard() {
        if (_flashcards.isNotEmpty()) {
            _flashcards.removeAt(currentIndex)
            if (currentIndex >= _flashcards.size && currentIndex > 0) {
                currentIndex--
            }
            isAnswerVisible = false
        }
    }
}