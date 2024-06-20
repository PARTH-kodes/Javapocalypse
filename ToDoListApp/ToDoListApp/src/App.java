import java.util.*;

public class App {

    private static final Scanner sc = new Scanner(System.in);

    static HashMap<String, ArrayList<Task>> category = new HashMap<>();

    static ArrayList<Task> tasks = new ArrayList<>();

    public static String toTitleString(String str) {

        StringBuilder result = new StringBuilder();

        String words[] = str.split("\\s+");

        for (String string : words) {

            if (string.equals(string.toUpperCase())) {

                result.append(string).append(" ");

            } else {

                char firstCharacter = Character.toUpperCase(string.charAt(0));

                String remainingWord = string.substring(1).toLowerCase();

                result.append(firstCharacter).append(remainingWord).append(" ");

            }

        }

        return result.toString().trim();

    }

    static class Task {

        private String title;

        private String description;

        private String category;

        public boolean isCompleted;

        public Task(String title, String description, String category) {

            this.title = title;

            this.description = description;

            this.category = category;

            this.isCompleted = false;

        }

        // Getter and setter methods omitted for brevity

        @Override

        public String toString() {

            StringBuilder taskDetails = new StringBuilder(

                    "Title: " + title + "\nDescription: " + description + "\nCategory: " + category + "\nStatus: ");

            if (isCompleted) {

                taskDetails.append("Completed!");

            } else {

                taskDetails.append("Not Yet Completed!");

            }

            return taskDetails.toString();

        }

    }

    public static void addTask() throws IllegalArgumentException {

        System.out.println("\n === ADDING A TASK ===");

        System.out.println("\nEnter the Title of your task: ");

        String title = toTitleString(sc.nextLine().trim());

        if (title.isBlank() || !title.matches("^[a-zA-Z0-9]*$")) {

            throw new IllegalArgumentException(
                    "\nYou are trying to enter an empty task or non-alpha numeric characters.\nMake sure the task title is alpha-numeric characters only.");

        }

        System.out.println("\nEnter the task description, if any! Else, type none.");

        String description = toTitleString(sc.nextLine().trim());

        System.out.println("\nEnter the category to which your task belongs: ");

        String taskCategory = toTitleString(sc.nextLine().trim());

        if (taskCategory.isBlank() || !taskCategory.matches("^[a-zA-Z0-9]*$")) {

            throw new IllegalArgumentException(
                    "\nYou are trying to enter an empty category or non-alpha numeric characters.\nMake sure the category is alpha-numeric characters only.");

        }

        if (!category.containsKey(taskCategory)) {

            category.put(taskCategory, new ArrayList<>());

        }

        Task newTask = new Task(title, description, taskCategory);

        tasks.add(newTask);

        category.get(taskCategory).add(newTask);

        System.out.println("\nYour task was added successfully in the -" + taskCategory + "- category");

    }

    public static void viewAllTasks() {

        System.out.println("\n ==== YOUR TASKS ====\n");

        for (String cate : category.keySet()) {

            if (category.get(cate).isEmpty()) {

                continue;
            }

            System.out.println("\n---- " + cate + " ----\n");

            for (int i = 0; i < category.get(cate).size(); i++) {

                System.out.println((i + 1) + ". " + category.get(cate).get(i).toString());

            }

            System.out.println();

        }

    }

    public static void deleteCategory() throws IllegalArgumentException {

        if (category.isEmpty()) {

            System.out.println("No categories have been specified yet.");

            return;

        }

        System.out.println("\n=== DELETE CATEGORY ===\n");

        System.out.println("\nList of available cateogries: ");

        for (String categories : category.keySet()) {

            System.out.println("-> " + categories);

        }

        System.out.println("\nEnter the category which you would like to delete:");

        String deleteCate = toTitleString(sc.nextLine().trim());

        if (deleteCate.isBlank() || !deleteCate.matches("^[a-zA-Z0-9]*$")) {

            throw new IllegalArgumentException(
                    "You have entered an invalid category name.\nYou can enter only alpha-numeric characters.\n(A-Z; a-z; 0-9).");

        }

        if (!category.containsKey(deleteCate)) {

            System.out.println("No such category exists.");

            System.out.println("Do you wish to add a task in this category?\nEnter Y for yes or N for no");

            if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                addTask();

                deleteCategory();

            }

            return;

        }

        System.out.println("\nThe category named -" + deleteCate + "- was deleted.");

