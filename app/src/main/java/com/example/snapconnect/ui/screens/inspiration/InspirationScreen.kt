package com.example.snapconnect.ui.screens.inspiration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.ui.viewmodel.InspirationViewModel
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.theme.SnapYellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip

@Composable
fun InspirationScreen(
    navController: NavController,
    viewModel: InspirationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var prompt by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text("Inspiration", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = { Text("Describe your project …") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { viewModel.generateMoodboard(prompt.text) },
                    enabled = prompt.text.isNotBlank() && !state.loading,
                ) { Text("Generate Stories") }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.generateAiImages(prompt.text) },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapYellow),
                    enabled = prompt.text.isNotBlank() && !state.loadingAi,
                ) { Text("Generate AI Images") }
            }

            if (state.loading || state.loadingAi) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (state.error != null) {
                    Text(state.error ?: "", color = MaterialTheme.colorScheme.error)
                }
                LazyColumn {
                    items(state.moodItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = item.content_url,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )
                                Column(Modifier.padding(8.dp)) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        items(item.style_tags) { styleTag ->
                                            AssistChip(
                                                onClick = {
                                                    navController.navigate(
                                                        Screen.StyleGallery.createRoute(styleTag)
                                                    )
                                                },
                                                label = { Text(styleTag) },
                                                colors = AssistChipDefaults.assistChipColors(
                                                    containerColor = SnapYellow.copy(alpha = 0.2f),
                                                    labelColor = MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                        }
                                    }
                                    Text(text = "Score: %.2f".format(item.score), style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }

                // AI images section
                if (state.aiImages.isNotEmpty()) {
                    Text(
                        "AI Images", 
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.aiImages) { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
} 