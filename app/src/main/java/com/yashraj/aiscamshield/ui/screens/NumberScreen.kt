
package com.yashraj.aiscamshield.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

private val NumberBg = Color(0xFFF5F8FF)
private val NumberNavy = Color(0xFF183B65)
private val NumberBlue = Color(0xFF477DE0)
private val NumberMuted = Color(0xFF71819A)
private val NumberSoftBlue = Color(0xFFE4EEFF)

private const val CALL_TEXT_API =
    "https://ai-scamshield-p6el.onrender.com/check-message"

private data class CallerTextResult(
    val category: String,
    val confidence: Double,
    val note: String
)

private fun normalizeIndianMobile(input: String): String? {
    if (input.any { !it.isDigit() && it !in " +().-" }) {
        return null
    }

    val digits = input.filter { it in '0'..'9' }

    val mobile = when {
        digits.length == 10 -> digits
        digits.length == 11 &&
                digits.startsWith("0") -> digits.drop(1)
        digits.length == 12 &&
                digits.startsWith("91") -> digits.drop(2)
        else -> return null
    }

    return mobile.takeIf {
        it.matches(Regex("[6-9][0-9]{9}"))
    }
}

@Composable
fun NumberScreen(onBack: () -> Unit) {

    var phoneNumber by remember { mutableStateOf("") }
    var callerWords by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var prediction by remember {
        mutableStateOf<CallerTextResult?>(null)
    }
    var checking by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = NumberBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 22.dp,
                    vertical = 20.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(20.dp)
        ) {

            // Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text("Back", color = NumberNavy)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "SCAMSHIELD",
                    color = NumberMuted,
                    fontSize = 10.sp,
                    letterSpacing = 1.2.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .background(
                            NumberSoftBlue,
                            RoundedCornerShape(19.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\u260E",
                        color = NumberNavy,
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Phone Check",
                        color = NumberNavy,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Be cautious with unknown callers",
                        color = NumberMuted,
                        fontSize = 13.sp
                    )
                }
            }

            // Information banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = NumberNavy
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "VERIFY BEFORE YOU TRUST",
                        color = Color(0xFFBFD6FF),
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Unknown number calling?",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Check the number format and analyse what the caller said. A number alone cannot prove a scam.",
                        color = Color(0xFFD7E4F8),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            // Number input
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "PHONE NUMBER",
                    color = NumberNavy,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        message = ""
                        prediction = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Enter phone number")
                    },
                    placeholder = {
                        Text("e.g. +91 98765 43210")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(17.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NumberBlue,
                        unfocusedBorderColor =
                            Color(0xFFD7E1F0),
                        focusedLabelColor = NumberBlue,
                        cursorColor = NumberBlue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Text(
                    text = "Format check supports Indian mobile numbers.",
                    color = NumberMuted,
                    fontSize = 12.sp
                )
            }

            // Caller details
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "WHAT DID THE CALLER SAY? (OPTIONAL)",
                    color = NumberNavy,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                OutlinedTextField(
                    value = callerWords,
                    onValueChange = {
                        callerWords = it
                        message = ""
                        prediction = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Caller message / call summary")
                    },
                    placeholder = {
                        Text(
                            "Example: Share your OTP immediately to unblock your account"
                        )
                    },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(17.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NumberBlue,
                        unfocusedBorderColor =
                            Color(0xFFD7E1F0),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Text(
                    text = "Caller text is analysed using the existing Message Detection server.",
                    color = NumberMuted,
                    fontSize = 12.sp
                )
            }

            // Check button
            Button(
                onClick = {
                    val normalized =
                        normalizeIndianMobile(phoneNumber)

                    prediction = null

                    when {
                        normalized == null -> {
                            message =
                                "Enter a valid Indian mobile number (10 digits, starting with 6-9)."
                        }

                        callerWords.isBlank() -> {
                            message =
                                "Format valid: +91 $normalized. Number reputation: NOT VERIFIED. No verified spam-number database is connected."
                        }

                        else -> {
                            scope.launch {
                                checking = true
                                message = ""

                                try {
                                    prediction = checkCallerText(
                                        callerWords.trim()
                                    )

                                    message =
                                        "Format valid: +91 $normalized. Number reputation: NOT VERIFIED. The result below is about what the caller said, not the phone number."

                                } catch (e: CancellationException) {
                                    throw e
                                } catch (e: Exception) {
                                    message =
                                        "Format valid: +91 $normalized. Number reputation: NOT VERIFIED. Caller analysis failed: ${e.message ?: "Server unavailable"}"
                                } finally {
                                    checking = false
                                }
                            }
                        }
                    }
                },
                enabled = !checking,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NumberNavy,
                    contentColor = Color.White
                )
            ) {
                if (checking) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Text(
                    text = if (checking) {
                        "Checking..."
                    } else {
                        "Check Number"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Results
            if (message.isNotBlank() || prediction != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Check Status",
                            color = NumberNavy,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if (message.isNotBlank()) {
                            Text(
                                text = message,
                                color = NumberMuted,
                                fontSize = 13.sp,
                                lineHeight = 20.sp
                            )
                        }

                        prediction?.let { found ->
                            val resultColor =
                                when (found.category.lowercase()) {
                                    "scam" -> Color(0xFFB42318)
                                    "suspicious" -> Color(0xFFB26A00)
                                    else -> Color(0xFF198754)
                                }

                            Text(
                                text = "Caller text: ${found.category.uppercase()}",
                                color = resultColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Model confidence: ${
                                    String.format(
                                        Locale.US,
                                        "%.2f",
                                        found.confidence
                                    )
                                }%",
                                color = NumberMuted,
                                fontSize = 13.sp
                            )

                            if (found.note.isNotBlank()) {
                                Text(
                                    text = found.note,
                                    color = NumberMuted,
                                    fontSize = 12.sp
                                )
                            }

                            Text(
                                text = "A Safe text prediction does not mean the caller or number is safe.",
                                color = NumberMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Safety reminder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = NumberSoftBlue
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                Color.White,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = NumberBlue,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(11.dp))

                    Column {
                        Text(
                            text = "Keep sensitive details private",
                            color = NumberNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Never share OTPs, passwords or banking PINs with callers.",
                            color = NumberMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Uses the existing Message Detection backend.
private suspend fun checkCallerText(
    text: String
): CallerTextResult = withContext(Dispatchers.IO) {

    val connection =
        URL(CALL_TEXT_API).openConnection()
                as HttpURLConnection

    try {
        connection.requestMethod = "POST"
        connection.connectTimeout = 10000
        connection.readTimeout = 20000
        connection.doOutput = true

        connection.setRequestProperty(
            "Content-Type",
            "application/json; charset=utf-8"
        )

        connection.setRequestProperty(
            "Accept",
            "application/json"
        )

        val body = JSONObject()
            .put("message", text)
            .toString()

        connection.outputStream.use {
            it.write(body.toByteArray(Charsets.UTF_8))
        }

        val code = connection.responseCode

        val response = (
                if (code in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
                )?.bufferedReader(Charsets.UTF_8)?.use {
                it.readText()
            }.orEmpty()

        if (code !in 200..299) {
            throw IOException(
                "Backend HTTP $code: $response"
            )
        }

        val json = JSONObject(response)

        CallerTextResult(
            category = json.optString(
                "category",
                "Unknown"
            ),
            confidence = json.optDouble(
                "confidence",
                0.0
            ),
            note = json.optString(
                "note",
                ""
            )
        )

    } finally {
        connection.disconnect()
    }
}
