import java.io.*;
import java.util.*;
import java.nio.file.*;

// Class to represent the user's bank account
class BankAccount {
    ATM atm = new ATM(); // Instance of ATM class to manage accounts
    private String accountHolder; // Name of the account holder
    private double balance; // Balance of the account

    // Constructor to initialize the balance
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        if (initialBalance < 0) {
            balance = 0; // Set balance to 0 if the initial balance is negative
            System.out.println("Initial balance cannot be negative. Setting balance to 0.");
        } else {
            balance = initialBalance; // Set balance to the initial amount if it's positive
        }
    }

     // Setter and getter methods for BankAccount
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    // Add a new customer
    public void addCustomer(String userName, double initialBalance) {

        BankAccount newAccount = new BankAccount(userName, initialBalance);
        atm.accounts.add(newAccount);

        System.out.println("\nNew account created for " + userName + " with initial balance of Rs. " + initialBalance);
        atm.saveAccounts(); // Save the new account to the CSV file
    }

    // Method to deposit money into the account
    public void deposit(double amount) {

        atm.delayThread("Depositing Rs. " + amount + " into your account...", 2000);
        
        if (amount > 0) {
            balance += amount; // Update the balance after the deposit amount
            System.out.println("\nDeposit successful!\nAdded: Rs. " + amount + " into your account.\n\nCurrent balance: Rs. " + balance);
        } else {
            System.out.println("\nInvalid deposit amount.\n");
        }
        atm.delayThread("Returning to main menu...\n\n",2000);
    }

    // Method to withdraw money from the account
    public void withdraw(double amount) {

        atm.delayThread("Checking your account balance...", 1500);
        if (amount > balance) {
            System.out.println("Insufficient balance for the withdrawal of Rs. " + amount);
        } else if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else {
            
            if(ATMApplication.getString("\nAre you sure you want to withdraw Rs. " + amount + " from your account? [Y/N]\n", "[yYnN]", "\nKindly enter Y or N\n").equalsIgnoreCase("Y")) {
                atm.delayThread("Processing Withdrawal...\n", 2000);
                balance -= amount; // Update the balance after the withdrawal amount
                System.out.println("Withdrawal successful!\nAmount withdrawn: Rs. " + amount + " from your account.\n\nCurrent balance: Rs. " + balance);
            }
        }
        atm.delayThread("Returning to main menu...\n\n",2000);
    }

    // Method to check the current balance
    public void checkBalance() {
        atm.delayThread("Checking your account balance...", 1500);

        System.out.println("\nCurrent balance: Rs. " + balance);

        atm.delayThread("Returning to main menu...\n\n",2000);
    }
}

// Class to represent the ATM machine
class ATM {
    // private BankAccount account;
    // Scanner sc = new Scanner(System.in);

    List<BankAccount> accounts; //List to store Bank Accounts

    public ATM() {
        accounts = new ArrayList<>();   //Initialization
    }

    // Constructor to initialize the ATM with a bank account
    // public ATM(BankAccount account) {
    //     this.account = account;
    // }

    // Method to save the accounts to the CSV file
    public void saveAccounts(){
        
        delayThread("Saving Data in Accounts...", 3000);

        try(PrintWriter writer = new PrintWriter(new FileWriter("Accounts.csv"))){
            writer.println("Account Holder,Account Balance");
            for(BankAccount account : accounts){
                // System.out.println("\nSaving account: " + account.getAccountHolder() + " with balance: " + account.getBalance() + "\n");

                writer.println(account.getAccountHolder() + "," + account.getBalance());
            }
        } catch(IOException e){
            System.out.println("Error saving Accounts: " + e.getMessage());
        }
    }

