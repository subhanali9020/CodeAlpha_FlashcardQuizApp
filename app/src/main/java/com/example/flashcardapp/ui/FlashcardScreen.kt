package com.example.flashcardapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardapp.R
import com.example.flashcardapp.ui.components.AddCardDialog
import com.example.flashcardapp.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(viewModel: FlashcardViewModel = viewModel()) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val cards = viewModel.flashcards

            if (cards.isEmpty()) {
                Text(stringResource(R.string.no_cards), style = MaterialTheme.typography.headlineSmall)
            } else {
                // Card Counter
                Text(
                    text = stringResource(R.string.card_count, viewModel.currentIndex + 1, cards.size),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Flashcard Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clickable { viewModel.toggleAnswer() },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (viewModel.isAnswerVisible)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (viewModel.isAnswerVisible) cards[viewModel.currentIndex].answer else cards[viewModel.currentIndex].question,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Control Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { viewModel.previousCard() }, enabled = viewModel.currentIndex > 0) {
                        Text(stringResource(R.string.previous))
                    }

                    Button(onClick = { viewModel.toggleAnswer() }) {
                        Text(if (viewModel.isAnswerVisible) stringResource(R.string.hide_answer) else stringResource(R.string.show_answer))
                    }

                    Button(onClick = { viewModel.nextCard() }, enabled = viewModel.currentIndex < cards.size - 1) {
                        Text(stringResource(R.string.next))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delete Button
                TextButton(
                    onClick = { viewModel.deleteCurrentCard() },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.delete_card))
                }
            }
        }

        if (showAddDialog) {
            AddCardDialog(
                onDismiss = { showAddDialog = false },
                onSave = { q, a ->
                    viewModel.addCard(q, a)
                    showAddDialog = false
                }
            )
        }
    }
}