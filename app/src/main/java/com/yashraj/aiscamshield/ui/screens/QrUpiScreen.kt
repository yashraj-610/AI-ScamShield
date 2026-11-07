
package com.yashraj.aiscamshield.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

private val QrBg = Color(0xFFF5F8FF)
private val QrNavy = Color(0xFF183B65)
private val QrBlue = Color(0xFF477DE0)
private val QrMuted = Color(0xFF71819A)
private val QrSoftBlue = Color(0xFFE4EEFF)

private const val QR_ENDPOINT =
    "https://ai-scamshield-p6el.onrender.com/check-qr"

private const val MAX_QR_IMAGE_BYTES = 5 * 1024 * 1024

private data class QrAnalysisResult(
    val type: String,
    val prediction: String,
    val maliciousProbability: Double?,
    val url: String,
    val riskLevel: String,
    val paymentDetails: List<Pair<String, String>>,
    val reasons: List<String>,
    val decodedData: String,
    val message: String,
    val note: String
)

@Composable
fun QrUpiScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var result by remember {
        mutableStateOf<QrAnalysisResult?>(null)
    }

    var notice by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    // Existing gallery image picker
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImage = uri
            result = null
            notice = ""
        }
    }

    // NEW: Camera photo capture
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->

        if (bitmap != null) {
            try {
                val photoFile = File(
                    context.cacheDir,
                    "scamshield_camera_qr.jpg"
                )

                photoFile.outputStream().use { stream ->
                    val saved = bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        95,
                        stream
                    )

                    if (!saved) {
                        throw IOException(
                            "Unable to save camera photo."
                        )
                    }
                }

                selectedImage = Uri.fromFile(photoFile)
                result = null
                notice =
                    "Camera QR image captured. Tap Scan / Check QR."

            } catch (e: Exception) {
                notice =
                    "Camera image error: ${e.message}"
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = QrBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 22.dp,
                    vertical = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Top navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(
                        text = "Back",
                        color = QrNavy
                    )
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
                    QrCodeIcon(
                        modifier = Modifier.size(34.dp),
                        color = QrNavy
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

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

            // Information banner
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

            // Existing QR preview area
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
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
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
                        QrCodeIcon(
                            modifier = Modifier.size(75.dp),
                            color = QrNavy
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (selectedImage != null) {
                            "QR image selected"
                        } else {
                            "QR scanner area"
                        },
                        color = QrNavy,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = if (selectedImage != null) {
                            "Ready for analysis"
                        } else {
                            "Choose a QR image from your gallery."
                        },
                        color = QrMuted,
                        fontSize = 12.sp
                    )
                }
            }

            // NEW: Camera button
            Button(
                onClick = {
                    cameraLauncher.launch(null)
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QrBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Scan QR with Camera",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Existing gallery selection button
            OutlinedButton(
                onClick = {
                    imagePicker.launch("image/*")
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (selectedImage == null) {
                        "Choose QR Image"
                    } else {
                        "Change QR Image"
                    },
                    color = QrNavy,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Existing QR analysis button
            Button(
                onClick = {
                    val imageUri = selectedImage

                    if (imageUri == null) {
                        notice = "Please choose a QR image first."
                        result = null
                    } else {
                        scope.launch {
                            isLoading = true
                            notice = ""
                            result = null

                            try {
                                result = analyseQrImage(
                                    context,
                                    imageUri
                                )
                            } catch (e: CancellationException) {
                                throw e
                            } catch (e: Exception) {
                                Log.e(
                                    "ScamShieldQR",
                                    "QR analysis failed",
                                    e
                                )

                                notice =
                                    "QR analysis failed: " +
                                            (e.message ?: "Unknown error")
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QrNavy,
                    contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Text(
                    text = if (isLoading) {
                        "Analysing QR..."
                    } else {
                        "Scan / Check QR"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Existing status card
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

            // Existing QR detection result
            result?.let { analysis ->

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
                            Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = "QR Analysis Result",
                            color = QrNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        when (analysis.type.lowercase()) {

                            "url" -> {
                                val predictionColor =
                                    if (
                                        analysis.prediction.equals(
                                            "malicious",
                                            ignoreCase = true
                                        )
                                    ) {
                                        Color(0xFFB42318)
                                    } else {
                                        Color(0xFF198754)
                                    }

                                Text(
                                    text = "Website QR Code",
                                    color = QrNavy,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Prediction: ${analysis.prediction}",
                                    color = predictionColor,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                analysis.maliciousProbability?.let {
                                    Text(
                                        text = "Estimated malicious probability: ${
                                            String.format(
                                                Locale.US,
                                                "%.2f",
                                                it
                                            )
                                        }%",
                                        color = QrMuted,
                                        fontSize = 13.sp
                                    )
                                }

                                Text(
                                    text = "Website: ${analysis.url}",
                                    color = QrMuted,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp
                                )
                            }

                            "upi" -> {
                                Text(
                                    text = "UPI Payment QR Code",
                                    color = QrNavy,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                val riskColor = when (
                                    analysis.riskLevel.uppercase()
                                ) {
                                    "HIGH" ->
                                        Color(0xFFB42318)

                                    "MODERATE" ->
                                        Color(0xFFB26A00)

                                    "LOW" ->
                                        Color(0xFF198754)

                                    else -> QrMuted
                                }

                                Text(
                                    text = "Risk level: ${analysis.riskLevel}",
                                    color = riskColor,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                analysis.paymentDetails.forEach { detail ->
                                    Text(
                                        text = "${detail.first}: ${detail.second}",
                                        color = QrMuted,
                                        fontSize = 13.sp,
                                        lineHeight = 20.sp
                                    )
                                }

                                if (analysis.reasons.isNotEmpty()) {
                                    Text(
                                        text = "Risk observations",
                                        color = QrNavy,
                                        fontWeight = FontWeight.Bold
                                    )

                                    analysis.reasons.forEach { reason ->
                                        Text(
                                            text = "- $reason",
                                            color = QrMuted,
                                            fontSize = 13.sp,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }

                            else -> {
                                Text(
                                    text = "QR content not assessed",
                                    color = QrNavy,
                                    fontWeight = FontWeight.SemiBold
                                )

                                if (analysis.decodedData.isNotBlank()) {
                                    Text(
                                        text = analysis.decodedData,
                                        color = QrMuted,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                        if (analysis.message.isNotBlank()) {
                            Text(
                                text = analysis.message,
                                color = QrMuted,
                                fontSize = 13.sp,
                                lineHeight = 20.sp
                            )
                        }

                        if (analysis.note.isNotBlank()) {
                            HorizontalDivider(
                                color = Color(0xFFE4EAF4)
                            )

                            Text(
                                text = analysis.note,
                                color = QrMuted,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }

                        Text(
                            text = "This prototype assessment does not guarantee that a website or UPI recipient is safe.",
                            color = QrMuted,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Existing safety reminder
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

                    Spacer(modifier = Modifier.width(11.dp))

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

// Existing backend function with camera image support
private suspend fun analyseQrImage(
    context: Context,
    imageUri: Uri
): QrAnalysisResult = withContext(Dispatchers.IO) {

    // Supports both gallery URIs and camera file URIs
    val detectedType =
        context.contentResolver.getType(imageUri)
            ?: if (imageUri.scheme == "file") {
                "image/jpeg"
            } else {
                null
            }

    val contentType = when (
        detectedType?.lowercase()
    ) {
        "image/png" -> "image/png"

        "image/jpeg", "image/jpg" ->
            "image/jpeg"

        "image/webp" -> "image/webp"

        else -> throw IOException(
            "Select a PNG, JPG or WebP QR image."
        )
    }

    val imageBytes =
        context.contentResolver.openInputStream(imageUri)
            ?.use { stream ->

                val output = ByteArrayOutputStream()
                val buffer = ByteArray(8192)
                var totalBytes = 0

                while (true) {
                    val count = stream.read(buffer)

                    if (count == -1) break

                    totalBytes += count

                    if (totalBytes > MAX_QR_IMAGE_BYTES) {
                        throw IOException(
                            "Image is too large. Maximum size is 5 MB."
                        )
                    }

                    output.write(buffer, 0, count)
                }

                output.toByteArray()
            } ?: throw IOException(
            "Could not open selected image."
        )

    if (imageBytes.isEmpty()) {
        throw IOException("Selected image is empty.")
    }

    val connection =
        URL(QR_ENDPOINT).openConnection()
                as HttpURLConnection

    try {
        connection.requestMethod = "POST"
        connection.connectTimeout = 10000
        connection.readTimeout = 30000
        connection.doOutput = true

        connection.setRequestProperty(
            "Content-Type",
            contentType
        )

        connection.setRequestProperty(
            "Accept",
            "application/json"
        )

        connection.outputStream.use { output ->
            output.write(imageBytes)
        }

        val code = connection.responseCode

        val responseText = (
                if (code in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
                )?.bufferedReader(Charsets.UTF_8)?.use {
                it.readText()
            }.orEmpty()

        if (code !in 200..299) {
            val detail = try {
                JSONObject(responseText).optString(
                    "detail",
                    responseText
                )
            } catch (e: Exception) {
                responseText
            }

            throw IOException(
                "Server returned HTTP $code: $detail"
            )
        }

        val json = JSONObject(responseText)

        val detailsJson =
            json.optJSONObject("payment_details")

        val details =
            mutableListOf<Pair<String, String>>()

        if (detailsJson != null) {
            val keys = detailsJson.keys()

            while (keys.hasNext()) {
                val key = keys.next()

                details.add(
                    key to detailsJson.optString(key)
                )
            }
        }

        val reasons = mutableListOf<String>()
        val reasonsArray = json.optJSONArray("reasons")

        if (reasonsArray != null) {
            for (i in 0 until reasonsArray.length()) {
                reasons.add(
                    reasonsArray.optString(i)
                )
            }
        }

        QrAnalysisResult(
            type = json.optString("type", "other"),
            prediction = json.optString(
                "prediction",
                "Not assessed"
            ),
            maliciousProbability =
                if (
                    json.has("malicious_probability") &&
                    !json.isNull("malicious_probability")
                ) {
                    json.optDouble("malicious_probability")
                } else {
                    null
                },
            url = json.optString("url", ""),
            riskLevel = json.optString(
                "risk_level",
                "NOT_ASSESSED"
            ),
            paymentDetails = details,
            reasons = reasons,
            decodedData = json.optString(
                "decoded_data",
                ""
            ),
            message = json.optString("message", ""),
            note = json.optString("note", "")
        )
    } finally {
        connection.disconnect()
    }
}

// Existing decorative QR icon
@Composable
private fun QrCodeIcon(
    modifier: Modifier = Modifier,
    color: Color = QrNavy
) {
    Canvas(modifier = modifier) {

        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()

        drawRect(
            color = color,
            topLeft = Offset(w * 0.08f, h * 0.08f),
            size = Size(w * 0.27f, h * 0.27f),
            style = Stroke(stroke)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.17f, h * 0.17f),
            size = Size(w * 0.09f, h * 0.09f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.65f, h * 0.08f),
            size = Size(w * 0.27f, h * 0.27f),
            style = Stroke(stroke)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.74f, h * 0.17f),
            size = Size(w * 0.09f, h * 0.09f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.08f, h * 0.65f),
            size = Size(w * 0.27f, h * 0.27f),
            style = Stroke(stroke)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.17f, h * 0.74f),
            size = Size(w * 0.09f, h * 0.09f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.65f, h * 0.65f),
            size = Size(w * 0.12f, h * 0.12f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.82f, h * 0.82f),
            size = Size(w * 0.10f, h * 0.10f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.65f, h * 0.85f),
            size = Size(w * 0.09f, h * 0.09f)
        )

        drawRect(
            color = color,
            topLeft = Offset(w * 0.82f, h * 0.65f),
            size = Size(w * 0.10f, h * 0.10f)
        )
    }
}
