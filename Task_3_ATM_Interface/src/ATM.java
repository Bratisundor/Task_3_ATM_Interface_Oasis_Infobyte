import java.util.List;
import java.util.Scanner;

public class ATM {
    Database db = new Database();
    Scanner scanner = new Scanner(System.in);
    String Name = null;

    public void Authentication() {
        System.out.println("*****Welcome to the ATM Interface*****");
        while (true) {
            System.out.print("\n 1. Login \t2. Exit \nPress: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    run();
                    return;
                case "2":
                    System.out.println("Thank you visit again!!!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please enter 1 for Login or 2 for Exit.");
                    break;
            }
        }
    }

    public void run() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Your Name : ");
        String Name = scanner.nextLine();

        User user = db.getUser(userId, pin);
        if (user == null) {
            System.out.println("Invalid User ID or PIN.");
            return;
        }

        boolean active = true;
        while (active) {
            System.out.println("*****Welcome "+Name+ " to the ATM Interface*****");
            System.out.println("\n1. Transaction History\t                   2. Withdraw\n3. Deposit\t                               4. Transfer\n                         5. Quit");
            System.out.print("Choose an option : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Transaction> transactions = db.getTransactions(user.getUserId());
                    transactions.forEach(System.out::println);
                    break;
                case "2":
                    withdraw(user);
                    break;
                case "3":
                    deposit(user);
                    break;
                case "4":
                    transfer(user);
                    break;
                case "5":
                    active = false;
                    System.out.println("Thank You " +Name+" Visit Again !!!!!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void withdraw(User user) {
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= user.getBalance()) {
            user.setBalance(user.getBalance() - amount);
            db.recordTransaction(user.getUserId(), new Transaction("Withdrawal", -amount));
            System.out.println("Withdrawal successful. New balance: ₹" + String.format("%.2f", user.getBalance()));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private void deposit(User user) {
        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        user.setBalance(user.getBalance() + amount);
        db.recordTransaction(user.getUserId(), new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: ₹" + String.format("%.2f", user.getBalance()));
    }

    private void transfer(User user) {
        System.out.print("Enter recipient User ID : ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer : ");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
            return;
        }

        User recipient = db.getUser(recipientId, "20221103");
        if (recipient == null) {
            System.out.println("Invalid recipient ID.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Please enter a positive amount to transfer.");
            return;
        }

        if (amount <= user.getBalance()) {
            user.setBalance(user.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            db.recordTransaction(user.getUserId(), new Transaction("Transfer to " + recipientId, -amount));
            db.recordTransaction(recipientId, new Transaction("Transfer from " + user.getUserId(), amount));
            System.out.println("Transfer successful!!!!!");
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }


    public static void main(String[] args) {
        new ATM().Authentication();
    }
}
