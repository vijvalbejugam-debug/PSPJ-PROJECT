import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    String date;
    String type;
    double amount;
    String description;

    Transaction(String date, String type, double amount, String description) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }
}

public class PersonalBankLedger {
    static ArrayList<Transaction> ledger = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static double balance = 0.0;

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== PERSONAL BANK LEDGER =====");
            System.out.println("1. Add Deposit");
            System.out.println("2. Add Withdrawal");
            System.out.println("3. View Ledger");
            System.out.println("4. Generate Statement");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addTransaction("Deposit");
                    break;
                case 2:
                    addTransaction("Withdrawal");
                    break;
                case 3:
                    viewLedger();
                    break;
                case 4:
                    generateStatement();
                    break;
                case 5:
                    System.out.printf("Current Balance: Rs. %.2f%n", balance);
                    break;
                case 6:
                    System.out.println("Thank you for using Personal Bank Ledger!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    static void addTransaction(String type) {
        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Enter amount: Rs. ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (type.equals("Withdrawal") && amount > balance) {
            System.out.println("Insufficient balance. Transaction cancelled.");
            return;
        }

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        ledger.add(new Transaction(date, type, amount, description));

        if (type.equals("Deposit"))
            balance += amount;
        else
            balance -= amount;

        System.out.println(type + " added successfully!");
    }

    static void viewLedger() {
        if (ledger.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\n---------------- LEDGER ----------------");
        System.out.printf("%-12s %-12s %-12s %-20s%n",
                "Date", "Type", "Amount", "Description");

        for (Transaction t : ledger) {
            System.out.printf("%-12s %-12s Rs.%-9.2f %-20s%n",
                    t.date, t.type, t.amount, t.description);
        }
    }

    static void generateStatement() {
        if (ledger.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        double deposits = 0;
        double withdrawals = 0;

        System.out.println("\n========== BANK STATEMENT ==========");
        for (Transaction t : ledger) {
            if (t.type.equals("Deposit"))
                deposits += t.amount;
            else
                withdrawals += t.amount;
        }

        System.out.printf("Total Deposits    : Rs. %.2f%n", deposits);
        System.out.printf("Total Withdrawals : Rs. %.2f%n", withdrawals);
        System.out.printf("Closing Balance   : Rs. %.2f%n", balance);
        System.out.println("=====================================");
    }
}
