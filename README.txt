Real-Time Event Ticketing System

INTRODUCTION
The Ticket Booking System is a real-time event ticketing platform built to simulate buying and selling tickets. The system simulates a multi-threaded environment where customers attempt to book tickets from a shared pool. Using Object-Oriented Programming (OOP) principles, it ensures thread-safe access and synchronized operations. The application provides both a Command-Line Interface (CLI) and a Graphical User Interface (GUI) for user interaction and system configuration.

---

SETUP INSTRUCTIONS

PREREQUISITES
- Java: JDK 17 or later
- Spring Boot: Version 3.0 or later.
- Angular: Version 16 or later.
- Build Tool: Maven (for backend) and npm (for frontend).
- IDE: IntelliJ IDEA, Eclipse, or any Java-compatible IDE for backend; VS Code for frontend.

STEPS TO BUILD AND RUN

BACKEND (SPRING BOOT):
1. Open the folder in IntelliJ

2. Build the Application:
   mvn clean install

3. Run the Application:
   mvn spring-boot:run

FRONTEND (ANGULAR):
1. Navigate to the Frontend Directory:
   cd ticket-booking-system/frontend

2. Install Dependencies:
   npm install

3. Run the Angular Application:
   ng serve

4. Access the application at http://localhost:4200.

---

USAGE INSTRUCTIONS

CONFIGURING THE SYSTEM
1. Launch both the backend and frontend applications.
2. Access the Angular-based frontend through a browser.
3. Configure the following parameters via the settings page:
   - Total Tickets: The initial total number of tickets available.
   - Ticket Release Rate: The rate at which new tickets are released into the system.
   - Customer Retrieval Rate: The speed at which customers attempt to book tickets.
   - Max Ticket Capacity: The maximum limit for tickets in the system.

STARTING THE SYSTEM
- Use the frontend to input configuration parameters.
- Click the Start button to begin the ticket booking process.

UI CONTROLS (FRONTEND)
- Parameter Fields: Input fields to configure system parameters.
- Start Button: Starts the ticket booking simulation.
- Live Feed Panel: Displays real-time logs and system activity.

---

