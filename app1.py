def assess_risk(details):
  """Custom validation and risk assessment logic for UPI QRs."""
  risks = []
  risk_level = "LOW"

  vpa = details.get("pa")
  name = details.get("pn")
  amount = details.get("am")

  
  if not vpa:
    return "HIGH", ["Critical: UPI ID (pa) गहाळ आहे!"]

  
  if "@" not in vpa:
    risks.append("Suspicious: UPI ID is in wrong format ('@' symbol).")
    risk_level = "HIGH"

  
  scam_keywords = ["lottery", "win", "prize", "lucky", "refund", "cashback"]
  vpa_lower = vpa.lower()
  name_lower = (name or "").lower()

  for word in scam_keywords:
    if word in vpa_lower or word in name_lower:
      risks.append(
          f"High Risk: ('{word}')"
          " done."
      )
      risk_level = "HIGH"

  
  trusted_handles = ["@paytm", "@ybl", "@okaxis", "@oksbi", "@icici"]
  is_trusted = any(vpa.endswith(handle) for handle in trusted_handles)
  if not is_trusted:
    risks.append(
        "Moderate: this upi id is not connected to any bank"
        " not able to see."
    )
    if risk_level != "HIGH":
      risk_level = "MODERATE"

  
  if not name:
    risks.append("Moderate: user (Payee) name is hidden.")
    if risk_level != "HIGH":
      risk_level = "MODERATE"

  
  if amount:
    try:
      amt_float = float(amount)
      if amt_float > 50000:
        risks.append(
            f"Moderate: amt is to big ({amt_float} INR)."
        )
        if risk_level != "HIGH":
          risk_level = "MODERATE"
    except ValueError:
      risks.append("Suspicious: Amount .")
      risk_level = "HIGH"
  else:
    risks.append("Info: Open Amount QR code.")

  if not risks:
    risks.append("Safe: QR is safe.")

  return risk_level, risks