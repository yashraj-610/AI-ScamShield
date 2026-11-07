
package com.example.aiscamsieldui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.aiscamsieldui.ui.screens.HomeScreen
import com.example.aiscamsieldui.ui.screens.MessageScreen
import com.example.aiscamsieldui.ui.screens.NumberScreen
import com.example.aiscamsieldui.ui.screens.QrUpiScreen
import com.example.aiscamsieldui.ui.screens.UrlScreen
import com.example.aiscamsieldui.ui.screens.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var currentScreen by remember {
                        mutableStateOf("welcome")
                    }

                    when (currentScreen) {
                        "welcome" -> WelcomeScreen(
                            onGetStarted = {
                                currentScreen = "home"
                            }
                        )

                        "home" -> HomeScreen(
                            onUrlClick = {
                                currentScreen = "url"
                            },
                            onMessageClick = {
                                currentScreen = "message"
                            },
                            onQrClick = {
                                currentScreen = "qr"
                            },
                            onNumberClick = {
                                currentScreen = "number"
                            }
                        )

                        "url" -> UrlScreen(
                            onBack = {
                                currentScreen = "home"
                            }
                        )

                        "message" -> MessageScreen(
                            onBack = {
                                currentScreen = "home"
                            }
                        )

                        "qr" -> QrUpiScreen(
                            onBack = {
                                currentScreen = "home"
                            }
                        )

                        "number" -> NumberScreen(
                            onBack = {
                                currentScreen = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}
