
import csv
import joblib
from pathlib import Path
from urllib.parse import urlsplit

from sklearn.model_selection import GroupShuffleSplit
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import (
    accuracy_score,
    classification_report,
    confusion_matrix
)

BASE_DIR = Path(__file__).resolve().parent
DATA_FILE = BASE_DIR / "qr_decoded_dataset.csv"
MODEL_FILE = BASE_DIR / "qr_url_model.joblib"

# Load Anjali's decoded QR dataset
with open(DATA_FILE, newline="", encoding="utf-8") as f:
    rows = list(csv.DictReader(f))

# Remove duplicates and validate labels
unique = {}

for row in rows:
    url = row["url"].strip()
    label = row["label"].strip().lower()

    if not url or label not in ("benign", "malicious"):
        continue

    if url in unique and unique[url] != label:
        raise ValueError("Conflicting labels: " + url)

    unique[url] = label

urls = list(unique.keys())
labels = [1 if unique[url] == "malicious" else 0 for url in urls]

# Keep the same hostname in only one split
hosts = [
    urlsplit(url).hostname or url
    for url in urls
]

splitter = GroupShuffleSplit(
    n_splits=1,
    test_size=0.20,
    random_state=42
)

train_idx, test_idx = next(
    splitter.split(urls, labels, groups=hosts)
)

X_train = [urls[i] for i in train_idx]
X_test = [urls[i] for i in test_idx]
y_train = [labels[i] for i in train_idx]
y_test = [labels[i] for i in test_idx]

# Train URL-based classifier
model = Pipeline([
    (
        "vectorizer",
        TfidfVectorizer(
            analyzer="char",
            ngram_range=(3, 5),
            min_df=2,
            sublinear_tf=True
        )
    ),
    (
        "classifier",
        LogisticRegression(
            max_iter=2000,
            class_weight="balanced",
            random_state=42
        )
    )
])

print("Training QR URL detection model...")

model.fit(X_train, y_train)

predictions = model.predict(X_test)

print("\nQR MODEL EVALUATION")
print("------------------------")
print("Total unique URLs:", len(urls))
print("Training samples:", len(X_train))
print("Testing samples:", len(X_test))
print(
    "Accuracy:",
    round(accuracy_score(y_test, predictions) * 100, 2),
    "%"
)

print("\nClassification Report:")
print(
    classification_report(
        y_test,
        predictions,
        labels=[0, 1],
        target_names=["Benign", "Malicious"],
        zero_division=0
    )
)

print("Confusion Matrix:")
print(confusion_matrix(y_test, predictions, labels=[0, 1]))

# Save the evaluated prototype
joblib.dump(model, MODEL_FILE)

print("\nModel saved:", MODEL_FILE)
print("Training and evaluation completed.")
