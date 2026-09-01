package ATM;
import java.util.Scanner;

// 1. The Account class encapsulates user data (balance, PIN) and core operations.
class Account {
    private double balance;
    private final int correctPin;

    // Constructor to initialize account details
    public Account(double initialBalance, int pin) {
        this.balance = initialBalance;
        this.correctPin = pin;
    }

    // Validates if the entered PIN matches the account PIN
    public boolean validatePin(int enteredPin) {
        return this.correctPin == enteredPin;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Handles logic for deposits with safety check
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    // Handles logic for withdrawals with safety checks
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// 2. The ATM class handles the presentation layer (menus, inputs, user feedback).
class ATM {
    private final Account account;
    private final Scanner in;

    public ATM(Account account) {
        this.account = account;
        this.in = new Scanner(System.in);
    }

    // Starts the ATM sequence
    public void start() {
        System.out.println("++++++++++++++++++++\n WELCOME TO THE ATM\n++++++++++++++++++++\n");
        
        if (authenticate()) {
            showDashboard();
        } else {
            System.out.println("Too many incorrect attempts. Your card is blocked!");
        }
        in.close();
    }

    // Handles the PIN authentication loop
    private boolean authenticate() {
        int attempts = 4;
        while (attempts > 0) {
            System.out.print("Enter your 6-digit PIN: ");
            if (in.hasNextInt()) {
                int enteredPin = in.nextInt();
                if (account.validatePin(enteredPin)) {
                    System.out.println("Access Granted!\n");
                    return true;
                }
            } else {
                in.next(); // Clear invalid non-integer token
            }
            attempts--;
            System.out.println("Incorrect PIN. Attempts remaining: " + attempts);
        }
        return false;
    }

    // Manages the dashboard user selection loop
    private void showDashboard() {
        int choice = 0;
        while (choice != 4) {
            System.out.print("+++++++++++\nDASH BOARD\n+++++++++++\n");
            System.out.print(" 1.Balance\n 2.Deposit\n 3.Withdraw \n 4.Exit\nSELECT OPTION: ");
            
            if (!in.hasNextInt()) {
                System.out.println("Error: Invalid choice. Select a number from 1 to 4.\n");
                in.next();
                continue;
            }
            
            choice = in.nextInt();
            handleMenuSelection(choice);
        }
    }

    // Routes the user choice to specific actions
    private void handleMenuSelection(int choice) {
        switch (choice) {
            case 1 -> System.out.println("Balance: FCFA " + account.getBalance() + "\n");
            case 2 -> performDeposit();
            case 3 -> performWithdrawal();
            case 4 -> System.out.println("++++ Goodbye and have a great day ++++!");
            default -> System.out.println("Error: Invalid choice. Please choose between 1 and 4.\n");
        }
    }

    private void performDeposit() {
        System.out.print("Amount to deposit: FCFA ");
        if (in.hasNextDouble()) {
            double amount = in.nextDouble();
            if (account.deposit(amount)) {
                System.out.println("Updated Balance: FCFA " + account.getBalance());
                System.out.println("Deposit successful.\n");
            } else {
                System.out.println("Error: Deposit amount must be greater than zero.\n");
            }
        } else {
            System.out.println("Error: Invalid input.\n");
            in.next();
        }
    }

    private void performWithdrawal() {
        System.out.print("Amount to withdraw: FCFA ");
        if (in.hasNextDouble()) {
            double amount = in.nextDouble();
            double temporaryBalance = account.getBalance();
            
            if (amount <= 0) {
                System.out.println("Error: Withdrawal amount must be greater than zero.\n");
            } else if (amount > temporaryBalance) {
                System.out.println("Error: Insufficient funds!\n");
            } else if (account.withdraw(amount)) {
                System.out.println("Updated Balance: FCFA " + account.getBalance());
                System.out.println("Withdrawal successful.\n");
            }
        } else {
            System.out.println("Error: Invalid input.\n");
            in.next();
        }
    }
}

// 3. Execution Entry Point
public class ATM_oop {
    public static void main(String[] args) {
        // Instantiate the core data model object
        Account userAccount = new Account(10000.0, 123456);
        
        // Pass the object to the ATM UI controller
        ATM atmInterface = new ATM(userAccount);
        
        // Execute application logic
        atmInterface.start();
    }
}
