import re
from urllib.parse import urlparse


BLACKLIST = {
    "fake-login.com",
    "scam-example.com",
    "bad-example.com"
}



def extract_urls(message):
    pattern = r'https?://[^\s]+|www\.[^\s]+'
    return re.findall(pattern, message)



def get_domain(url):
    if not url.startswith(("http://", "https://")):
        url = "http://" + url

    parsed = urlparse(url)

    domain = parsed.netloc.lower()

   
    domain = domain.split(":")[0]

    return domain



def check_suspicious_words(url):
    suspicious_words = [
        "login",
        "verify",
        "urgent",
        "password",
        "account",
        "claim",
        "winner",
        "free"
    ]

    found_words = []

    for word in suspicious_words:
        if word in url.lower():
            found_words.append(word)

    return found_words



def check_https(url):
    return url.startswith("https://")



def contains_ip(url):
    pattern = r'https?://(?:\d{1,3}\.){3}\d{1,3}'
    return bool(re.search(pattern, url))



def contains_at_symbol(url):
    return "@" in url



def is_long_url(url):
    return len(url) > 100



def check_blacklist(url):
    domain = get_domain(url)
    return domain in BLACKLIST



def analyze_url(url):

    score = 0
    reasons = []

  
    if not check_https(url):
        score += 2
        reasons.append("URL does not use HTTPS")

 
    if contains_ip(url):
        score += 3
        reasons.append("URL uses an IP address")


    if contains_at_symbol(url):
        score += 3
        reasons.append("URL contains @ symbol")

    if is_long_url(url):
        score += 2
        reasons.append("URL is unusually long")

    suspicious_words = check_suspicious_words(url)

    if suspicious_words:
        score += len(suspicious_words)

        reasons.append(
            "Suspicious words: " + ", ".join(suspicious_words)
        )

    
    if check_blacklist(url):
        score += 5
        reasons.append("Domain is in blacklist")

    if score >= 5:
        risk = "HIGH"

    elif score >= 3:
        risk = "MEDIUM"

    else:
        risk = "LOW"

    return risk, score, reasons
      
       