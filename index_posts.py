import requests
from faker import Faker
import uuid
import random

# Konfiguracija
url = "http://localhost:9000/elasticsearch-database-service/posts"
faker = Faker()

def generate_post():
    return {
        "authorId": random.randint(1, 5),
        "blogId": random.randint(1, 10),
        "content": faker.paragraph(nb_sentences=15),
        "createdAt": faker.date_time().isoformat()
    }

# Generisanje i slanje 1000 postova
for _ in range(1000):
    post_data = generate_post()
    response = requests.post(url, json=post_data)
    if response.status_code != 200:
        print(f"Failed to create post: {response.status_code}, {response.text}")
    else:
        print(f"Successfully created post with ID: {response.json()['id']}")

print("Successfully indexed 1000 posts")
