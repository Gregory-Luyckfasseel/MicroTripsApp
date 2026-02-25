package com.prog7313.microtrips.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.prog7313.microtrips.models.Budget
import com.prog7313.microtrips.models.Destination
import com.prog7313.microtrips.models.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDestScreen(
    onBack: () -> Unit,
    onSave: (Destination) -> Unit
) {
    var destinationName by remember { mutableStateOf("") }
    var destinationDescription by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var timeNeeded by remember { mutableStateOf("") }
    var tips by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var mapsQuery by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    var priceText by remember { mutableStateOf("") }
    val price = priceText.toDoubleOrNull()
    val priceIsValid = price != null && price > 0.0

    val canSave = destinationName.isNotBlank() && destinationDescription.isNotBlank() && priceIsValid &&
            province.isNotBlank() && category.isNotBlank() && image.isNotBlank() && timeNeeded.isNotBlank() &&
            tips.isNotBlank() && area.isNotBlank() && mapsQuery.isNotBlank() && tags.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new destination") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(onClick = {
                        if (price != null) {
                            val newDest = Destination(
                                name = destinationName,
                                shortDescription = destinationDescription,
                                budget = Budget(transport = price.toLong(), food = 0, entry = 0, misc = 0),
                                id = 0L,
                                province = province,
                                category = category,
                                image = image,
                                timeNeeded = timeNeeded,
                                bestSeason = "",
                                tips = tips.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                location = Location(area, mapsQuery),
                                tags = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            )
                            onSave(newDest)
                        }
                    }, enabled = canSave) {
                        Text("Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = destinationName,
                onValueChange = { destinationName = it },
                label = { Text("Destination Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = priceText,
                onValueChange = { input ->
                    priceText = sanitizeMoneyInput(input)
                },
                label = { Text("Price (R)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = priceText.isNotBlank() && !priceIsValid,
                supportingText = {
                    if (priceText.isNotBlank() && !priceIsValid) {
                        Text("Enter a valid amount (e.g., 1499.99)")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = destinationDescription,
                onValueChange = { destinationDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = province,
                onValueChange = { province = it },
                label = { Text("Province") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = { Text("Image file name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = timeNeeded,
                onValueChange = { timeNeeded = it },
                label = { Text("Time needed") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tips,
                onValueChange = { tips = it },
                label = { Text("Tips (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = area,
                onValueChange = { area = it },
                label = { Text("Area") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = mapsQuery,
                onValueChange = { mapsQuery = it },
                label = { Text("Map Search Query") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text("Tags (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun sanitizeMoneyInput(input: String): String {
    val filtered = input.filter { it.isDigit() ||  it == '.' }

    val firstDot = filtered.indexOf('.')
    if (firstDot == -1) return filtered

    val before = filtered.substring(0, firstDot)
    val afterRaw = filtered.substring(firstDot + 1).replace(".","")
    val after = afterRaw.take(2)

    return "$before.$after"
}
