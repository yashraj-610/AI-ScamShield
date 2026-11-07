
from pathlib import Path

import joblib
from fastapi import FastAPI
from pydantic import BaseModel

from qr_api import app as qr_app

BASE_DIR = Path(__file__).resolve().parent

MESSAGE_MODEL_PATH = (
    BASE_DIR
    / "message-detection-python"
    / "scam_message_model_v2.joblib"
)

message_model = joblib.load(MESSAGE_MODEL_PATH)

app = FastAPI(title="AI-ScamShield Unified Backend")


class MessageRequest(BaseModel):
    message: str


@app.get("/")
def home():
    return {
        "status": "AI-ScamShield Unified Backend running",
        "features": [
            "message_detection",
            "phone_caller_text_analysis",
            "qr_url_detection",
            "upi_qr_validation"
        ]
    }


@app.post("/check-message")
def check_message(request: MessageRequest):

    message = request.message.strip()

    if not message:
        return {
            "error": "Please enter a message."
        }

    probabilities = message_model.predict_proba(
        [message]
    )[0]

    classes = message_model.classes_

    scores = {
        str(label): round(
            float(probability) * 100, 2
        )
        for label, probability in zip(
            classes, probabilities
        )
    }

    category = max(scores, key=scores.get)

    return {
        "category": category.capitalize(),
        "confidence": scores[category],
        "scores": scores,
        "note": "Prototype prediction, not a guarantee."
    }


# Reuse the existing working QR backend
for route in qr_app.routes:
    if getattr(route, "path", None) == "/check-qr":
        app.router.routes.append(route)


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(
        app,
        host="127.0.0.1",
        port=8765
    )
