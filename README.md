# Retreat Meal Manager

A comprehensive meal planning and management system for retreat centers, built with Spring Boot and Thymeleaf.

## Overview

Retreat Meal Manager helps retreat centers manage sessions, menus, meal preparation, and purchasing with age-specific portion calculations and automated list generation.

## Features

- **Session Management**: Create and manage retreat sessions with multiple groups
- **Menu Planning**: Build reusable menus with ingredients and age-specific portions
- **Meal Period Assignment**: Assign menus to daily meal periods (Breakfast, Lunch, Dinner)
- **Prep List Generation**: Automatic kitchen prep sheets with ingredient quantities
- **Purchase List Generation**: Consolidated shopping lists aggregated across sessions
- **Group Management**: Track multiple groups with different arrival/departure dates
- **Age-Specific Portions**: Different portion sizes for adults, youth, and kids

## Technology Stack

- **Backend**: Spring Boot 4.0.0
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: PostgreSQL 15
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Java Version**: 21

## Architecture

The application follows a **Layered Modular Monolith** pattern with clean separation of concerns:

```
├── Presentation Layer (Controllers & Web UI)
├── Application Layer (Services)
├── Domain Layer (Entities & Business Logic)
└── Infrastructure Layer (Repositories & Technical Services)
```

### Core Modules

1. **Sessions**: Manage retreat sessions, groups, and days
2. **MealPeriod**: Handle daily meal time slots
3. **Menu**: Create and manage reusable menus
4. **MealPrep**: Generate kitchen preparation lists
5. **Purchasing**: Generate consolidated purchase lists
6. **Auth**: Admin authentication and authorization

## Prerequisites

- **Java 21** or higher
- **PostgreSQL 15** or higher
- **Maven 3.9+** (included via Maven wrapper)

## Installation

### 1. Install PostgreSQL

#### Using Homebrew (macOS)

```bash
# Install PostgreSQL
brew install postgresql@15

# Start PostgreSQL service
brew services start postgresql@15

# Add PostgreSQL to your PATH
echo 'export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

#### Using Docker

```bash
docker run --name retreat-postgres \
  -e POSTGRES_PASSWORD=your_password \
  -e POSTGRES_DB=retreatdb \
  -p 5432:5432 \
  -d postgres:15
```

### 2. Create Database

```bash
# Create the database
createdb retreatdb

# Verify database was created
psql -l
```

### 3. Configure Database Connection

The application is pre-configured to connect to PostgreSQL. If you need to change credentials, edit:

**File**: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/retreatdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### Start the Application

```bash
./mvnw spring-boot:run
```

The application will:
1. Connect to PostgreSQL
2. Automatically create all database tables
3. Start the web server on port 8080

### Access the Application

Open your browser and navigate to:

```
http://localhost:8080
```

## Database Management

### Connect to PostgreSQL

```bash
# Connect to the database
psql -d retreatdb

# Useful commands inside psql:
\dt              # List all tables
\d table_name    # Describe a table structure
SELECT * FROM sessions;   # Query data
\q               # Quit psql
```

### View Database Tables

The application creates the following tables:

- `sessions` - Retreat session information
- `days` - Daily records for each session
- `groups` - Groups attending retreats
- `meal_periods` - Meal time slots (Breakfast/Lunch/Dinner)
- `menus` - Menu definitions
- `menu_items` - Items within menus
- `ingredients` - Ingredients with age-specific portions
- `prep_lists` - Kitchen preparation lists
- `purchase_lists` - Shopping lists
- `purchase_list_items` - Items in shopping lists
- `users` - Admin users

### PostgreSQL Service Management

```bash
# Start PostgreSQL
brew services start postgresql@15

# Stop PostgreSQL
brew services stop postgresql@15

# Restart PostgreSQL
brew services restart postgresql@15

