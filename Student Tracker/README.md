# Student Management System

## Overview

The Student Management System is a console-based application developed in Java to manage student information efficiently. The system allows users to add, edit, search, and display student details. The data is stored in a CSV file, ensuring persistence and easy retrieval.

## Features

- **Add Student**: Add new student details including name, roll number, grade, date of birth, gender, address, phone number, and email.
- **Edit Student Details**: Modify existing student information.
- **Search Student**: Search for a student by roll number and display their details.
- **Display All Students**: Display all students' information, sorted alphabetically by name.
- **Data Validation**: Ensures that the input data is valid (e.g., roll number must be unique, name must only contain alphabets, etc.).
- **Data Persistence**: Stores student information in a CSV file, allowing data to persist between application runs.
- **Input Flexibility**: Handles empty or whitespace-only inputs appropriately, requiring valid data entry.
- **Real-Time Touch**: Includes delays to simulate real-time data processing and enhance user experience.

## Requirements

- Java Development Kit (JDK) 8 or higher
- Any IDE or text editor for Java development

## Installation and Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/PARTH-kodes/CODSOFT.git
    ```

2. **Navigate to the project directory**:
    ```sh
    cd "CODSOFT/TASK 5/"
    ```

3. **Compile the Java files**:
    ```sh
    javac StudentManagementSysApp.java
    ```

4. **Run the application**:
    ```sh
    java StudentManagementSysApp
    ```

## Usage

1. **Main Menu**:
    - 1. Add Student
    - 2. Edit Student Details
    - 3. Search Student
    - 4. Display All Students
    - 5. Exit

2. **Adding a Student**:
    - Enter the student's name, roll number, grade, date of birth, gender, address, phone number, and email.
    - The system validates each input and ensures the roll number is unique.

3. **Editing a Student's Details**:
    - Enter the roll number of the student whose details you want to edit.
    - Update the required fields. Leave blank to retain the existing value.

4. **Searching for a Student**:
    - Enter the roll number of the student to display their details.

5. **Displaying All Students**:
    - The system displays all students' details sorted alphabetically by name.

6. **Exiting the System**:
    - Choose option 5 to save the data and exit the application.

## Data Validation

- **Name**: Must only contain alphabets.
- **Roll Number**: Must be unique.
- **Grade**: Must be an integer between 1 and 12.
- **Date of Birth**: Must be in dd-MM-yyyy format.
- **Gender**: Must be 'M' or 'F'.
- **Phone Number**: Must be a 10-digit number.
- **Email**: Must be in a valid email format.

## Data Storage

- Student data is stored in a `students.csv` file in the project's root directory.
- The CSV file ensures data persistence and can be edited directly (ensure the format is maintained).
