# Number Guessing Game

A simple Java console-based number guessing game where the user attempts to guess a randomly generated number within a specified range (1 to 100). The game provides feedback on each guess, maintains scores, and includes surprise rewards for the player.

## Features

- Guess a number between 1 and 100.
- User specifies the maximum number of attempts (up to 10).
- Feedback on whether the guess is too high, too low, or correct.
- Multiple rounds with scoring.
- Surprise rewards for failing to guess correctly or achieving a streak of correct guesses.
- Option to play again or exit after each round.

## How to Play

1. **Start the Game:**
   - Run the Java program.
   - Read the guidelines and press Enter to begin.

2. **Set Maximum Attempts:**
   - Enter the maximum number of attempts you think you need (1 to 10).

3. **Make a Guess:**
   - Enter a number between 1 and 100.
   - The game will provide feedback on whether your guess is too high, too low, or correct.

4. **Score Points:**
   - Each correct guess within the specified attempts scores 1 point.
   - Maintain a streak to earn surprise rewards.

5. **Play Again or Exit:**
   - After each round, choose whether to play again or exit the game.
   - Your total score will be displayed if you choose to exit.

## Requirements

- Java Development Kit (JDK) 8 or higher
- A terminal or command prompt to run the Java program

## Running the Game

1. Clone the repository or download the `NumberGame.java` file.
2. Open a terminal or command prompt.
3. Navigate to the directory containing `NumberGame.java`.
4. Compile the Java file:

   ```sh
   javac NumberGame.java
   ```
5. Run the compiled java program

   ```sh
   java NumberGame
   ```

## Code Structure

- ***Main Class :***
    - `NumberGame`: Contains the main game and loop and game logic.

- ***Methods :*** 

    - `openingGreetandInstructions()`: Displays the opening greetings and game instructions.
    - `takeInt(String command, int MIN, int MAX, String errorMessage)` : Prompts the user for an integer input within specified bounds and handles invalid input.
    - `guessResult(int userGuess, int targetNumber)` : Checks if the user's guess matches the target number.
    - `openSurprise(String url)` : Opens a URL in the default web browser as a surprise reward.