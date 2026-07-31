# 🔗 URL Shortening Service

A simple and efficient URL shortening service built with Spring Boot. It lets users turn long URLs into short, shareable links, redirects visitors to the original address, and tracks how many times each link has been clicked.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

## 📋 About

This project is a RESTful API that lets users shorten long URLs into unique short codes, redirects visitors from the short link to the original URL, and keeps track of how many times each link has been clicked. It's built on Spring Boot 4.1, Spring Data JPA, and PostgreSQL.

## ✨ Features

- 🔗 **URL shortening** — generates a unique, 9-character Base62 short code for any given URL
- 🚀 **Automatic redirection** — redirects from the short code to the original URL with a `302 Found` response
- 📊 **Click tracking** — automatically increments a click counter on every redirect
- ✏️ **Full CRUD operations** — create, read, update, and delete shortened URLs
- ✅ **Input validation** — incoming URLs are validated with `jakarta.validation`
- ⚠️ **Centralized error handling** — clean, structured error responses via `@ControllerAdvice`
- 🗄️ **PostgreSQL integration** — persistent storage powered by Spring Data JPA

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Boot 4.1.0 | Application framework |
| Spring Data JPA | Database access layer |
| Spring Web (MVC) | REST API |
| Spring Validation | Request input validation |
| PostgreSQL | Database |
| Lombok | Reducing boilerplate code |
| Maven | Project management and build |

## 📁 Project Structure

```
UrlShorteningService/
├── src/main/java/com/caviding/urlshorteningservice/
│   ├── controller/
│   │   ├── UrlController.java        # CRUD endpoints for URLs
│   │   └── RedirectController.java   # Redirects from short code to original URL
│   ├── service/
│   │   └── UrlService.java           # Core business logic
│   ├── repository/
│   │   └── UrlRepository.java        # JPA repository interface
│   ├── entity/
│   │   └── Url.java                  # Database entity
│   ├── dto/
│   │   ├── CreateUrlRequest.java     # Request DTO
│   │   └── UrlResponse.java          # Response DTO
│   ├── exception/
│   │   └── UrlNotFoundException.java # Custom exception
│   ├── handler/
│   │   └── GlobalExceptionHandler.java # Global error handling
│   ├── util/
│   │   └── ShortCodeGenerator.java   # Short code generator (Base62)
│   └── starter/
│       └── UrlShorteningServiceApplication.java # Main entry point
└── src/main/resources/
    └── application.yml               # Configuration file
```

## ⚙️ Setup & Running

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or the bundled `mvnw` wrapper)
- PostgreSQL (local or containerized)

### 1. Clone the repository

```bash
git clone https://github.com/caviding/url-shortening-service.git
cd url-shortening-service
```

### 2. Set up the database

Create the database in PostgreSQL:

```sql
CREATE DATABASE url_shortener;
```

### 3. Configure the application

Update `src/main/resources/application.yml` with your own database settings:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/url_shortener
    username: postgres
    password: admin
server:
  port: 8080
```

> ⚠️ For security, it's recommended to use environment variables for credentials in production instead of hardcoding them in the config file.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

By default, the application will start at `http://localhost:8080`.

## 📡 API Endpoints

Base URL: `/api/v1/urls`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/urls` | Creates a new shortened URL |
| `GET` | `/api/v1/urls` | Returns a list of all URLs |
| `GET` | `/api/v1/urls/details/{id}` | Returns a specific URL by ID |
| `PUT` | `/api/v1/urls/{id}` | Updates an existing URL |
| `DELETE` | `/api/v1/urls/{id}` | Deletes a URL |
| `GET` | `/{shortCode}` | Redirects to the original URL (302) |

### Example: Creating a new short URL

**Request**

```http
POST /api/v1/urls
Content-Type: application/json

{
  "originalUrl": "https://www.example.com/very/long/url/path"
}
```

**Response**

```json
{
  "id": 1,
  "originalUrl": "https://www.example.com/very/long/url/path",
  "shortCode": "aZ3kLmN9p",
  "shortUrl": "http://localhost:8080/aZ3kLmN9p",
  "clickCount": 0
}
```

### Example: Redirecting via a short link

```http
GET /aZ3kLmN9p
```

This request redirects the user to the original URL with a `302 Found` status and increments the `clickCount` by 1.

## 🧪 Running Tests

```bash
./mvnw test
```

## 🗺️ Roadmap

- [ ] User authentication with JWT
- [ ] Expiration dates for links
- [ ] Custom short code support
- [ ] Analytics dashboard for click statistics
- [ ] Docker and `docker-compose` support
- [ ] Swagger/OpenAPI documentation

## 🤝 Contributing

Contributions are welcome! Please open an issue first to discuss what you'd like to change, or submit a pull request directly.

1. Fork the repository
2. Create a new branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## 👤 Author

**caviding**
GitHub: [@caviding](https://github.com/caviding)
