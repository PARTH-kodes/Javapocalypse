# ATM Interface in Java

This is a console-based ATM Interface application implemented in Java. The program allows users to manage their bank accounts, including creating accounts, depositing money, withdrawing money, and checking their balance. Account data is persisted in a CSV file for future sessions.

## Features

- **Create a New Account**: Users can create a new account by entering their name and an initial balance.
- **Deposit Money**: Users can deposit money into their account.
- **Withdraw Money**: Users can withdraw money from their account, provided they have sufficient balance.
- **Check Balance**: Users can view their current account balance.
- **Data Persistence**: Account details (name and balance) are saved to a CSV file (`Accounts.csv`) and reloaded each time the program starts.

## File Structure

- **`ATMApplication.java`**: Main class to run the ATM program.
- **`ATM.java`**: Class representing the ATM machine, handles loading and saving account data, and manages the list of accounts.
- **`BankAccount.java`**: Class representing a bank account, includes methods for deposit, withdrawal, and balance check.

## How It Works

1. **Account Management**: 
   - On startup, the program prompts the user to enter their name.
   - The program then searches for an existing account in `Accounts.csv`.
   - If the account is found, the user is greeted and can proceed with ATM operations.
   - If not found, the user is offered the option to create a new account.

2. **ATM Operations**:
   - The user can choose to deposit money, withdraw money, or check their balance.
   - After performing any transactions, the updated balance is displayed.

3. **Data Persistence**:
   - The `Accounts.csv` file is used to store account data between sessions.
   - The file is updated with the latest balance when the user exits the program.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine.

### Running the Program

1. Compile the Java files:
    ```sh
    javac ATMApplication.java
    ```

2. Run the program:
    ```sh
    java ATMApplication
    ```

## CSV File
- The `Accounts.csv` file is automatically created in the same directory, as the program when the application is run for the first time
- The file contains account details in the format: `Account Holder, Account Balance`.