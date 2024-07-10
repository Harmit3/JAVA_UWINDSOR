import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Interface for BankOperations to define common banking operations
interface BankOperations {
    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;
    void transfer(Account toAccount, double amount) throws InsufficientFundsException, InvalidAmountException;
    void printAccountDetails();
    void writeToFile(String filename);
}

// Custom exception for invalid amounts in transactions
class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

// Custom exception for insufficient funds during withdrawals and transfers
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Base class Account to manage individual account details and transactions
abstract class Account implements BankOperations {
    private String accountNumber;
    private String name;
    private double balance;
    private ArrayList<Transaction> transactions; // To store transaction history
    private Lock lock; // Lock for thread-safe access to account data

    // Constructor to initialize account with account number, name, and initial balance
    public Account(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction("Account opened with initial balance", initialBalance));
        this.lock = new ReentrantLock();
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account holder's name
    public String getName() {
        return name;
    }

    // Setter for account holder's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for current balance
    public double getBalance() {
        return balance;
    }

    // Getter for transaction history
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // Method to print account details and transaction history to console
    public void printAccountDetails() {
        lock.lock();
        try {
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Name: " + name);
            System.out.println("Balance: " + balance);
            System.out.println("Transactions: ");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to write account details and transaction history to a file
    public void writeToFile(String filename) {
        lock.lock();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println("Account Number: " + accountNumber);
            writer.println("Name: " + name);
            writer.println("Balance: " + balance);
            writer.println("Transactions: ");
            for (Transaction transaction : transactions) {
                writer.println(transaction);
            }
            writer.println();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    // Method to deposit money into the account
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount for deposit: " + amount);
        }
        lock.lock();
        try {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
        } finally {
            lock.unlock();
        }
    }

    // Method to withdraw money from the account
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount for withdrawal: " + amount);
        }
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                transactions.add(new Transaction("Withdrawal", amount));
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal: " + amount);
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to transfer money to another account
    public void transfer(Account toAccount, double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount for transfer: " + amount);
        }
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                transactions.add(new Transaction("Transfer to " + toAccount.getAccountNumber(), amount));
                toAccount.deposit(amount);
            } else {
                throw new InsufficientFundsException("Insufficient funds for transfer: " + amount);
            }
        } finally {
            lock.unlock();
        }
    }
}

// CheckingAccount class inheriting from Account for checking accounts
class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, String name, double initialBalance) {
        super(accountNumber, name, initialBalance);
    }
}

// SavingsAccount class inheriting from Account for savings accounts
class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, String name, double initialBalance) {
        super(accountNumber, name, initialBalance);
    }
}

// Transaction class to manage individual transactions
class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": " + amount;
    }
}

// Bank class to manage multiple accounts and provide banking operations
class Bank {
    private HashMap<String, Account> accounts; // HashMap to store accounts using account number as key
    private int accountCounter; // Counter to generate unique account numbers

    // Constructor to initialize the bank with an empty HashMap and starting account number
    public Bank() {
        accounts = new HashMap<>();
        accountCounter = 1;
    }

    // Method to open a new checking account with a given name and initial balance
    public Account openCheckingAccount(String name, double initialBalance) {
        String accountNumber = "CHK" + accountCounter++; // Generate account number
        Account account = new CheckingAccount(accountNumber, name, initialBalance); // Create new checking account
        accounts.put(accountNumber, account); // Add account to HashMap
        return account;
    }

    // Method to open a new savings account with a given name and initial balance
    public Account openSavingsAccount(String name, double initialBalance) {
        String accountNumber = "SAV" + accountCounter++; // Generate account number
        Account account = new SavingsAccount(accountNumber, name, initialBalance); // Create new savings account
        accounts.put(accountNumber, account); // Add account to HashMap
        return account;
    }

    // Method to get account details based on account number
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber); // Return account associated with given account number
    }

    // Method to find accounts by customer name
    public List<Account> findAccountsByCustomerName(String name) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                result.add(account);
            }
        }
        return result;
    }

    // Method to find accounts by account number
    public List<Account> findAccountsByAccountNumber(String accountNumber) {
        List<Account> result = new ArrayList<>();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            result.add(account);
        }
        return result;
    }

    // Method to find accounts by balance
    public List<Account> findAccountsByBalance(double balance) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getBalance() == balance) {
                result.add(account);
            }
        }
        return result;
    }

    // Method to print details of all accounts in the bank
    public void printAllAccounts() {
        for (Account account : accounts.values()) {
            account.printAccountDetails(); // Print details of each account
            System.out.println();
        }
    }

    // Method to generate a report of all accounts and save it to a file
    public void generateReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Account account : accounts.values()) {
                writer.println("Account Number: " + account.getAccountNumber());
                writer.println("Name: " + account.getName());
                writer.println("Balance: " + account.getBalance());
                writer.println("Transactions: ");
                for (Transaction transaction : account.getTransactions()) {
                    writer.println(transaction);
                }
                writer.println();
            }
            System.out.println("Report generated successfully: " + filename);
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}

