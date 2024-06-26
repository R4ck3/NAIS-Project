import requests
from faker import Faker
import random

# Configuration
url = "http://localhost:9000/elasticsearch-database-service/blogs.json/createBlog"
faker = Faker()

def generate_blog(blog_id):
    return {
        "authorId": str(random.randint(1, 20)),
        "blogId": str(blog_id),
        "category": random.choice([
    'Travel',
    'Health and Fitness',
    'Food and Recipes',
    'Technology',
    'Personal Finance',
    'Lifestyle',
    'Fashion',
    'Beauty',
    'Parenting',
    'DIY and Crafts',
    'Education',
    'Career and Business',
    'Books and Literature',
    'Movies and TV Shows',
    'Music',
    'Gaming',
    'Sports',
    'Home and Garden',
    'Photography',
    'Pets'
]),
        "title": faker.word().capitalize(),
        "description": faker.paragraph(nb_sentences=7),
        "createdAt": faker.date_time_between(start_date='-1y', end_date='now').isoformat(),
        "country": random.choice( [
    'United States',
    'Canada',
    'United Kingdom',
    'Australia',
    'Germany',
    'France',
    'Italy',
    'Spain',
    'Japan',
    'China',
    'India',
    'Brazil',
    'Mexico',
    'Russia',
    'South Africa',
    'South Korea',
    'New Zealand',
    'Argentina',
    'Netherlands',
    'Sweden'
])
    }

# Generate and send 1000 blogs
for i in range(1000):
    blog_data = generate_blog(i)
    response = requests.post(url, json=blog_data)
    if response.status_code != 200:
        print(f"Failed to create blog: {response.status_code}, {response.text}")
    else:
        print(f"Successfully created blog with ID: {response.json().get('id')}")

print("Successfully indexed 1000 blogs")
