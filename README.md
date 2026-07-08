# Freelancer Project Manager — Backend API

A full-stack SaaS application for freelancers to manage clients, 
projects, payments, and generate AI-powered proposals.

## Tech Stack
- **Backend:** Java 21, Spring Boot 3, Spring Security
- **Database:** PostgreSQL
- **Auth:** JWT (stateless)
- **AI:** Google Gemini 1.5 Flash API
- **Deployment:** Railway

## Features
- JWT authentication with role-based access
- Multi-user client & project management
- Payment tracking (paid/pending)
- AI proposal generator (Gemini API)
- Free/Pro subscription tiers

## Architecture
Android App (Kotlin/Jetpack Compose)
↕ REST API (JWT)
Spring Boot Backend (Railway)
↕
PostgreSQL Database
↕
Gemini AI API

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login, returns JWT |

### Clients
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/clients | Get all clients |
| POST | /api/clients | Create client |
| PUT | /api/clients/{id} | Update client |
| DELETE | /api/clients/{id} | Delete client |

### Projects
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/projects | Get all projects |
| POST | /api/projects | Create project |
| PATCH | /api/projects/{id}/status | Update status |

### Payments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/payments | Log payment |
| GET | /api/payments/project/{id} | Get by project |
| PATCH | /api/payments/{id}/paid | Mark as paid |

### AI
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/ai/proposal | Generate full proposal (Pro) |
| POST | /api/ai/proposal/preview | Preview proposal (Free) |

## Setup & Running Locally

1. Clone the repo
2. Copy `application.properties.example` → `application.properties`
3. Fill in your database and API keys
4. Run: `mvn spring-boot:run`

## Android App
[Link to Android repo here]
