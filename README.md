# ToDoList Application

Welcome to the ToDoList Application! This project is a simple yet powerful tool for managing your tasks efficiently using Java. It allows you to create, view, describe, and manage tasks across different categories.

## Features

- **Add Tasks**: Create new tasks with a title, description, and category.
- **View Tasks**: Display all tasks categorized for easy viewing.
- **Delete Categories**: Remove entire categories along with all their tasks.
- **Delete Tasks**: Remove specific tasks from any category.
- **Mark Tasks as Completed**: Mark tasks as completed and keep track of them.
- **Check Task Completion**: Verify if a task has been completed.

## Code Overview

The application is structured with a main class `App` and an inner class `Task`. Below is a brief overview of the key functionalities implemented:

### App Class

- **Fields**:

  - `Scanner sc`: For reading user input.
  - `HashMap<String, ArrayList<Task>> category`: Stores tasks categorized.
  - `ArrayList<Task> tasks`: List of all tasks.

- **Methods**:
  - `toTitleString(String str)`: Converts a string to title case.
  - `addTask()`: Adds a new task.
  - `viewAllTasks()`: Displays all tasks.
  - `deleteCategory()`: Deletes a category.
  - `deleteTask()`: Deletes a specific task.
  - `markAsCompleted()`: Marks a task as completed.
  - `isCompleted()`: Checks if a task is completed.
  - `allOptions()`: Displays the menu and handles user choices.
  - `main(String[] args)`: Main method to run the application.

### Task Class

- **Fields**:

  - `String title`: Task title.
  - `String description`: Task description.
  - `String category`: Task category.
  - `boolean isCompleted`: Task completion status.

- **Methods**:
  - `toString()`: Returns a string representation of the task.

## How to Use

### Running the Application

1. Clone the repository to your local machine:

   ```sh
   git clone https://github.com/yourusername/your-repo-name.git
   cd your-repo-name
   ```

2. Open the project in your preferred IDE (e.g., VS Code).

3. Compile and run the `App.java` file.

### PowerShell Commands

If you encounter an error related to `index.lock`, you can remove the file manually using PowerShell:

1. Open PowerShell.

2. Navigate to your repository:

   ```sh
   cd D:\Javapocalypse
   ```

3. List all files, including hidden ones:

   ```sh
   ls -Force
   ```

4. Remove the `index.lock` file:

   ```sh
   Remove-Item -Force .git\index.lock
   ```

### Compiling the Application

1. Open a terminal or command prompt.

2. Navigate to the directory where your `App.java` file is located:

   ```sh
   cd path\to\your\project\src
   ```

3. Compile the Java file:

   ```sh
   javac App.java
   ```

### Running the Application

1. Run the compiled Java file:

   ```sh
   java App
   ```

### Handling the Errors

- **Missing `javac` or `java` Commands**
  If you get an error saying `javac` or `java` is not recognized as an internal or external command, ensure that the JDK is properly installed and the `JAVA_HOME` environment variable is set.

  - **Verify the Installation**

  ```sh
  java -version
  javac -version
  ```

  - If Java is not installed, download and install the JDK from Oracle's official site or OpenJDK.

  - Add the JDK bin directory to your `PATH` environment variable.

- **Troubleshooting Compilation Errors**
  - Ensure all required classes are in the correct directories.
  - Ensure all required classes are in the correct directories.
  - Check for missing imports.

### Cleaning Up

If you want to clean up the compiled files:

1. Navigate to the directory where your `.class` files are located.

2. Remove the `.class` files:

   ```sh
   del *.class
   ```

   Or, if you're using a Unix-based system:

   ```sh
   rm *.class
   ```

### Additional Tips

- Make sure you are using the correct version of Java that matches your codebase.

- Regularly commit and push your changes to avoid losing your progress.

- Use version control branching to manage different features or fixes without affecting the main codebase.

- Follow the on-screen instructions to interact with the ToDoList application.
