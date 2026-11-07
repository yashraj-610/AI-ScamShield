
from pathlib import Path
from urllib.parse import urlparse

import cv2
import joblib
import numpy as np

from fastapi import FastAPI, Request, HTTPException

from qr_validator import parse_upi_string, assess_risk

BASE_DIR = Path(__file__).resolve().parent

model = joblib.load(BASE_DIR / "qr_url_model.joblib")

app = FastAPI(title="AI-ScamShield QR Detection")


@app.get("/")
def home():
    return {"status": "QR Detection API is running"}


@app.post("/check-qr")
async def check_qr(request: Request):

    content_type = request.headers.get(
        "content-type", ""
    ).split(";")[0].lower().strip()

    if content_type not in (
        "image/png", "image/jpeg", "image/webp"
    ):
        raise HTTPException(
            status_code=415,
            detail="Upload a PNG, JPEG or WebP image"
        )

    image_bytes = await request.body()

    if not image_bytes:
        raise HTTPException(
            status_code=400,
            detail="Empty image"
        )

    if len(image_bytes) > 5 * 1024 * 1024:
        raise HTTPException(
            status_code=413,
            detail="Image exceeds 5 MB"
        )

    image_array = np.frombuffer(
        image_bytes, dtype=np.uint8
    )

    image = cv2.imdecode(
        image_array, cv2.IMREAD_COLOR
    )

    if image is None:
        raise HTTPException(
            status_code=400,
            detail="Invalid image"
        )

    detector = cv2.QRCodeDetector()
    qr_data, _, _ = detector.detectAndDecode(image)

    if not qr_data:
        raise HTTPException(
            status_code=422,
            detail="No readable QR code found"
        )

    # Handle UPI payment QR codes
    if qr_data.lower().startswith("upi://pay"):
        details, message = parse_upi_string(qr_data)

        if details is None:
            return {
                "type": "upi",
                "risk_level": "NOT_ASSESSED",
                "message": message
            }

        risk, reasons = assess_risk(details)

        return {
            "type": "upi",
            "risk_level": risk,
            "payment_details": details,
            "reasons": reasons,
            "note": (
                "Rule-based checks only. "
                "Recipient ownership is not verified."
            )
        }

    # Handle website QR codes using Anjali's model
    parsed = urlparse(qr_data)

    if (
        parsed.scheme.lower() in ("http", "https")
        and parsed.hostname
    ):
        probabilities = model.predict_proba(
            [qr_data]
        )[0]

        classes = list(model.classes_)
        malicious_index = classes.index(1)

        malicious_probability = float(
            probabilities[malicious_index]
        )

        prediction = (
            "Malicious"
            if malicious_probability >= 0.5
            else "Benign"
        )

        return {
            "type": "url",
            "url": qr_data,
            "prediction": prediction,
            "malicious_probability": round(
                malicious_probability * 100, 2
            ),
            "note": (
                "Prototype machine-learning prediction. "
                "A benign result does not guarantee safety."
            )
        }

    return {
        "type": "other",
        "decoded_data": qr_data,
        "risk_level": "NOT_ASSESSED",
        "message": "Unsupported QR content"
    }
