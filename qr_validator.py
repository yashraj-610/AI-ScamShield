"""
Original Contribution: UPI QR Decoder and Risk Assessment Tool
Author: Personal AI Collaborator & Developer
Description: Decodes UPI QR codes from images, parses payment metadata,
and applies custom validation and risk assessment heuristics.
"""

import cv2
from urllib.parse import parse_qs, urlparse


def decode_qr_code(image_path):
  """Reads an image file and decodes any QR code present using OpenCV."""
  try:
    img = cv2.imread(image_path)
    if img is None:
      return None, "Error: Could not load image. Check the file path."

    detector = cv2.QRCodeDetector()
    data, bbox, _ = detector.detectAndDecode(img)

    if data:
      return data, "Success: QR code decoded."
    else:
      return None, "Error: No QR code found in the image."
  except Exception as e:
    return None, f"Exception occurred during decoding: {str(e)}"


def parse_upi_string(upi_data):
  """Parses UPI URL parameters (Payee Address, Name, Amount, Currency, etc.)."""
  parsed_url = urlparse(upi_data)

  if parsed_url.scheme != "upi" or parsed_url.netloc != "pay":
    return None, "Invalid UPI URL format (Not a standard UPI string)."

  query_params = parse_qs(parsed_url.query)

  # Extract standard fields
  details = {
      "pa": query_params.get("pa", [None])[0],  # Payee Address (UPI ID)
      "pn": query_params.get("pn", [None])[0],  # Payee Name
      "am": query_params.get("am", [None])[0],  # Amount
      "cu": query_params.get("cu", ["INR"])[0],  # Currency
      "tr": query_params.get("tr", [None])[0],  # Transaction Reference ID
  }
  return details, "Parsed successfully."


def assess_risk(details):
  """Implements custom validation and risk assessment logic."""
  risks = []
  risk_level = "LOW"

  vpa = details.get("pa")
  name = details.get("pn")
  amount = details.get("am")

  # 1. Check if UPI ID exists
  if not vpa:
    return "HIGH", ["Critical: Missing Payee UPI ID (pa)."]

  # 2. VPA Format Validation (must contain '@')
  if "@" not in vpa:
    risks.append("Suspicious: UPI ID format is invalid (missing '@').")
    risk_level = "HIGH"

  # 3. Check for missing merchant/payee name
  if not name:
    risks.append(
        "Moderate: Payee Name (pn) is missing. Recipient identity is hidden."
    )
    if risk_level != "HIGH":
      risk_level = "MODERATE"

  # 4. Amount validation heuristic
  if amount:
    try:
      amt_float = float(amount)
      if amt_float > 50000:
        risks.append(
            f"Moderate: High transaction amount requested ({amt_float} INR)."
        )
        if risk_level != "HIGH":
          risk_level = "MODERATE"
    except ValueError:
      risks.append("Suspicious: Invalid amount format detected.")
      risk_level = "HIGH"
  else:
    risks.append("Info: Open amount QR code (user enters amount manually).")

  if not risks:
    risks.append("Safe: Standard merchant structure with verified VPA format.")

  return risk_level, risks


def main():
  print("=== UPI QR Code Validator & Risk Assessment ===")
  image_path = input(
      "Enter the file name or path to your QR code image (e.g., test_qr.png): "
  )

  # Step 1: Decode QR
  data, message = decode_qr_code(image_path)
  print(f"\n[QR Decode Status] {message}")

  if not data:
    return

  print(f"Raw QR Data: {data}")

  # Step 2: Parse UPI Details
  details, parse_msg = parse_upi_string(data)
  if not details:
    print(f"[Parsing Error] {parse_msg}")
    return

  print("\n--- Extracted Payment Details ---")
  for key, value in details.items():
    print(f"{key.upper()}: {value or 'Not Provided'}")

  # Step 3: Risk Assessment
  risk_level, reasons = assess_risk(details)
  print(f"\n--- Risk Assessment ---")
  print(f"Risk Level: {risk_level}")
  print("Reasons / Analysis:")
  for reason in reasons:
    print(f" - {reason}")


if __name__ == "__main__":
  main()
  