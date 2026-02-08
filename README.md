# Library Management System

A comprehensive Library Management System built with **Spring Boot**, **MySQL**, and **Thymeleaf**. This application provides distinct functionalities for Administrators and Users, allowing for efficient management of books, authors, and personal reading lists.

## 📖 Project Description

The Library Management System is designed to streamline the operations of a library. It features a secure authentication system using JWT (JSON Web Tokens) and offers a responsive user interface.
- **Administrators** can manage the entire catalog of books and authors, as well as oversee user accounts.
- **Users** can browse the collection, create personal favorite lists, and track books they have read or intend to read.

## ✨ Key Features

### 🔐 Authentication & Security
- Secure Login and Registration system.
- **JWT (JSON Web Token)** based authentication.
- Role-based access control (**ADMIN** vs **USER**).

### 📚 Book & Author Management (Admin)
- **Add, Update, Delete** Books.
- **Add, Update, Delete** Authors.
- View detailed lists of all books and authors.

### 👤 User Features
- **Browse** the library catalog.
- **Search** for books and authors.
- Manage a personal **Favorite List**.
- Manage a **Read List** (books read or to be read).
- View personal profile information.

### 🛠️ Admin Dashboard
- User management (view all users, delete users).
- System-wide statistics (optional implication).

## 📸 Screenshots

### Start Page (Index)


### Dashboard


## 🛠 Technologies Used

- **Backend:** Java 17, Spring Boot 3.x
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security, JWT
- **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript
- **Build Tool:** Maven

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

- **Java JDK 17** or higher
- **Maven** 3.x
- **MySQL** Server

### Installation or Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/burakbaslik/librarymanagement.git
    cd librarymanagement
    ```

2.  **Database Configuration:**
    - Create a MySQL database named `system_management`.
    - The application uses environment variables for security. You must set them or update `application.properties` for local testing.

    **Environment Variables:**
    - `DB_USERNAME`: Your MySQL username (e.g., root)
    - `DB_PASSWORD`: Your MySQL password
    - `JWT_SECRET_KEY`: A strong secret key for token generation

3.  **Build the project:**
    ```bash
    mvn clean install
    ```

4.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    Or run the generated JAR file:
    ```bash
    java -jar target/librarymanagement-0.0.1-SNAPSHOT.jar
    ```

5.  **Access the application:**
    Open your browser and navigate to: `http://localhost:6060`

## 👤 Usage / Default Credentials

The application initializes with a default Admin user for testing purposes.

- **Username:** `cc`
- **Password:** `1234`

> **Note:** It is highly recommended to change these credentials or create a new admin user after the initial login.

## 📡 API Endpoints (Brief)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| POST | `/auth/register` | Register a new user | Public |
| POST | `/auth/generateToken` | Login & Get Token | Public |
| GET | `/books` | List all books | Public |
| POST | `/books` | Add a new book | Admin |
| GET | `/auth/users` | List all users | Admin |

## 📄 License

[MIT](https://choosealicense.com/licenses/mit/)
