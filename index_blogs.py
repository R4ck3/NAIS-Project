import requests
from faker import Faker
import random

# Configuration
url = "http://localhost:9000/elasticsearch-database-service/blogs.json/createBlog"
faker = Faker()

def generate_blog(blog_id):
    return {
        "authorId": str(random.randint(1, 5)),
        "blogId": str(blog_id),
        "category": random.choice([
    'Travel',
    'Health and Fitness',
    'Food and Recipes',
    'Technology',
    'Personal Finance',
    'Pets'
]),
        "title": faker.paragraph(nb_sentences=1),
        "description": faker.paragraph(nb_sentences=6),
        "createdAt": faker.date_time_between(start_date='-1y', end_date='now').isoformat(),
        "country": random.choice( [
    'Serbia',
    'Canada',
    'Niger',
    'Australia',
    'Sweden'
])
    }

# Generate and send 1000 blogs
for i in range(5000):
    blog_data = generate_blog(i)
    response = requests.post(url, json=blog_data)
    if response.status_code != 200:
        print(f"Failed to create blog: {response.status_code}, {response.text}")
    else:
        print(f"Successfully created blog with ID: {response.json().get('id')}")

print("Successfully indexed 1000 blogs")
