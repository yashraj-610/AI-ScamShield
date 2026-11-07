

package com.yashraj.aiscamshield.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MessageBg = Color(0xFFF5F8FF)
private val MessageNavy = Color(0xFF183B65)
private val MessageBlue = Color(0xFF477DE0)
private val MessageMuted = Color(0xFF71819A)
private val MessageSoftBlue = Color(0xFFE4EEFF)

@Composable
fun MessageScreen(onBack: () -> Unit) {

    var message by remember { mutableStateOf("") }
    var showNotice by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MessageBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // Back navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MessageNavy
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(
                        text = "Back",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "SCAMSHIELD",
                    color = MessageMuted,
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
                            MessageSoftBlue,
                            RoundedCornerShape(19.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    MessageIcon(
                        modifier = Modifier.size(30.dp),
                        color = MessageNavy
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Message Check",
                        color = MessageNavy,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Spot suspicious messages",
                        color = MessageMuted,
                        fontSize = 13.sp
                    )
                }
            }

            // Information banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MessageNavy
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "PAUSE. PASTE. CHECK.",
                        color = Color(0xFFBFD6FF),
                        fontSize = 10.sp,
                        letterSpacing = 1.3.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Text(
                        text = "Something feels suspicious?",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Review a message before responding or sharing personal information.",
                        color = Color(0xFFD7E4F8),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            // Message input
            Column(
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Text(
                    text = "MESSAGE CONTENT",
                    color = MessageNavy,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )

                Text(
                    text = "Paste the message you want to review below.",
                    color = MessageMuted,
                    fontSize = 13.sp
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        showNotice = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    label = {
                        Text("Enter or paste a message")
                    },
                    placeholder = {
                        Text(
                            "Example: You have won a prize! Click this link..."
                        )
                    },
                    shape = RoundedCornerShape(18.dp),
                    maxLines = 7,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MessageBlue,
                        unfocusedBorderColor = Color(0xFFD7E1F0),
                        focusedLabelColor = MessageBlue,
                        cursorColor = MessageBlue,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Analyse button
            Button(
                onClick = {
                    showNotice = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MessageNavy,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Analyse Message",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Prototype status
            if (showNotice) {
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
                            text = if (message.isBlank()) {
                                "Message required"
                            } else {
                                "Detection not connected"
                            },
                            color = MessageNavy,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = if (message.isBlank()) {
                                "Please enter a message before continuing."
                            } else {
                                "The message detection module is not connected to this screen yet. No prediction has been made."
                            },
                            color = MessageMuted,
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
                    containerColor = MessageSoftBlue
                )
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = MessageBlue,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(11.dp))

                    Column {
                        Text(
                            text = "Protect your information",
                            color = MessageNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Never share an OTP, password or UPI PIN.",
                            color = MessageMuted,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Vector message icon - no font dependency
@Composable
private fun MessageIcon(
    modifier: Modifier = Modifier,
    color: Color = MessageNavy
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2.3.dp.toPx()

        val bubble = Path().apply {
            moveTo(w * 0.16f, h * 0.16f)
            lineTo(w * 0.84f, h * 0.16f)

            quadraticTo(
                w * 0.92f, h * 0.16f,
                w * 0.92f, h * 0.27f
            )

            lineTo(w * 0.92f, h * 0.67f)

            quadraticTo(
                w * 0.92f, h * 0.77f,
                w * 0.82f, h * 0.77f
            )

            lineTo(w * 0.43f, h * 0.77f)
            lineTo(w * 0.19f, h * 0.94f)
            lineTo(w * 0.19f, h * 0.77f)

            quadraticTo(
                w * 0.08f, h * 0.77f,
                w * 0.08f, h * 0.67f
            )

            lineTo(w * 0.08f, h * 0.27f)

            quadraticTo(
                w * 0.08f, h * 0.16f,
                w * 0.16f, h * 0.16f
            )

            close()
        }

        drawPath(
            path = bubble,
            color = color,
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        drawLine(
            color = color,
            start = Offset(w * 0.28f, h * 0.38f),
            end = Offset(w * 0.72f, h * 0.38f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(w * 0.28f, h * 0.55f),
            end = Offset(w * 0.62f, h * 0.55f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}
