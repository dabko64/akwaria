# Oceanarium Manager

Project created as part of the university course.

Oceanarium Manager is a desktop application for managing aquariums, fish and aquarium ratings.  
The application uses **Hibernate ORM**, **H2 Database** and a graphical user interface built with **Swing**.

---

## Features

### Aquarium Management
- Add and remove aquariums
- Display a list of aquariums
- Sort aquariums by occupancy level

### Fish Management
- Add and remove fish
- Change fish status, for example healthy, sick or quarantine
- Filter fish by name or species
- Filter fish by status

### Rating System
- Add ratings from 0 to 5
- Assign ratings to aquariums
- Add comments and rating dates
- Calculate:
  - number of ratings
  - average rating for each aquarium

### Statistics
- Uses **Criteria API**
- Supports grouping with `GROUP BY`
- Displays statistical summaries in the user interface

### Data Storage
- Binary file save/load using serialization
- CSV export and import

---

## Technologies

- Java 17
- Hibernate ORM
- H2 Database
- Maven
- Swing GUI
- JDBC / JPA
- Criteria API
- HQL

---

## Project Structure

```text
├── model      # entities: Aquarium, Fish, Rating
├── dao        # database access layer
├── service    # business logic, statistics and file operations
├── facade     # intermediate API layer for the UI
├── ui         # Swing graphical user interface
├── dto        # transfer objects used in tables
├── config     # Hibernate configuration
