
import cv2
import csv
from pathlib import Path
from collections import Counter

# Dataset location
BASE_DIR = Path(__file__).resolve().parent
DATASET_DIR = BASE_DIR / "Datasets" / "QR"
OUTPUT_FILE = BASE_DIR / "qr_decoded_dataset.csv"

# Find QR images
image_files = sorted(DATASET_DIR.rglob("*.png"))

detector = cv2.QRCodeDetector()

results = Counter()
decoded_rows = []

print("AI-ScamShield QR Dataset Analysis")
print("--------------------------------")

print(f"Total images found: {len(image_files)}")

if not image_files:
    print("ERROR: No QR images found.")
    print(f"Check dataset location: {DATASET_DIR}")
    raise SystemExit(1)

# Process each QR image
for index, image_path in enumerate(image_files, start=1):

    filename = image_path.name.lower()

    # Identify label from the filename
    if "malicious" in filename:
        label = "malicious"
    elif "benign" in filename:
        label = "benign"
    else:
        label = "unknown"

    # Read image
    image = cv2.imread(str(image_path))

    if image is None:
        results[f"{label}_unreadable"] += 1
        continue

    # Decode QR code
    try:
        decoded_data, _, _ = detector.detectAndDecode(image)
    except cv2.error:
        results[f"{label}_failed"] += 1
        continue

    if decoded_data:
        results[f"{label}_decoded"] += 1

        decoded_rows.append({
            "filename": filename,
            "label": label,
            "url": decoded_data
        })
    else:
        results[f"{label}_failed"] += 1

    # Show progress
    if index % 100 == 0:
        print(f"Processed {index}/{len(image_files)} images")

# Save decoded dataset
with open(
    OUTPUT_FILE,
    "w",
    newline="",
    encoding="utf-8"
) as file:

    writer = csv.DictWriter(
        file,
        fieldnames=["filename", "label", "url"]
    )

    writer.writeheader()
    writer.writerows(decoded_rows)

# Print results
print("\nQR DATASET RESULTS")
print("------------------")

for key, value in sorted(results.items()):
    print(f"{key}: {value}")

print(f"\nTotal images: {len(image_files)}")
print(f"Successfully decoded: {len(decoded_rows)}")
print(f"Failed or unreadable: {len(image_files) - len(decoded_rows)}")

print(f"\nDataset saved to: {OUTPUT_FILE}")

print("\nSample decoded QR contents:")

for row in decoded_rows[:6]:
    print(f"{row['label']}: {row['url'][:150]}")

print("\nQR dataset processing completed.")
