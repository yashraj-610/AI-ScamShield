

package com.yashraj.aiscamshield.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val UrlBg = Color(0xFFF5F8FF)
private val UrlNavy = Color(0xFF183B65)
private val UrlBlue = Color(0xFF477DE0)
private val UrlMuted = Color(0xFF71819A)
private val UrlSoftBlue = Color(0xFFE4EEFF)

@Composable
fun UrlScreen(onBack: () -> Unit) {

    var url by remember { mutableStateOf("") }
    var notice by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = UrlBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Top navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = UrlNavy
                    )
                ) {
                    Text(
                        text = "Back",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "SCAMSHIELD",
                    color = UrlMuted,
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
                            UrlSoftBlue,
                            RoundedCornerShape(19.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    LinkCheckIcon(
                        modifier = Modifier.size(34.dp),
                        color = UrlNavy
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Web Link Check",
                        color = UrlNavy,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Pause before opening a link",
                        color = UrlMuted,
                        fontSize = 13.sp
                    )
                }
            }

            // Information banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = UrlNavy
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "BROWSE WITH CAUTION",
                        color = Color(0xFFBFD6FF),
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Does this link look trustworthy?",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Enter a website address to begin a link check. Never enter passwords or payment details on an untrusted website.",
                        color = Color(0xFFD7E4F8),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            // URL input
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "WEBSITE ADDRESS",
                    color = UrlNavy,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )

                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                        notice = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Enter website URL")
                    },
                    placeholder = {
                        Text("https://example.com")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri
                    ),
                    shape = RoundedCornerShape(17.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = UrlBlue,
                        unfocusedBorderColor = Color(0xFFD7E1F0),
                        focusedLabelColor = UrlBlue,
                        cursorColor = UrlBlue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Text(
                    text = "Tip: Check the spelling of the domain carefully.",
                    color = UrlMuted,
                    fontSize = 12.sp
                )
            }

            // Check button
            Button(
                onClick = {
                    val value = url.trim()

                    notice = when {
                        value.isBlank() ->
                            "Please enter a URL."

                        value.startsWith(
                            "https://",
                            ignoreCase = true
                        ) || value.startsWith(
                            "http://",
                            ignoreCase = true
                        ) ->
                            "URL format accepted. Real scam detection is not connected to this screen yet."

                        else ->
                            "Enter a URL starting with http:// or https://."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = UrlNavy,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Check Website",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Result message
            if (notice.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "Link Check Status",
                            color = UrlNavy,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = notice,
                            color = UrlMuted,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Safety reminder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = UrlSoftBlue
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
                            color = UrlBlue,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(11.dp))

                    Column {
                        Text(
                            text = "HTTPS doesn't guarantee safety",
                            color = UrlNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Scam websites can also use HTTPS.",
                            color = UrlMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Vector link icon
@Composable
private fun LinkCheckIcon(
    modifier: Modifier = Modifier,
    color: Color = UrlNavy
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()

        // First chain link
        drawArc(
            color = color,
            startAngle = 45f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(w * 0.06f, h * 0.33f),
            size = androidx.compose.ui.geometry.Size(
                w * 0.48f,
                h * 0.42f
            ),
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        // Second chain link
        drawArc(
            color = color,
            startAngle = 225f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(w * 0.46f, h * 0.25f),
            size = androidx.compose.ui.geometry.Size(
                w * 0.48f,
                h * 0.42f
            ),
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        // Connecting line
        drawLine(
            color = color,
            start = Offset(w * 0.36f, h * 0.62f),
            end = Offset(w * 0.66f, h * 0.38f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}
