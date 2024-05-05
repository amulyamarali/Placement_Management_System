# Placement Management System (Spring Boot)

## Overview
The Placement Management System is designed to manage student placements within an organization. It allows administrators to track student profiles, job openings, and placement activities.

## Features
- Student registration and profile management
- Job posting and application tracking
- Interview scheduling and feedback
- Placement statistics and reporting

## Technologies Used
- **Java Spring Boot**: Backend framework
- **Hibernate (JPA)**: Object-relational mapping
- **MySQL**: Database
- **Thymeleaf**: Frontend templating
- **Bootstrap**: Styling

## Design Principles

### SOLID Principles
1. **Single Responsibility Principle (SRP)**: Each class should have one reason to change. Avoid mixing responsibilities in a single class.
   - Example: Separate report generation and email sending logic into different classes.

2. **Open/Closed Principle (OCP)**: Software entities (classes, modules, functions) should be open for extension but closed for modification.
   - Design classes to allow adding new features without altering existing code.

3. **Liskov's Substitution Principle (LSP)**: Objects of a superclass should be replaceable with objects of a subclass without affecting correctness.
   - Ensure that subclasses adhere to the contract defined by their superclass.

4. **Interface Segregation Principle (ISP)**: Clients should not be forced to depend on interfaces they do not use.
   - Split large interfaces into smaller, more focused ones.

5. **Dependency Inversion Principle (DIP)**: High-level modules should not depend on low-level modules; both should depend on abstractions.
   - Use dependency injection to invert control and decouple components.

### GRASP Principles
1. **Information Expert**: Assign responsibilities to the class with the most information required to fulfill them.
2. **Creator**: Assign the responsibility of creating an object to the class that has the necessary information.
3. **Controller**: Assign the responsibility of handling system events to a controller class.
4. **Low Coupling**: Minimize dependencies between classes.
5. **High Cohesion**: Group related responsibilities together within a class.

## Design Patterns
Explore the following design patterns for your Placement Management System:

1. **Factory Method**: Create instances of different placement-related objects (e.g., student, job, interview) based on specific criteria.
2. **Observer**: Notify interested parties (e.g., students, administrators) about placement events (e.g., job postings, interview schedules).
3. **Strategy**: Define different algorithms for ranking students based on placement criteria (e.g., GPA, skills).
4. **Decorator**: Enhance student profiles dynamically by adding additional information (e.g., certifications, projects).