    // Method to load the accounts from the CSV file
    public BankAccount loadAccounts(String accountHolder) {
        accounts.clear();   // Clear the existing accounts list

        if(!Files.exists(Paths.get("Accounts.csv"))){
            return null;    // Return null is account doesn't exist
        }

        delayThread("Loading Data from Accounts...", 3000);

        try (BufferedReader br = Files.newBufferedReader(Paths.get("Accounts.csv"))) {
            String line = br.readLine();
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                BankAccount account = new BankAccount(values[0], Double.parseDouble(values[1])); 
                accounts.add(account);

                if(account.getAccountHolder().equals(accountHolder)){
                    return account; // Return the matching account
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading Students: " + e.getMessage());
        }

        return null;
    }

    // Method to delay the execution of the program and display a message
    public void delayThread(String message, long milliseconds){
        try{
            if(!message.isBlank()){
                System.out.println("\n" + message);
            }
            Thread.sleep(milliseconds);

        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }    
}

// Main class to run the ATM program
public class ATMApplication {

    static BankAccount userAccount; // The user's bank account
    static ATM atmMachine = new ATM(); // The ATM machine
    static Scanner sc = new Scanner(System.in); // Scanner for user input

    public static void main(String[] args) {
        System.out.println("\n==== ATM MACHINE: =====");

        // Prompt the user to enter their name
        String accountHolder = getString("\nEnter your name:\n", "[a-zA-Z]+", "\nInvalid name. Please enter a valid name.");

        // Load the account details for the current user
        userAccount = atmMachine.loadAccounts(accountHolder);

        if(userAccount == null){
            // If account is not found, prompt the user to create a new account
            
            System.out.print("\nUnable to find account details for - " + accountHolder + "\n\nWould you like to create a new account?");
        
            if(getString(" [Y/N]\n", "[YyNn]", "\nKindly enter Y or N").equalsIgnoreCase("Y")){
                
                delayThread("Creating a new account...", 2000);
                
                double initialBalance = getDouble("\nEnter initial balance:\nRs. ", 1000, 10000);
                userAccount = new BankAccount(accountHolder, initialBalance);

                atmMachine.accounts.add(userAccount);   // Add the new account to the list of accounts
                
                delayThread("Account Created Succesfully!\nReturning to main menu...\n\n", 2000);
                
                System.out.println("Welcome " + accountHolder.toUpperCase() + "!\nWhat would you like to do today?\n");
            }

            else{
                System.out.println("\nThank you for using the ATM. Goodbye!\n");
                sc.close();
                System.exit(0); // Exit the program, if user does not want to create a new account
            }
        
        } else{
            // If the account is found, welcome the user
            System.out.println("\nWelcome back " + accountHolder.toUpperCase() + "!\nWhat would you like to do today?\n");
        }

        /* Main loop to dispay the ATM menu and handle user choices. */
        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit (necessary to save data)\n");

            int choice = (int)getDouble("\nChoose an option: ", 1, 4);

            switch (choice) {
                
                case 1:
                    // Handle deposit
                    double depositAmount = getDouble("\nEnter amount to deposit:\nRs. ", 1, 10000);
                    userAccount.deposit(depositAmount);

                    // Update the account balance in the ATM List
                    for(BankAccount account : atmMachine.accounts){
                        if(account.getAccountHolder().equals(userAccount.getAccountHolder())){
                            account.setBalance(userAccount.getBalance());
                        }
                    }

                    break;

                case 2:
                    // Handle withdraw
                    System.out.print("\nEnter amount to withdraw:\nRs. ");
                    double withdrawAmount = sc.nextDouble();
                    sc.nextLine();
                    userAccount.withdraw(withdrawAmount);

                    // Update the account balance in the ATM List
                    for(BankAccount account : atmMachine.accounts){
                        if(account.getAccountHolder().equals(userAccount.getAccountHolder())){
                            account.setBalance(userAccount.getBalance());
                        }
                    }
                    
                    break;

                case 3:
                    // Handle check balance
                    userAccount.checkBalance();
                    break;

                case 4:
                    // Exit the program and save account data
                    atmMachine.saveAccounts();
                    System.out.println("\nThank you for using the ATM. Goodbye!\n");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }
        }
    }

    // Method to take and validate double input from the user and error handling
    public static double getDouble(String command, double MIN, double MAX){
        while(true){
            System.out.print(command);
            try{
                double input = Double.parseDouble(sc.nextLine().trim());
                if(input >= MIN && input <= MAX){
                    return input;
                }
                System.out.println("Enter a number between " + MIN + " and " + MAX);
            } catch (NumberFormatException ne){
                System.out.println("\nEnter a valid number.\n");
            }
        }
    }

    // Method to take and validate string input from the user and error handling
    public static String getString(String command, String regex, String errorMessage) {
        
        while (true) {

            try{
                
                System.out.print(command);
                String input = sc.nextLine().trim();

                if (input.matches(regex)) {
                    return input;
                }
                System.out.println("\n" + errorMessage);

            } catch (Exception e) {
                System.out.println("\n" + errorMessage);
            }
            
        }
    }

    // Method to delay the execution of the program and display a message
    public static void delayThread(String message, long milliseconds){
        try{
            if(!message.isBlank()){
                System.out.println("\n" + message);
            }
            Thread.sleep(milliseconds);

        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}