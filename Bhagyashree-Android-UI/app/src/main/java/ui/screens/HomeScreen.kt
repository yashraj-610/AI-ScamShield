
package com.example.aiscamsieldui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HomeBg = Color(0xFFF5F8FF)
private val HomeNavy = Color(0xFF183B65)
private val HomeBlue = Color(0xFF477DE0)
private val HomeMuted = Color(0xFF71819A)
private val HomeBorder = Color(0xFFE4EAF4)

@Composable
fun HomeScreen(
    onUrlClick: () -> Unit,
    onMessageClick: () -> Unit,
    onQrClick: () -> Unit,
    onNumberClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(HomeNavy),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ScamShield",
                    color = HomeNavy,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "YOUR DIGITAL SAFETY PARTNER",
                    color = HomeMuted,
                    fontSize = 9.sp,
                    letterSpacing = 0.8.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE5EEFF)),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = HomeBlue, fontSize = 22.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Welcome heading
        Text(
            text = "Stay one step",
            color = HomeNavy,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.6).sp
        )

        Text(
            text = "ahead of scams.",
            color = HomeBlue,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.6).sp
        )

        Spacer(modifier = Modifier.height(9.dp))

        Text(
            text = "Choose what you want to check today.",
            color = HomeMuted,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Premium security banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = HomeNavy
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "THINK BEFORE YOU CLICK",
                        color = Color(0xFFBFD6FF),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Text(
                        text = "Not sure it's safe?",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Check it with ScamShield.",
                        color = Color(0xFFD7E4F8),
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(66.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFF315783)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        fontSize = 43.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "SECURITY TOOLS",
            color = HomeMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(13.dp))

        // First row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            FeatureCard(
                modifier = Modifier.weight(1f),
                symbol = "✉",
                title = "Message",
                subtitle = "Check suspicious texts",
                number = "01",
                tint = Color(0xFFE6EEFF),
                onClick = onMessageClick
            )

            FeatureCard(
                modifier = Modifier.weight(1f),
                symbol = "↗",
                title = "Web Links",
                subtitle = "Check a URL",
                number = "02",
                tint = Color(0xFFE2F4F5),
                onClick = onUrlClick
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        // Second row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            FeatureCard(
                modifier = Modifier.weight(1f),
                symbol = "▦",
                title = "QR & UPI",
                subtitle = "Check payment risks",
                number = "03",
                tint = Color(0xFFF0E8FF),
                onClick = onQrClick
            )

            FeatureCard(
                modifier = Modifier.weight(1f),
                symbol = "☎",
                title = "Phone Check",
                subtitle = "Review a number",
                number = "04",
                tint = Color(0xFFFFEEDF),
                onClick = onNumberClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Safety reminder
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(39.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF1D9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "!",
                        color = Color(0xFFB7791F),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Stay alert, stay safe",
                        color = HomeNavy,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Never share your UPI PIN or OTP.",
                        color = HomeMuted,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "SCAMSHIELD  •  DIGITAL SAFETY",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = HomeMuted,
            fontSize = 10.sp,
            letterSpacing = 0.8.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun FeatureCard(
    modifier: Modifier = Modifier,
    symbol: String,
    title: String,
    subtitle: String,
    number: String,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(178.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(tint),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = symbol,
                        color = HomeNavy,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = number,
                    color = HomeMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(17.dp))

            Text(
                text = title,
                color = HomeNavy,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = subtitle,
                color = HomeMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Explore  →",
                color = HomeBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
