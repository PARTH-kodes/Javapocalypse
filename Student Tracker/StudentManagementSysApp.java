import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.file.*;


// Student class implementing Serializable for object serialization
class Student implements Serializable {

    // Attributes of the Student class
    String name;
    String gender;
    String address;
    String phoneNumber;
    String email;

    LocalDate dateOfBirth;

    int rollno;
    int grade;

    // Constructor to initialize Student object with provided values
    public Student (int rollno, String name, int grade, String gender, LocalDate dateOfBirth, String address, String phoneNumber, String email) {
        this.rollno = rollno;
        this.name = name;
        this.grade = grade;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address.toUpperCase();
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters for each attribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber(){
        return rollno;
    }

    public void setRollNumber(int rollno){
        this.rollno = rollno;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // Converts the Student object to a String for easy display
    @Override
    public String toString() {
        return String.format("%-10d %-15s %-10d %-10s %-15s %-20s %-15s %-20s", rollno, name, grade, gender, dateOfBirth, address, phoneNumber, email);
    }

    // Converts the Student object to a CSV format string
    public String toCSV(){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dob = dateOfBirth != null ? dateOfBirth.format(format) : "";
        return String.join(",", 
            String.valueOf(rollno), 
            escapeCsv(name), 
            String.valueOf(suffixGrade()), 
            escapeCsv(gender), 
            escapeCsv(dob), 
            escapeCsv(address), 
            escapeCsv(phoneNumber), 
            escapeCsv(email)
        );
    }

    // Creates a Student object from a CSV format string
    public static Student fromCSV(String csvLine){
        String[] tokens = parseCsvLine(csvLine);
        int rollno = tokens[0].isEmpty() ? 0 : Integer.parseInt(tokens[0]);
        String name = tokens[1];
        int grade = tokens[2].isEmpty() ? 0 : Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 2));
        String gender = tokens[3];
        LocalDate dateOfBirth = tokens[4].isEmpty() ? null : LocalDate.parse(tokens[4], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String address = tokens[5].toUpperCase();
        String phoneNumber = tokens[6];
        String email = tokens[7];
        return new Student(rollno, name, grade, gender, dateOfBirth, address, phoneNumber, email);
    }

    // Escapes fields for CSV compatibility (e.g., handling commas and quotes)
    private String escapeCsv(String field) {
        if (field.contains(",") || field.contains("\"")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    // Parses a CSV line into individual fields
    private static String[] parseCsvLine(String csvLine) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < csvLine.length(); i++) {
            char c = csvLine.charAt(i);
            if (inQuotes) {
                if (c == '\"') {
                    if (i < csvLine.length() - 1 && csvLine.charAt(i + 1) == '\"') {
                        currentToken.append(c);
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    currentToken.append(c);
                }
            } else {
                if (c == '\"') {
                    inQuotes = true;
                } else if (c == ',') {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                } else {
                    currentToken.append(c);
                }
            }
        }
        tokens.add(currentToken.toString());
        return tokens.toArray(new String[0]);
    }

    // Converts the grade to a string with an appropriate suffix (e.g., "1st", "2nd")
    public String suffixGrade() {
        int gradeMod100 = grade % 100;
        int gradeMod10 = grade % 10;
        if(gradeMod100 - gradeMod10 == 10) {
            return grade + "th";
        }
        switch(gradeMod10){
            case 1:
                return grade + "st";
            case 2:
                return grade + "nd";
            case 3:
                return grade + "rd";

            default:
                return grade + "th";
        }
    }
}

// Class to manage student data with various functionalities
class StudentManagementSystem{
    private Scanner sc = new Scanner(System.in);

    // Class to manage student data with various functionalities
    public List<Student> students;
    
    // Constructor initializing the student list and loading existing students from file
    public StudentManagementSystem(){
        students = new ArrayList<>();
        loadStudents();
    }

    // Adds a new student to the list and saves to file
    public void addStudent(Student student){
        students.add(student);
        saveStudents();
    }

    // Deletes a student by roll number after confirmation
    public void deleteStudent(int rollNumber) {
        if (students.isEmpty()) {
            System.out.println("\nTHE DATABASE IS EMPTY. PLEASE ADD DATA FIRST.\n");
            return;
        }
        
        System.out.println("\nAre you sure you want to delete student with roll number \"" + rollNumber + "\"?");
        System.out.println("[Y/N]");
        
        char confirmation = sc.next().charAt(0);
        
        if (confirmation == 'y' || confirmation == 'Y') {
            delay("\nDeleting student with roll number \"" + rollNumber + "\"...", 1500);
    
    
            boolean removed = students.removeIf(student -> student.getRollNumber() == rollNumber);
            
            if (removed) {
                saveStudents();
                System.out.println("\nStudent with roll number \"" + rollNumber + "\" deleted successfully.");
            } else {
                System.out.println("\nNo student found with roll number \"" + rollNumber + "\".");
            }

        } else {
            System.out.println("Operation canceled.");
        }
        
        delay("\nReturning to main menu...", 1400);
        
    }
    
    // SEARCHING METHODS
    // Searches students by name
    public List<Student> searchStudentByName(String name){
        List<Student> searchResult = new ArrayList<>();
        
        delay("\nSearching for \"" + name + "\"...\n", 1500);

        for (Student stu : students){
            if(stu.getName().equalsIgnoreCase(name)){
                searchResult.add(stu);
            }
        }

        if(searchResult.isEmpty()){
            System.out.println("\nNo student found with name \"" + name + "\".\n");
            return null;
        }

        return searchResult;
    }

    
    // Searches for a student by roll number
    public Student searchStudentByRollNumber(int rollNumber){

        delay("\nSearching for roll number \"" + rollNumber + "\"...\n", 1500);

        for (Student stu : students){
            if(stu.getRollNumber() == rollNumber){
                return stu;
            }
        }
        return null;
    }


    // Searches for students by grade
    public List<Student> searchStudentByGrade(int grade){
        List<Student> searchResult = new ArrayList<>();
        
        delay("\nSearching for \"" + grade + "\" grade...\n", 1500);

        for (Student stu : students){
            if(stu.getGrade() == grade){
                searchResult.add(stu);
            }
        }

        if(searchResult.isEmpty()){
            System.out.println("\nNo student found with grade \"" + grade + "\".\n");
            return null;
        }

        return searchResult;
    }


    // Searches for students by gender
    public List<Student> searchStudentByGender(String gender){
        List<Student> searchResult = new ArrayList<>();
        
        if(gender.equalsIgnoreCase("M")){
            System.out.println("\nSearching for all male students..");
        }else{
            System.out.println("\nSearching for all female students..");
        }

        delay(".\n", 1500);

        for (Student stu : students){
            if(stu.getGender().equalsIgnoreCase(gender)){
                searchResult.add(stu);
            }
        }

        if(searchResult.isEmpty()){
            System.out.println("\nNo student found with the specified gender.\n");
            return null;
        }

        return searchResult;
    }


    // Displays all students sorted by roll number
    public void showAllStudents(){
        if(students.isEmpty()){
            System.out.println("\nNO DATA FOUND. PLEASE ADD DATA FIRST.\n");

        } else {
            students.sort(Comparator.comparingInt(Student::getRollNumber));
            
            delay("\nDisplaying details of all students...\n", 2300);

            System.out.printf("%-10s %-15s %-10s %-10s %-15s %-20s %-15s %-20s\n", "Roll No.", "Name", "Grade", "Gender", "Date of Birth", "Address", "Phone Number", "Email");

            for (Student stu : students){
                System.out.println(stu.toString());
            }
        }

        delay("\nReturning to main menu...", 4000);
        return;
    }


    // SAVE AND LOAD METHODS
    // Saves all students to a CSV file
    public void saveStudents() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("students.csv"))) {
            writer.println("Roll Number,Name,Grade,Gender,Date of Birth,Address,PhoneNumber,Email");
            for (Student student : students) {
                writer.write(student.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving Students: " + e.getMessage());
        }

    }

    // Loads students from a CSV file
    public void loadStudents() {
        students.clear();
        if(!Files.exists(Paths.get("students.csv"))){
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("students.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                students.add(Student.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading Students: " + e.getMessage());
        }

    }

    public void delay(String message, long milliseconds){

        try{
            System.out.println(message);
            Thread.sleep(milliseconds);
        } catch(InterruptedException ie){
            Thread.currentThread().interrupt();
            System.out.println("Error: " + ie.getMessage());
        }

    }

    // Checks if the roll number is unique
    public boolean isRollNumberUnique(int rollNumber){
        return students.stream().noneMatch(student -> student.getRollNumber() == rollNumber);
    }
}


public class StudentManagementSysApp{
    static StudentManagementSystem sms = new StudentManagementSystem();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            
            optionMenu();   // Display the main menu
            int selection = takeInt("\nEnter your choice: ", 1, 6);
            