// Main banking application class with CLI interface
public class Banking_Application {
    private static Bank bank = new Bank(); // Create a bank instance
    private static Scanner scanner = new Scanner(System.in); // Scanner for user input

    // Main method to run the banking application
    public static void main(String[] args) {
        while (true) {
            // Display menu options
            System.out.println("1. Open Checking Account");
            System.out.println("2. Open Savings Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Search Accounts by Customer Name");
            System.out.println("7. Search Accounts by Account Number");
            System.out.println("8. Search Accounts by Balance");
            System.out.println("9. Print All Accounts");
            System.out.println("10. Generate Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt(); // Read user choice

            switch (choice) {
                case 1:
                    openCheckingAccount();
                    break;
                case 2:
                    openSavingsAccount();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performWithdrawal();
                    break;
                case 5:
                    performTransfer();
                    break;
                case 6:
                    searchAccountsByCustomerName();
                    break;
                case 7:
                    searchAccountsByAccountNumber();
                    break;
                case 8:
                    searchAccountsByBalance();
                    break;
                case 9:
                    printAllAccounts();
                    break;
                case 10:
                    generateReport();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close(); // Close scanner before exiting
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            System.out.println(); // Add a blank line for readability
        }
    }

    // Method to open a checking account
    private static void openCheckingAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.next();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        Account account = bank.openCheckingAccount(name, initialBalance);
        System.out.println("Checking account opened successfully with account number: " + account.getAccountNumber());
    }

    // Method to open a savings account
    private static void openSavingsAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.next();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        Account account = bank.openSavingsAccount(name, initialBalance);
        System.out.println("Savings account opened successfully with account number: " + account.getAccountNumber());
    }

    // Method to perform a deposit into an account
    private static void performDeposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        try {
            Account account = bank.getAccount(accountNumber);
            if (account != null) {
                account.deposit(amount);
                System.out.println("Deposit successful. Updated balance: " + account.getBalance());
            } else {
                System.out.println("Account not found with account number: " + accountNumber);
            }
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount entered: " + amount);
        }
    }

    // Method to perform a withdrawal from an account
    private static void performWithdrawal() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        try {
            Account account = bank.getAccount(accountNumber);
            if (account != null) {
                account.withdraw(amount);
                System.out.println("Withdrawal successful. Updated balance: " + account.getBalance());
            } else {
                System.out.println("Account not found with account number: " + accountNumber);
            }
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }

    // Method to perform a transfer between accounts
    private static void performTransfer() {
        System.out.print("Enter account number to transfer from: ");
        String fromAccountNumber = scanner.next();
        System.out.print("Enter account number to transfer to: ");
        String toAccountNumber = scanner.next();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        try {
            Account fromAccount = bank.getAccount(fromAccountNumber);
            Account toAccount = bank.getAccount(toAccountNumber);
            if (fromAccount != null && toAccount != null) {
                fromAccount.transfer(toAccount, amount);
                System.out.println("Transfer successful.");
                System.out.println("Updated balance of " + fromAccountNumber + ": " + fromAccount.getBalance());
                System.out.println("Updated balance of " + toAccountNumber + ": " + toAccount.getBalance());
            } else {
                System.out.println("One or both accounts not found.");
            }
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Error during transfer: " + e.getMessage());
        }
    }

    // Method to search accounts by customer name
    private static void searchAccountsByCustomerName() {
        System.out.print("Enter customer name to search: ");
        String name = scanner.next();
        List<Account> accounts = bank.findAccountsByCustomerName(name);
        if (!accounts.isEmpty()) {
            System.out.println("Accounts found for customer " + name + ": ");
            for (Account account : accounts) {
                account.printAccountDetails();
            }
        } else {
            System.out.println("No accounts found for customer " + name);
        }
    }

    // Method to search accounts by account number
    private static void searchAccountsByAccountNumber() {
        System.out.print("Enter account number to search: ");
        String accountNumber = scanner.next();
        List<Account> accounts = bank.findAccountsByAccountNumber(accountNumber);
        if (!accounts.isEmpty()) {
            System.out.println("Account found: ");
            for (Account account : accounts) {
                account.printAccountDetails();
            }
        } else {
            System.out.println("No account found with account number: " + accountNumber);
        }
    }

    // Method to search accounts by balance
    private static void searchAccountsByBalance() {
        System.out.print("Enter balance to search: ");
        double balance = scanner.nextDouble();
        List<Account> accounts = bank.findAccountsByBalance(balance);
        if (!accounts.isEmpty()) {
            System.out.println("Accounts found with balance " + balance + ": ");
            for (Account account : accounts) {
                account.printAccountDetails();
            }
        } else {
            System.out.println("No accounts found with balance: " + balance);
        }
    }

    // Method to print details of all accounts in the bank
    private static void printAllAccounts() {
        System.out.println("Printing details of all accounts:");
        bank.printAllAccounts();
    }

    // Method to generate a report of all accounts
    private static void generateReport() {
        System.out.print("Enter filename to generate report (e.g., report.txt): ");
        String filename = scanner.next();
        bank.generateReport(filename);
    }
}
