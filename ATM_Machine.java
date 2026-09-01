package ATM;
import java.util.Scanner;

public class ATM_Machine {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double balance = 10000.0; // Starting money
        int choice = 0;

        // PIN Authentication Setup
        final int CORRECT_PIN = 123456;
        int attempts = 4;
        boolean authenticated = false;

        System.out.println("++++++++++++++++++++\n WELCOME TO THE ATM\n++++++++++++++++++++\n");

        // PIN Verification Loop
        while (attempts > 0) {
            System.out.print("Enter your 6-digit PIN: ");
            int enteredPin = in.nextInt();

            if (enteredPin == CORRECT_PIN) {
                authenticated = true;
                System.out.println("Access Granted!\n");
                break;
            } else {
                attempts--;
                System.out.println("Incorrect PIN. Attempts remaining: " + attempts);
            }
        }

        // If authentication failed after all attempts, exit the program
        if (!authenticated) {
            System.out.println("Too many incorrect attempts. Your card is blocked!");
            in.close();
            return; // Terminate execution
        }

        while (choice != 4) {
            System.out.print("+++++++++++\nDASH BOARD\n+++++++++++\n");
            System.out.print(" 1.Balance\n 2.Deposit\n 3.Withdraw \n 4.Exit\nSELECT OPTION:");
            choice = in.nextInt();

            if (choice == 1) {
                System.out.println("Balance: FCFA  " + balance);
            } else if (choice == 2) {
                System.out.print("Amount to deposit: FCFA  ");
                balance += in.nextDouble();
                System.out.println("Updated Balance: FCFA  " + balance);
                System.out.println("Deposit succcessfull");
            } else if (choice == 3) {
                System.out.print("Amount to withdraw: FCFA  ");
                double amount = in.nextDouble();
                if (amount <= balance) {
                    balance -= amount;
                    System.out.println("Updated Balance: FCFA  " + balance);
                    System.out.println("withdrawal succcessfull");
                } else {
                    System.out.println("Error: Not enough money!");
                }
            }
        }
        System.out.println("++++Goodbye and have a great day++++!");
        in.close();
    }
}