        category.remove(deleteCate);

    }

    public static void deleteTask() throws IllegalArgumentException, NumberFormatException {

        if (category.isEmpty()) {

            System.out.println("No categories have been specified yet.");

            return;

        }

        System.out.println("\n=== DELETE TASK ===\n");

        System.out.println("\nList of available cateogries: ");

        for (String available : category.keySet()) {

            System.out.println("-> " + available);

        }

        System.out.println("\nEnter the category from which you would like to delete the task:");

        ArrayList<Task> tasksToDelete;

        String deleteCate = toTitleString(sc.nextLine().trim());

        if (deleteCate.isBlank() || !deleteCate.matches("^[a-zA-Z0-9]*$")) {

            throw new IllegalArgumentException(
                    "\nYou have entered an invalid name of the category.\nPlease enter a valid alpha-numeric category name.\n");

        }

        if (!category.containsKey(deleteCate) || category.get(deleteCate).isEmpty()) {

            System.out.println("No tasks are added in this category.");

            System.out.println(
                    "Do you wish to add a task or create a new category?\nEnter Y for yes or N for no");

            if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                addTask();

                deleteTask();

            }

            return;

        } else {

            System.out.println("List of all available tasks: ");

            tasksToDelete = category.get(deleteCate);

            for (int i = 0; i < tasksToDelete.size(); i++) {

                System.out.println((i + 1) + ". " + tasksToDelete.get(i).title);

            }

            System.out.println("\nSelect a particular task to delete: ");

            String userInput = sc.nextLine().trim();

            int indxToDelete;

            if (userInput.isBlank() || !userInput.matches("^[0-9]*$")) {

                throw new NumberFormatException(
                        "\nYou have enterd a non-numeric character. Kindly enter a valid number.");

            }

            indxToDelete = Integer.parseInt(userInput);

            if (indxToDelete < 1 || indxToDelete > tasksToDelete.size()) {

                System.out.println(

                        "Invalid task number. Please enter a number.\n).");

            } else {

                tasksToDelete.remove(indxToDelete - 1);

                System.out.println("Task Deleted Successfully!");

            }

        }

    }

    public static void markAsCompleted() throws IllegalArgumentException, NumberFormatException {

        if (category.isEmpty()) {

            System.out.println("\nThere are no categories of tasks, available right now.");

            return;

        }

        System.out.println("\n=== MARKING A TASK AS COMPLETED ===");

        System.out.println("\nSelect a Category, from: ");

        for (String str : category.keySet()) {

            if (category.get(str).isEmpty()) {

                continue;

            }

            System.out.println("-> " + str);

        }

        String markCate = toTitleString(sc.nextLine().trim());

        if (markCate.isBlank() || !markCate.matches("^[a-z0-9A-Z]*$")) {

            throw new IllegalArgumentException(
                    "\nYou are trying to enter a non alpha-numeric category name, or an empty name.\nKindly enter a valid name.");

        }

        if (!category.containsKey(markCate) || category.get(markCate).isEmpty()) {

            System.out.println("\nNo such category or task exists.");

            System.out.println("Do you wish to add a category and task?\nEnter Y for yes or N for no");

            if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                addTask();

                markAsCompleted();

            }

            return;

        }

        ArrayList<Task> markCateTasks = category.get(markCate);

        System.out.println("\nList of available tasks:");

        for (int i = 0; i < markCateTasks.size(); i++) {

            System.out.println((i + 1) + ". " + markCateTasks.get(i).title);

        }

        System.out.println("\nSelect the choice of task to be marked as completed: ");

        int toComplete;

        String userInput = sc.nextLine().trim();

        if (userInput.isBlank() || !userInput.matches("^[0-9]*$")) {

            throw new NumberFormatException("\nKindly enter a valid choice of task.\nOnly digits are allowed.");

        }

        toComplete = Integer.parseInt(userInput);

        if (toComplete < 1 || toComplete > markCateTasks.size()) {

            System.out.println("\nERROR: Invalid choice of task .");

        } else {

            markCateTasks.get(toComplete - 1).isCompleted = true;

            System.out.println(
                    "\nYour task -" + markCateTasks.get(toComplete - 1).title + "- has been marked as completed.");

        }

    }

    public static void isCompleted() throws IllegalArgumentException, NumberFormatException {

        if (category.isEmpty()) {

            System.out.println("\nThere are no categories or tasks available. ");

            return;

        }

        System.out.println("\n=== CHECK WHETHER TASK IS COMPLETED ===");

        System.out.println("\nList of categories: ");

        for (String str : category.keySet()) {

            System.out.println("-> " + str);

        }

        System.out.println("\nSelect one category: ");

        String checkCate = toTitleString(sc.nextLine().trim());

        if (checkCate.isBlank() || !checkCate.matches("^[a-z0-9A-Z]*$")) {

            throw new IllegalArgumentException(
                    "\nThe category you are entering is either empty or contain non alpha-numeric characters.\nKindly enter a valid name.");

        }

        if (!category.containsKey(checkCate) || category.get(checkCate).isEmpty()) {

            System.out.println(
                    "\nYour To Do List does not have any particular tasks or category of such name, at the moment.");

            System.out.println("\nDo you wish to add a category and task?\nEnter Y for yes or N for no");

            if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                addTask();

            }

            return;

        }

        ArrayList<Task> checkCateTasks = category.get(checkCate);

        System.out.println("\nList of all available tasks: ");

        for (int i = 0; i < checkCateTasks.size(); i++) {

            System.out.println((i + 1) + ". " + checkCateTasks.get(i).title);

        }

        System.out.println("Which task do you want to check as completed?\nEnter the number: ");

        int toCheck;

        String userInput = sc.nextLine().trim();

        if (userInput.isBlank() || !userInput.matches("^[0-9]*$")) {

            throw new NumberFormatException(
                    "\nYou are trying to enter an invalid choice. Only numeric characters are allowed.");
        }

        toCheck = Integer.parseInt(userInput);

        if (toCheck < 1 || toCheck > checkCateTasks.size()) {

            System.out.println("\nERROR: Invalid task choice.\nKindly input a valid task number.");

        }

        if (checkCateTasks.get(toCheck - 1).isCompleted) {

            System.out.println("Your task is marked as completed.");

        } else {

            System.out.println(

                    "Your task is not yet completed.\nDo you wish to mark it as completed?\nEnter Y for yes or N for no: ");

            if (sc.next().trim().toUpperCase().equals("Y")) {

                markAsCompleted();

            }

            return;

        }

    }

    public static void main(String[] args) throws Exception {

        category.put("Work", new ArrayList<>());

        category.put("Home", new ArrayList<>());

        category.put("Daily Errands", new ArrayList<>());

        System.out.println("Welcome to the To Do List Application:");

        System.out.println("WOULD YOU LIKE TO CONTINUE?\nENTER Y OR N.");

        String yesno = sc.next();

        if (yesno.equalsIgnoreCase("Y")) {

            try {

                allOptions();

            } catch (InputMismatchException e) {

                System.out.println(

                        "\nYou are trying to input an invalid character. Only digits are allowed.\nTRY AGAIN!");

            }

        } else {

            System.out.println("THANK YOU SO MUCH! HAVE A NICE DAY.");

        }

    }

    public static void allOptions() throws InputMismatchException {

        while (true) {

            System.out.println("\n ==== HERE IS WHAT YOU CAN DO, WITH US ==== ");

            System.out.println("1. Create a new task cateogry or add a new task.");

            System.out.println("2. View the previously created categories, and its tasks.");

            System.out.println("3. Delete a complete category.");

            System.out.println("4. Delete any task.");

            System.out.println("5. Mark the task completed.");

            System.out.println("6. Check whether your task is completed or not.");

            System.out.println("7. Exit the application.");

            System.out.println("\nEnter your choice of operation: ");

            int choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:

                    try {

                        addTask();

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                        System.out.println("Do you wish to try again? Enter Y for yes or N for no: ");

                        if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                            addTask();

                        } else {

                            allOptions();

                        }

                    }

                    break;

                case 2:

                    viewAllTasks();

                    break;

                case 3:

                    try {

                        deleteCategory();

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                        System.out.println("Do you wish to try again? Enter Y for yes or N for no: ");

                        if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                            deleteCategory();

                        }

                    }

                    break;

                case 4:

                    try {

                        deleteTask();

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                        System.out.println("Do you wish to try again? Enter Y for yes or N for no: ");

                        if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                            deleteTask();

                        }

                    }

                    break;

                case 5:

                    try {

                        markAsCompleted();

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                        System.out.println("Do you wish to try again? Enter Y for yes or N for no: ");

                        if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                            markAsCompleted();

                        }

                    }

                    break;

                case 6:

                    try {

                        isCompleted();

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());

                        System.out.println("Do you wish to try again? Enter Y for yes or N for no: ");

                        if (sc.nextLine().trim().toUpperCase().equals("Y")) {

                            isCompleted();

                        }

                    }

                    break;

                case 7:

                    System.out.println("THANK YOU FOR USING US!\nHAVE A NICE DAY AHEAD");

                    return;

                default:

                    System.out.println("ERROR: Invalid Choice.\nKindly enter a valid choice.");

            }

        }

    }

}