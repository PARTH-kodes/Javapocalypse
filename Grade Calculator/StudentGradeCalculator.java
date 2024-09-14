import java.util.*;
// import java.io.*;

/* Class represening a Student with subject and marks obtained information */
class Student{
    String subjectName; // Subject name
    int marksSubject;   // Marks scored
    HashMap<String, Integer> students = new HashMap<String, Integer>(); // Map to store subject and marks obtained

    /* Default constructor for the object */
    Student(){
    }

    /* Parameterised constructor to initialise subject name and marks scored */
    Student(String subjectName, int marksSubject){
        this.subjectName = subjectName;
        this.marksSubject = marksSubject;
    }

    /* Getters and Setters */

    // Get the subject name
    public String getSubjectName(){
        return subjectName;
    }

    // Get the marks scored
    public int getmarksSubject(){
        return marksSubject;
    }

    // Add subject and marks of the student to the map
    public void addStudent(String subjectName, int marksSubject){
        students.put(subjectName, marksSubject);
    }
}

/* Main class to calculate and display student's grades */
public class StudentGradeCalculator
{   
    static Scanner sc = new Scanner(System.in); // SCanner for user input
    static Student st = new Student();  // Student object to refer Student class and attributes
    static int totalMarks = 0;  // Variable to store total marks
    static double percentage = 0.0; // Variable to store percentage
    static String grade = "";   // Variable to store grade

    /* Main function to start the program */
    public static void main(String[] args)
    {
        System.out.println("\n\nSTUDENT GRADE CALCULATOR");

        while(true){

            // Display the menu of the application
            System.out.println("\n\n1. Add subject and marks scored. (out of 100)\n2. Calculate Total Marks.\n3. Calculate Percentage.\n4. Assign Grade based on percentage.\n5. View Result.\n6. Exit");

            int choice = getInt("Please enter your choice:", 1, 6);

            switch(choice) {
                case 1:
                    addSubjectandMarks();
                    break;
                case 2:
                    calculateTotalMarks();
                    if(totalMarks != 0) System.out.println("\nTotal Marks Obtained: " + totalMarks);
                    break;
                case 3:
                    calculatePercentage();
                    if(totalMarks != 0) System.out.println("\nPercentage: " + percentage + " %");
                    break;
                case 4:
                    assignGrade();
                    if(!grade.isBlank()) System.out.println("\nGrade: " + grade);
                    break;
                case 5:
                    viewResult();
                    
                case 6:
                    System.out.println("\nThank You for using our services. Good Bye!\n");
                    try{
                        System.out.println("Exiting....\n");
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch(InterruptedException e){
                        System.exit(0);
                    }
            }
        }
    }

    // Method to add subject names and their corresponding marks
    public static void addSubjectandMarks(){

        while(true){

            // Subject and Marks input and validation
            String subjectName = getString("Enter the subject name:", "^[a-zA-Z. ]+$", "Enter a valid subject name.").toUpperCase();
            int marksSubject = getInt("Enter marks scored:", 0, 100);

            // Add subject and marks to the map
            st.addStudent(subjectName, marksSubject);

            System.out.println("\n**MARKS ADDED SUCCESSFULLY.**");

            // Ask if user wants to add more subjects
            if(getString("Add another subject? [Y/N]", "[yYnN]", "Kindly enter Y or N").equalsIgnoreCase("N")){
                System.out.println("\nReturning to main menu...");
                try{
                    Thread.sleep(1500);
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                return;
            }
        }
    }

    // Method to calculate total marks
    public static void calculateTotalMarks(){
        if(st.students.isEmpty()){
            System.out.println("\nNo subjects added. Please add atleast 1 subject.");
            return;
        }

        try{
            System.out.println("\nCalculating total marks...\n");
            Thread.sleep(1500);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    
        for(Map.Entry<String, Integer> entry : st.students.entrySet()){
            totalMarks += entry.getValue();
        }
    }

    // Method to calculate percentage
    public static void calculatePercentage(){

        if(st.students.isEmpty()){
            System.out.println("\nNo subjects added. Please add atleast 1 subject.");
            return;
        }

        if (totalMarks == 0){
            calculateTotalMarks();
        }

        try{
            System.out.println("\nCalculating the Percentage...\n");
            Thread.sleep(1500);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        // Calculate percentage and round it upto 2-decimal places
        percentage = Math.round((totalMarks * 100) / (st.students.size() * 100.0) * 100.0) / 100.0;

    }

    // Method to assign grade based on the percentage obtained
    public static void assignGrade(){

        if(st.students.isEmpty()){
            System.out.println("\nNo subjects added. Please add atleast 1 subject.");
            return;
        }

        if (percentage == 0){
            calculatePercentage();
        }

        try{
            System.out.println("\nAssigning Grade based on Percentage...\n");
            Thread.sleep(1500);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        // Determining grade based on percentage
        if(percentage >= 90){
            grade = "A";
        }else if(percentage >= 80){
            grade = "B";
        }else if(percentage >= 70){
            grade = "C";
        }else if(percentage >= 60){
            grade = "D";
        }else if(percentage >= 40){
            grade = "E";
        } else{
            grade = "F";
        }
    }

    // Method to display the entire result of the student
    public static void viewResult(){

        if(st.students.isEmpty()){
            System.out.println("\nNo subjects added. Please add atleast 1 subject.");
            return;
        }

        if(grade.isBlank()){
            assignGrade();
        }

        try{
            System.out.println("\nGenerating Result...\n");
            Thread.sleep(2500);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n**STUDENT'S RESULT**\n");
        
        for(Map.Entry<String, Integer> entry : st.students.entrySet()){
            String key = entry.getKey();
            int value = entry.getValue();

            if (key.endsWith(".")) {
                key.replace(".", " ");
            }
            System.out.println(key + " : " + value);
        }

        System.out.println("\nTOTAL MARKS OBTAINED: " + totalMarks);
        System.out.println("MAXIMUM MARKS: " + st.students.size() * 100);
        System.out.println("\nPERCENTAGE: " + percentage + " %");
        System.out.println("\nGRADE RECEIVED: " + grade);

        try{
            Thread.sleep(4000);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("\nReturning to main menu...");
        return;
        
    }

    // Method to take and validate integer input from the user and error handling
    private static int getInt(String command, int MIN, int MAX){
        while(true){
            System.out.println("\n" + command);
            try{
                int input = Integer.parseInt(sc.nextLine().trim());
                if(input >= MIN && input <= MAX){
                    return input;
                }
                System.out.println("Enter a number between " + MIN + " and " + MAX + ".");
            } catch (NumberFormatException ne){
                System.out.println("\nEnter a valid number.\n");
            }
        }
    }

    // Method to take and validate string input from the user and error handling
    private static String getString(String command, String regex, String errorMessage){
        while(true){
            System.out.println("\n" + command);
            String input = sc.nextLine().trim();
            if(input.matches(regex)){
                return input;
            }
            System.out.println("\n" + errorMessage);
        }
    }
}