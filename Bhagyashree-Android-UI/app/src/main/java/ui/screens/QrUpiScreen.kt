
package com.example.aiscamsieldui.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val QrBg = Color(0xFFF5F8FF)
private val QrNavy = Color(0xFF183B65)
private val QrBlue = Color(0xFF477DE0)
private val QrMuted = Color(0xFF71819A)
private val QrSoftBlue = Color(0xFFE4EEFF)

@Composable
fun QrUpiScreen(onBack: () -> Unit) {
    var notice by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = QrBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text("← Back", color = QrNavy)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "SCAMSHIELD",
                    color = QrMuted,
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
                            QrSoftBlue,
                            RoundedCornerShape(19.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "▦",
                        color = QrNavy,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(14.dp))

                Column {
                    Text(
                        text = "QR & UPI Check",
                        color = QrNavy,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Think before you pay",
                        color = QrMuted,
                        fontSize = 13.sp
                    )
                }
            }

            // Intro banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = QrNavy
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "SCAN SMARTER",
                        color = Color(0xFFBFD6FF),
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Check before you scan.",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Be careful with unexpected payment requests and QR codes. A QR code can direct you to a suspicious website or payment address.",
                        color = Color(0xFFD7E4F8),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            // QR preview area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                QrSoftBlue,
                                RoundedCornerShape(22.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "▦",
                            color = QrNavy,
                            fontSize = 76.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "QR scanner area",
                        color = QrNavy,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Camera scanning or image upload is not connected yet.",
                        color = QrMuted,
                        fontSize = 12.sp
                    )
                }
            }

            // Scan button
            Button(
                onClick = {
                    notice =
                        "QR scanning and fraud detection are not connected to this screen yet. No QR code has been analysed."
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QrNavy,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Scan / Check QR  →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Status
            if (notice.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = notice,
                        modifier = Modifier.padding(18.dp),
                        color = QrMuted,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            // Safety reminder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = QrSoftBlue
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
                            color = QrBlue,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.size(11.dp))

                    Column {
                        Text(
                            text = "Never pay to receive money",
                            color = QrNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Verify the recipient and payment details before approving.",
                            color = QrMuted,
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
