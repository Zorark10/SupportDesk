# SupportDesk

A backend REST API for managing support tickets, users, ticket comments, priorities, and ticket status workflows.

SupportDesk provides JWT-based authentication and role-based authorization, allowing users to manage their own tickets and comments while administrators have elevated access across the system.

## Features

- JWT-based authentication
- Role-based authorization with `USER` and `ADMIN` roles
- Secure password hashing using BCrypt
- User management
- Ticket creation and management
- Ticket ownership-based access control
- Ticket priority management
- Controlled ticket status transitions
- Comment creation and management
- Comment ownership-based authorization
- Custom exception handling
- DTO-based ticket responses
- RESTful API architecture
- JPA/Hibernate database persistence

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Maven**

## Authentication & Authorization

SupportDesk uses JWT tokens for authentication.

After successful authentication, the client sends the JWT with protected requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

Authorization is based on the user's role and ownership of resources.

### Roles

| Role | Access |
|------|--------|
| `USER` | Manage their own tickets and comments |
| `ADMIN` | Elevated access across users, tickets, and comments |

Normal users cannot access or modify resources belonging to other users, while administrators have elevated access.

## API Endpoints

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/user` | Create a user |
| `GET` | `/user/{id}` | Get a user by ID |
| `GET` | `/user` | Get all users |
| `DELETE` | `/user/delete/{id}` | Delete a user |
| `PATCH` | `/user/update/{id}` | Update a user |

Getting all users requires the `ADMIN` role.

### Tickets

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/ticket` | Create a ticket |
| `GET` | `/ticket/{id}` | Get a ticket by ID |
| `GET` | `/ticket` | Get accessible tickets |
| `DELETE` | `/ticket/delete/{id}` | Delete a ticket |
| `PATCH` | `/ticket/update/{id}` | Update a ticket |
| `PATCH` | `/ticket/{ticketId}/status` | Update ticket status |

Ticket access is restricted based on ownership and role.

### Comments

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/ticket/{ticketId}/comments` | Create a comment |
| `GET` | `/comments` | Get all comments |
| `GET` | `/comment/{commentId}` | Get a comment |
| `DELETE` | `/comment/{commentId}/delete` | Delete a comment |
| `PATCH` | `/comment/{commentId}/update` | Update a comment |
| `GET` | `/ticket/{ticketId}/comments` | Get comments for a ticket |

Comment modification and deletion are restricted based on ownership and administrator privileges.

## Database

The application uses **MySQL** with **Spring Data JPA/Hibernate** for persistence.

The main entities are:

- **User** — stores user information, roles, and credentials
- **Ticket** — stores support requests, priorities, statuses, and ownership
- **Comment** — stores comments associated with users and tickets

## Security

The application implements several security measures:

- Passwords are hashed using BCrypt before being stored.
- Authentication is handled using JWT tokens.
- Protected endpoints require authentication.
- Administrative endpoints use role-based authorization.
- Users are restricted from accessing resources belonging to other users.
- Password fields are excluded from JSON responses.
