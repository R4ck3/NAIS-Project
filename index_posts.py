import requests
from faker import Faker
import random
from datetime import datetime

# Configuration
url = "http://localhost:9000/elasticsearch-database-service/posts"
faker = Faker()

def generate_post():
    return {
        "title": faker.sentence(),
        "content": faker.paragraph(nb_sentences=10),
        "blogId": str(random.randint(1, 10)),
        "author": random.choice( [
    'Djordje',
    'Coda',
    'Radovan',
    'Dejan',
    'Miroslav',
    'Jovo'
]),
        "createdAt": faker.date_between(start_date='-1y', end_date='today').strftime('%Y-%m-%d'),
        "category": random.choice(["Nature", "Technology", "Travel", "Food", "Lifestyle"]),
        "likes": str(random.randint(0, 1000)),
        "language": random.choice(["en", "fr", "de", "es", "it"])
    }

# Generate and send 1000 posts
for _ in range(300):
    post_data = generate_post()
    response = requests.post(url, json=post_data)
    if response.status_code != 200:
        print(f"Failed to create post: {response.status_code}, {response.text}")
    else:
        print(f"Successfully created post with ID: {response.json().get('id')}")

print("Successfully indexed 1000 posts")
