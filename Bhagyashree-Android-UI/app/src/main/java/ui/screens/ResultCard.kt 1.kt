
package com.example.aiscamsieldui.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultCard(
    status: String,
    score: Int? = null,
    reason: String = ""
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Status: $status")

            if (score != null) {
                Text(text = "Risk score: $score/100")
            }

            if (reason.isNotBlank()) {
                Text(text = reason)
            }
        }
    }
}
