import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userId = 12345; // Replace with your user ID
        int pin = 6789; // Replace with your PIN
        double deposited = 0.0; // Total amount deposited
        List<String> transactionHistory = new ArrayList<>();

        System.out.println("Welcome to the ATM system!");

        // Authenticate the user
        System.out.print("Enter your user ID: ");
        int enteredUserId = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int enteredPin = scanner.nextInt();

        if (enteredUserId == userId && enteredPin == pin) {
            System.out.println("Authentication successful!");

            // Ask if the user wants to run the demo
            System.out.print("Do you want to run the demo? (yes/no): ");
            String demoChoice = scanner.next().toLowerCase();
            if (demoChoice.equals("yes")) {
                runDemo(deposited, transactionHistory);
            }

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. View Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // View transaction history
                        viewTransactionHistory(transactionHistory);
                        break;
                    case 2:
                        // Withdraw funds
                        System.out.print("Enter the withdrawal amount: $");
                        double withdrawAmount = scanner.nextDouble();
                        if (withdrawAmount > 0) {
                            if (withdrawAmount <= deposited) {
                                deposited -= withdrawAmount;
                                recordTransaction(transactionHistory, "Withdrawal: -$" + withdrawAmount);
                                System.out.println("Withdrawal successful.");
                            } else {
                                System.out.println("Warning: Withdrawal amount exceeds total deposited.");
                            }
                        } else {
                            System.out.println("Invalid withdrawal amount.");
                        }
                        break;
                    case 3:
                        // Deposit funds
                        System.out.print("Enter the deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        if (depositAmount > 0) {
                            deposited += depositAmount;
                            recordTransaction(transactionHistory, "Deposit: +$" + depositAmount);
                            System.out.println("Deposit successful.");
                        } else {
                            System.out.println("Invalid deposit amount.");
                        }
                        break;
                    case 4:
                        // Transfer funds
                        System.out.print("Enter the recipient's user ID: ");
                        int recipientUserId = scanner.nextInt();
                        System.out.print("Enter the amount to transfer: $");
                        double transferAmount = scanner.nextDouble();

                        if (recipientUserId == userId) {
                            System.out.println("Cannot transfer funds to yourself.");
                        } else if (transferAmount > 0 && transferAmount <= deposited) {
                            deposited -= transferAmount;
                            recordTransaction(transactionHistory, "Transfer to User " + recipientUserId + ": -$" + transferAmount);
                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Invalid transfer amount or insufficient funds.");
                        }
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

                // Display the remaining balance (total deposited) after each transaction
                System.out.println("Remaining balance (Total Deposited): $" + deposited);
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
        scanner.close();
    }

    private static void viewTransactionHistory(List<String> transactionHistory) {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    private static void recordTransaction(List<String> transactionHistory, String transaction) {
        transactionHistory.add(transaction);
    }

    private static void runDemo(double deposited, List<String> transactionHistory) {
        System.out.println("\nDemo Scenario:");
        System.out.println("1. Deposit $500");
        deposited += 500.0;
        recordTransaction(transactionHistory, "Deposit: +$500");

        System.out.println("2. Withdraw $300");
        if (300.0 <= deposited) {
            deposited -= 300.0;
            recordTransaction(transactionHistory, "Withdrawal: -$300");
        } else {
            System.out.println("Warning: Withdrawal amount exceeds total deposited.");
        }

        System.out.println("3. View Transaction History");
        viewTransactionHistory(transactionHistory);

        System.out.println("4. Transfer $200 to User 98765");
        int recipientUserId = 98765;
        double transferAmount = 200.0;
        if (recipientUserId != 12345 && transferAmount <= deposited) {
            deposited -= transferAmount;
            recordTransaction(transactionHistory, "Transfer to User " + recipientUserId + ": -$" + transferAmount);
        } else {
            System.out.println("Invalid transfer or insufficient funds.");
        }
    }
}
