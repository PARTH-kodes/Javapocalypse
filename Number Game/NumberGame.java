import java.util.*;
import java.io.*;
import java.awt.*;
import java.net.*;


public class NumberGame
{
    /* Static objects for Scanner and Random classes */
    static Scanner sc = new Scanner(System.in);
    static Random rnd = new Random();
    
    public static void main(String[] args)
    {
        // Game Settings
        int lower_limit = 1;
        int upper_limit = 100;
        int currScore = 0;
        int totalScore = 0;

        //Main loop for the Game
        while(true){
            openingGreetandInstructions();  // Displaying opening greetingss and instructions
            
            // Get the maximum attempts from the user, max is 10.
            int maxAttempts = takeInt("How many attempts you think, you need?", 1, 10, "No more than 10 attempts allowed!");
            boolean streak = true;
            int targetNumber = rnd.nextInt(upper_limit - lower_limit + 1) + lower_limit;
            
            int usedAttempts = 0;
            boolean guessedCorrectly = false;

            /* Loop for the user's guessing attempts */
            while(usedAttempts <= maxAttempts && !guessedCorrectly){
                currScore = 0;

                // Prompt user for their guess within the specified limits
                int userGuess = takeInt("Make your guess, old friend: ", lower_limit, upper_limit, "No, no, no...stay within limits!\nEnter a number between " + lower_limit + " and " + upper_limit);
                usedAttempts++;

                // Delay for a real time user experience
                try{
                    Thread.sleep(1200);
                } catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }

                // Check if the user guessed correctly and provide feedback
                if(guessResult(userGuess, targetNumber)){
                    guessedCorrectly = true;
                    System.out.println("\n**HURRAAHH..!! You hit the BULL'S EYE.**");
                    currScore++;
                } else if(userGuess < targetNumber){
                    System.out.println("\n*You didn't reach there, climb up big boy.*\n");
                }else{
                    System.out.println("\n*Woah woah, come down, you are ahead of your destiny.*\n");
                }

            } 

            totalScore = (currScore == 0) ? totalScore : currScore; //Update total score only when the user guesses correctly

            /* Surprise Link, if the user did not guess correctly */
            if(currScore == 0){
                streak = false;
                System.out.println("\nLooks like you are a noob.\nLets cheer you up...");
                openSurprise("https://youtu.be/dQw4w9WgXcQ?si=PALqp8LvjJgX7HMe");
            } else if(streak && totalScore % 3 == 0) {  // 5 point streak reqard link 
                System.out.println("\nCongratulations! On your 5-point streak...\nHere's your reward!\n");
                openSurprise("https://youtu.be/9bZkp7q19f0?si=sVDF956oSXKCnzze&t=69");
            }

            /* Delay before asking to play again */
            try{
                Thread.sleep(3000);
            } catch(InterruptedException e){
                System.out.println(e.getMessage());
            }

            // Ask if they user would like to play again
            System.out.println("\nLet's play again?\t[Y/N]");
            char playAgainChoice = Character.toUpperCase(sc.next().charAt(0));

            if(playAgainChoice == 'N'){
                System.out.println("\nHey, A big thanks for playing.\n\nYou have scored " + totalScore + " points");

                System.out.println("\nThanks for sticking this long.\nUntil we meet again!\n");
                System.exit(0);
            }

            sc.close();
        }
    }

    /* METHODS */

    /* Method for opening greeting and instructions */
    public static void openingGreetandInstructions(){

        System.out.println("\n\n======= WELCOME TO THE NUMBER GUESSING GAME =======\n");
        System.out.println("\nGUIDELINES:\n\nBefore entering into the game, read the instructions for better interaction with the game.\n");
        System.out.println(
            "1. You have to guess a number between 1 and 100.\n2. You will be asked the maximum number of attempts you might require in order to correctly guess the number.\n3. However, the limit of maximum attempts is 10.\n4. Every correct guess within the specified number of attempts scores +1 point.\n5. Try to guess the number in as few attempts as possible.\n6. You can play again, if you want and can exit at any moment.\n7. You will get a surprise reward. if you score more than 5 points(i.e, 5 games, 5 correct guesses) in a row.\n7. Well that's it. Good Luck and have FUN...\n"            
        );
        
        System.out.println("\nPRESS ENTER TO BEGIN...");
        sc.nextLine();  //Waiting for user, to start...
        
        System.out.println("\nI am thinkning of a number between 1 and 100. Let's see if you can guess it?");
        try{
            System.out.println("\nThinking.....");
            Thread.sleep(2900);
            System.out.println("\nOkk. I am *READY*. Begin whenever you are....");
        } catch(InterruptedException e){
            System.err.println("\nLooks like I have trouble thinking straight...\n\nKindly **RESTART** the game...");
            Thread.currentThread().interrupt();
            System.exit(0);
        }
        
        System.out.println("\nPRESS ANY KEY TO BEGIN THE QUEST....");
        sc.nextLine();  //Waiting for user to proceed...

        return;
    }

    /* Method to check if the user guessed correctly */
    private static boolean guessResult(int userGuess, int targetNumber){
        return userGuess == targetNumber;
    }

    /* Method to take and validate integer input from the user and error handling */
    private static int takeInt(String command, int MIN, int MAX, String errorMessage){

        while(true){
            try{

                // Taking user input
                System.out.println("\n" + command);
                int userInput = sc.nextInt();

                // Validating user input
                if(MIN <= userInput && MAX >= userInput){
                    return userInput;
                } else{
                    System.out.println("\n" + errorMessage);
                }

            }catch(NumberFormatException e){    //Erorr handling
                System.out.println("\nInvalid input. Kindly enter a number between " + MIN + " and " + MAX);
            }

            sc.nextLine();  // Clearing the buffer
        }
    }

    /* Method to open the URL in the default browser as a surprise */
    public static void openSurprise(String url){
        
        try {
            URI uri = new URI(url);
            if(Desktop.isDesktopSupported()){
                Desktop desk = Desktop.getDesktop();
                if(desk.isSupported(Desktop.Action.BROWSE)){
                    desk.browse(uri);   // Open the link in the default browser
                } else {
                    System.err.println("BROWSE action is not supported on your system.");
                }
            } else {
                System.err.println("Desktop is not supported on your system.");
            }
        } catch(URISyntaxException e){
            System.err.println("The URL is not formatted correctly.");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("AN I/O error occurred while trying to open the URL.");
            e.printStackTrace();
        }

    }
}