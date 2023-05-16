# Spring News App
Spring Boot application with PostgreSQL, token-based authentication and authorization, and a Dockerfile

## Prerequisites:
* Java JDK17+
* Intellij IDEA
* Docker (latest version)
* Git Bash (latest version)

## Getting Started
Follow these steps to set up and run the project:

1. Open IntelliJ IDEA.
2. Go to ***File > New > Project from Version Control.***
3. Enter the following URL: https://github.com/TemirlanZhumagulov/spring_news_app/
4. Specify the directory where you want to save the project, ensuring the path ends with \spring_news_app
5. Click on Clone
6. Register or generate token
7. Open the terminal and run the command ***docker pull postgres*** to pull the latest PostgreSQL image
8. After the official image is installed, go to the ***docker-compose.yml*** and start the services 

## How to Use the Application
To interact with the application, follow these steps:

1. Open Postman.
2. Register and obtain an access token. Without a valid token, you won't have access rights
   
   To register send a POST request to http://localhost:8080/api/v1/auth/register
   In the body section select raw and JSON and write
    ```
    {
      "full name": "your_name",
      "email": "temirlan@gmail.com",
      "password": "123"
    }
    ```
   You will receive something similar to the token, **copy it for future use**:
   
   eyJhbGciOiJIUzasdasdiJ9.eyJzdWIiOiJasdasdpcmxhbkBnbWFpbC5jb20iLCJpYXQiOjE2ODQxNzgyNDEsImV4cCI6MTY4NDE3OTY4MX0.Nn5vbasdadNeuDzD2jW56sBAg8v3D_cllLOQBLuRZoXw
3. If your token expires or becomes invalid, you can retrieve a new one by sending a request to http://localhost:8080/api/v1/auth/authenticate
   In the body section select raw and JSON and write
    ```
    {
        "email":"temirlan@gmail.com",
        "password":"123"
    }
    ```
4. Enter GET request to http://localhost:8080/api/v1/news , and go to Authroization, select the **Bearer token** and paste your token.
   
   Now you can try to send it again and if you will see 2 different news, congratulations, you did everything right!

## 4. Test other functions:
GET, POST, PUT, DELETE methods for news sources; http://localhost:8080/api/v1/news/sources/{id} 

GET, POST, PUT, DELETE methods for news; http://localhost:8080/api/v1/news/{id}

GET, POST, PUT, DELETE methods for news topics; http://localhost:8080/api/v1/news/topics/{id}

GET method for getting list of all news sources; http://localhost:8080/api/v1/news/sources

GET method for getting list of all news topics; http://localhost:8080/api/v1/news/topics

GET method for getting list of all news (with pagination); http://localhost:8080/api/v1/news

GET method for getting list of news by source id (with pagination); http://localhost:8080/api/v1/news/source/{id}

GET method for getting list of news by topic id (with pagination); http://localhost:8080/api/v1/news/topic/{id}

When you make post request you should indicate this url and don't forget about body section, as well as in PUT request! Here for each:
http://localhost:8080/api/v1/news/sources
```
{
    "name":"great source"
}
```
http://localhost:8080/api/v1/news/topics
```
{
    "name":"great topic"
}
```
http://localhost:8080/api/v1/news
```
{
    "title": "your title",
    "content": "your content",
    "source": {
        "id": "3",
        "name": "great source"
    },
    "topic": {
        "id": "3",
        "name": "great topic"
    },
    "createdDate": "2023-05-16T03:34:38.2477707"
}
```
// If you don't specify one field, it will be empty
### Scheduled statistical task
Go to the ***src > main.java.strong.news > service  > NewsStatisticsService***

You can change your cron to the convinent time 

The pattern is:
```
second, minute, hour, day, month, weekday
````
Or you can do it here: http://www.cronmaker.com/

You can then run the app again using: docker compose up

After that, you will see the statistics.txt file in the src folder

< Thank you for your attention, and looking forward to the next round of the interview!