# Check status
brew services list
```

## Usage Guide

### 1. Create Ingredients

1. Navigate to **Ingredients** (`/menus/ingredients`)
2. Click **Create New Ingredient**
3. Enter:
   - Ingredient name (e.g., "Flour", "Eggs", "Milk")
   - Unit of measure (e.g., "cups", "lbs", "count")
   - Adult portion (e.g., 2.0)
   - Youth portion (e.g., 1.5)
   - Kid portion (e.g., 1.0)

### 2. Build Menus

1. Navigate to **Menus** (`/menus`)
2. Click **Create New Menu**
3. Enter menu name (e.g., "Breakfast - Pancakes & Eggs")
4. Add menu items by selecting ingredients

### 3. Create a Session

1. Navigate to **Sessions** (`/sessions`)
2. Click **Create New Session**
3. Enter:
   - Session name (e.g., "Summer Youth Retreat 2025")
   - Start date
   - End date
4. System automatically creates days with 3 meal periods each

### 4. Add Groups

1. View a session
2. Click **Add Group**
3. Enter group details:
   - Group name
   - Adult count
   - Youth count
   - Kid count
   - Arrival and departure dates

### 5. Assign Menus to Meal Periods

1. View a session
2. For each meal period, click **Assign Menu**
3. Select a menu from your created menus

### 6. Generate Prep Lists

1. View a session
2. Click **View Prep List** for any day
3. Click **Generate Prep List** if not yet created
4. Print or save the prep list for kitchen staff

### 7. Generate Purchase Lists

1. View a session
2. Click **View Purchase List**
3. Click **Generate Purchase List**
4. Review aggregated ingredients across all meal periods
5. Update status as needed (Draft → Pending → Ordered → Completed)

## API Endpoints

### REST API

The application provides REST API endpoints for all operations:

#### Sessions
- `GET /api/sessions` - List all sessions
- `POST /api/sessions` - Create a new session
- `GET /api/sessions/{id}` - Get session details
- `PUT /api/sessions/{id}` - Update a session
- `DELETE /api/sessions/{id}` - Delete a session
- `POST /api/sessions/{id}/groups` - Add a group to session
- `GET /api/sessions/{id}/groups` - Get session groups

#### Menus
- `GET /api/menus` - List all menus
- `POST /api/menus` - Create a new menu
- `GET /api/menus/{id}` - Get menu details
- `PUT /api/menus/{id}` - Update a menu
- `DELETE /api/menus/{id}` - Delete a menu
- `POST /api/menus/{id}/items` - Add item to menu
- `DELETE /api/menus/items/{itemId}` - Remove menu item

#### Ingredients
- `GET /api/menus/ingredients` - List all ingredients
- `POST /api/menus/ingredients` - Create an ingredient
- `GET /api/menus/ingredients/{id}` - Get ingredient details

#### Meal Periods
- `GET /api/mealperiods/{id}` - Get meal period details
- `PUT /api/mealperiods/{id}/menu/{menuId}` - Assign menu
- `DELETE /api/mealperiods/{id}/menu` - Clear menu assignment

#### Prep Lists
- `POST /api/preplists/day/{dayId}` - Generate prep list
- `GET /api/preplists/{id}` - Get prep list details

#### Purchase Lists
- `POST /api/purchaselists/session/{sessionId}` - Generate purchase list
- `GET /api/purchaselists/{id}` - Get purchase list details
- `PUT /api/purchaselists/{id}/status` - Update status

## Development

### Project Structure

```
src/main/java/com/cpsc464/retreat_meal_manager/
├── application/         # Application services
│   ├── session/
│   ├── mealperiod/
│   ├── menu/
│   ├── preplist/
│   ├── purchaselist/
│   └── auth/
├── domain/             # Domain entities and services
│   ├── session/
│   ├── mealperiod/
│   ├── menu/
│   ├── preplist/
│   ├── purchaselist/
│   ├── auth/
│   └── services/
├── infrastructure/     # Repositories and technical services
│   ├── persistence/
│   └── security/
└── presentation/       # Controllers and web UI
    ├── web/
    ├── session/
    ├── mealperiod/
    ├── menu/
    ├── preplist/
    ├── purchaselist/
    └── auth/

src/main/resources/
├── templates/          # Thymeleaf HTML templates
│   ├── sessions/
│   ├── menus/
│   ├── ingredients/
│   ├── preplists/
│   └── purchaselists/
└── static/            # CSS and JavaScript
    ├── css/
    └── js/
```

### Build Commands

```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package as JAR
./mvnw package

# Run without Maven
java -jar target/retreat-meal-manager-0.0.1-SNAPSHOT.jar
```

### Development Mode

The application includes Spring Boot DevTools for automatic restart on code changes.

### Database Schema Updates

The application uses Hibernate with `ddl-auto=update`, which means:
- Tables are created automatically on first run
- Schema changes are applied automatically
- **Data persists** between application restarts

## Configuration

### Key Configuration Properties

**File**: `src/main/resources/application.properties`

```properties
# Application name
spring.application.name=retreat-meal-manager

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/retreatdb
spring.datasource.username=josh
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## Troubleshooting

### Port 8080 Already in Use

```bash
# Find process using port 8080
lsof -ti:8080

# Kill the process
kill -9 <PID>
```

### PostgreSQL Connection Failed

```bash
# Check if PostgreSQL is running
brew services list

# Start PostgreSQL if stopped
brew services start postgresql@15

# Verify database exists
psql -l
```

### Database Tables Not Created

1. Check application logs for errors
2. Verify PostgreSQL connection in `application.properties`
3. Ensure user has CREATE TABLE permissions

## License

This project was created for CPSC 464 - Software Architecture course.

## Authors

- Josh
- Co-Authored-By: Claude Sonnet 4.5

## Acknowledgments

Built with Spring Boot, Thymeleaf, PostgreSQL, and Hibernate.
