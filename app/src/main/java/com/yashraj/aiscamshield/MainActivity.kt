
package com.yashraj.aiscamshield

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader
import com.yashraj.aiscamshield.ui.theme.AISCAMSHIELDTheme
import com.yashraj.aiscamshield.ui.screens.WelcomeScreen
import com.yashraj.aiscamshield.ui.screens.HomeScreen
import com.yashraj.aiscamshield.ui.screens.MessageScreen
import com.yashraj.aiscamshield.ui.screens.NumberScreen
import com.yashraj.aiscamshield.ui.screens.QrUpiScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AISCAMSHIELDTheme {

                var currentScreen by remember {
                    mutableStateOf("welcome")
                }

                BackHandler(
                    enabled = currentScreen != "welcome"
                ) {
                    currentScreen = when (currentScreen) {
                        "home" -> "welcome"
                        else -> "home"
                    }
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

                    "url" -> PhishGuardScreen()

                    "message" -> MessageScreen(
                        onBack = {
                            currentScreen = "home"
                        }
                    )

                    "number" -> NumberScreen(
                        onBack = {
                            currentScreen = "home"
                        }
                    )

                    "qr" -> QrUpiScreen(
                        onBack = {
                            currentScreen = "home"
                        }
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PhishGuardScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val assetLoader = WebViewAssetLoader.Builder()
                .addPathHandler(
                    "/assets/",
                    WebViewAssetLoader.AssetsPathHandler(context)
                )
                .build()

            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest
                    ) = assetLoader.shouldInterceptRequest(request.url)
                }

                loadUrl(
                    "https://appassets.androidplatform.net/assets/phishguard/index.html"
                )
            }
        }
    )
}