            // If the choice is not 'Exit', show an entering message with a delay
            if(selection != 6){
                sms.delay("\nEntering the interface...", 1800);
            }

            // Handle user's choice using a switch-case statement
            switch(selection){
                case 1:
                    System.out.println("\n\n=====ADD STUDENT DETAILS======\n");
                    addStudent();
                    break;

                case 2:
                    System.out.println("\n\n=====EDIT STUDENT DETAILS======\n");
                    editDetails();
                    break;

                case 3:
                    System.out.println("\n\n=====SEARCH STUDENT DETAILS======\n");
                    searchStudent();
                    break;

                case 4:
                    System.out.println("\n\n=====DELETE STUDENT DETAILS======\n");

                    sms.deleteStudent(takeInt("Enter roll number of the student to delete: ", 1, 500));
                    System.out.println("\n\nDETAILS(if available), DELETED SUCCESSFULLY.");
                    break;

                case 5:
                    sms.showAllStudents();
                    break;

                case 6:
                    sms.saveStudents();
                    
                    sms.delay("\nExiting the Interface...\nADIOS AMIGOS\n", 1500);
                    
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice. Please Try Again.");
            }
        }
    }

    public static void optionMenu() {
        System.out.println("\n\n                                         =====STUDENT MANAGEMENT SYSTEM======\n\nHere is your list of options:\n");

        System.out.println("1. Add Student Details?");
        System.out.println("2. Edit Details of a Student?");
        System.out.println("3. Search Student?");
        System.out.println("4. Delete a Student Data?");
        System.out.println("5. Show All Students?");
        System.out.println("6. Exit the Interface.");
    }

    public static void addStudent(){

        String name = takeString("Enter the Name of the student: ", "^[a-zA-Z ]+$", "Name must only contain alphabets");
        // System.out.println();

        int rollno = takeUniqueInt("Enter the roll number for the student: ");
        // System.out.println();

        int grade = takeInt("Enter the grade of the student: ", 1, 12);
        // System.out.println();

        LocalDate dateOfBirth = takeDate("Enter the student's date of birth in (dd-mm-yyyy): ");
        // System.out.println();

        String gender = takeString("Enter gender (M/F): ", "^(M|F)$", "Gender can be either M or F");
        // System.out.println();

        String address = takeString("Enter student's address: ", "^(?!\s*$).+", "Address cannot be empty");
        // System.out.println();

        String phoneNumber = takeString("Enter student's phone number: ", "^[0-9]{10}$", "Phone number must be 10 digits");
        // System.out.println();

        String email = takeString("Enter student's email: ", "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "Invalid email format");
        

        sms.addStudent(new Student(rollno, name, grade, gender, dateOfBirth, address, phoneNumber, email));

        System.out.println("\n\nSTUDENT'S DETAILS ADDED SUCCESSFULLY.\n");
        sms.delay("Returning to main menu...", 1800);

    } 

    public static void editDetails(){

        if(sms.students.isEmpty()){
            System.out.println("NO DATA FOUND. PLEASE ADD DATA FIRST.");
            sms.delay("Returning to main menu...", 1400);
            return;
        }

        int rollNumber = takeInt("Enter the roll number of the student to edit details: ", 1, 500);
        Student student = sms.searchStudentByRollNumber(rollNumber);

        if(student == null){
            System.out.println("\nNO STUDENT WITH THAT ROLL NUMBER IS FOUND.\n");
            sms.delay("Returning to main menu...", 1400);
            
            return;
    }

        String newName = takeString("Enter the new name of the student\n(PRESS ENTER TO SKIP): ", "^$|^[a-zA-Z ]+$", "Name must only contain alphabets");
        
        if(!newName.isBlank()) {
            student.setName(newName);
            System.out.println("Name updated successfully.");
        }

        int newGrade = takeInt("Enter the new grade of the student\n(ENTER 0 TO SKIP): ", 0, 12);
        
        if(newGrade != 0) {
            student.setGrade(newGrade);
            System.out.println("Grade updated successfully.");
        }

        String newDateOfBirth = takeString("Enter the new date of birth of the student(dd-mm-yyyy)\n(PRESS ENTER TO SKIP): ");

        if(!newDateOfBirth.isBlank()) {

            try {
            LocalDate dateOfBirth = LocalDate.parse(newDateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            student.setDateOfBirth(dateOfBirth);
            System.out.println("Date of birth updated successfully.");
            } catch (Exception e) {
                System.out.println("Skipping date of birth update.");
            }

        }

        String newGender = takeString("Enter the new gender of the student\n(PRESS ENTER TO SKIP): ", "^$|^(M|F)?$", "Gender can be either M or F");

        if(!newGender.isBlank()) {
            student.setGender(newGender);
            System.out.println("Gender updated successfully.");
        }

        String newAddress = takeString("Enter the new address of the student\n(PRESS ENTER TO SKIP): ", "^$|^(?!\s*$).+", "Address cannot be empty");

        if(!newAddress.isBlank()) {
            student.setAddress(newAddress);
            System.out.println("Address updated successfully.");
        }

        String newPhoneNumber = takeString("Enter the new phone number of the student\n(PRESS ENTER TO SKIP): ", "^[0-9]{0,10}$", "Phone number must be 10 digits");

        if(!newPhoneNumber.isBlank()) {
            student.setPhoneNumber(newPhoneNumber);
            System.out.println("Phone number updated successfully.");
        }

        String newEmail = takeString("Enter the new email of the student\n(PRESS ENTER TO SKIP): ", "^$|^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "Invalid email format");

        if(!newEmail.isBlank()) {
            student.setEmail(newEmail);
            System.out.println("Email updated successfully.");
        }

        sms.saveStudents();
        System.out.println("\nDETAILS UPDATED SUCCESSFULLY.\n");

        sms.delay("Returning to main menu...", 1800);
    }

    public static void searchStudent(){

        if(sms.students.isEmpty()){
            System.out.println("\nNO DATA FOUND. PLEASE ADD DATA FIRST.\n");
            return;
        }

        System.out.println("Search By:\n1. Roll Number\n2. Name\n3. Grade\n4. Gender");

        int choiceOfSearch = takeInt("Enter your choice: ", 1, 4);
        switch(choiceOfSearch){
            case 1:
                int rollNumber = takeInt("Enter roll number: ", 1, 500);
                Student searchByRollNumber = sms.searchStudentByRollNumber(rollNumber);
                if(searchByRollNumber == null){
                    System.out.println("\nNO STUDENT WITH THAT ROLL NUMBER IS FOUND.\n");
                    return;
                }
                System.out.println(searchByRollNumber.toString());
                break;
            case 2:
                String name = takeString("Enter name: ", "^[a-zA-Z ]+$", "Name must only contain alphabets");
                List<Student> searchByName = sms.searchStudentByName(name);
                if(searchByName == null){
                    return;
                }
                for(Student stu : searchByName){
                    System.out.println(stu.toString());
                }
                break;
            case 3:
                int grade = takeInt("Enter grade: ", 1, 12);
                List<Student> searchByGrade = sms.searchStudentByGrade(grade);
                if(searchByGrade == null){
                    return;
                }
                for(Student stu : searchByGrade){
                    System.out.println(stu.toString());
                }
                break;
            case 4:
                String gender = takeString("Enter gender: ", "^(M|F)?$", "Gender can be either M or F");
                List<Student> searchByGender = sms.searchStudentByGender(gender);
                if(searchByGender == null){
                    return;
                }
                for(Student stu : searchByGender){
                    System.out.println(stu.toString());
                }
                break;
        }

        System.out.println("\n\nSEARCH COMPLETED SUCCESSFULLY.");
        
        sms.delay("Returning to main menu...", 1800);
    }

    private static String takeString(String command){
        System.out.println("\n" + command);
        return sc.nextLine().trim();
    }

    private static String takeString(String command, String regex, String errorMessage){
        while(true){
            System.out.println("\n" + command);
            String input = sc.nextLine();
            if(input.matches(regex)){
                return input;
            }
            System.out.println("\n" + errorMessage);
        }
    }

    private static int takeInt(String command, int MIN, int MAX){
        while(true){
            System.out.println("\n" + command);
            try{
                int input = Integer.parseInt(sc.nextLine());
                if(input >= MIN && input <= MAX){
                    return input;
                }
                System.out.println("Enter a number between " + MIN + " and " + MAX + ".");
            } catch (NumberFormatException ne){
                System.out.println("\nEnter a valid number.\n");
            }
        }
    }

    private static int takeUniqueInt(String command){
        while(true){
            int rollNumber = takeInt(command, 1, Integer.MAX_VALUE);
            if(sms.isRollNumberUnique(rollNumber)){
                return rollNumber;
            }
            System.out.println("\nROLL NUMBER ALREADY EXISTS.\nKINDLY ENTER A UNIQUE ROLL NUMBER.\n");
        }
    }

    private static LocalDate takeDate(String command){
        while(true){
            System.out.println("\n" + command);
            try {
                return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch(Exception e){
                System.out.println("\nEnter a valid date in (dd-mm-yyyy) format.\n");
            }
        }
    }
}