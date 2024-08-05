package Bank;

// Account.java
// Base class to manage individual account details and transactions
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Base class Account to manage individual account details and transactions using generics
public abstract class Account<T> implements BankOperations<T> {
    private String accountNumber;
    private Customer customer; // Reference to Customer object
    private double balance;
    private ArrayList<Transaction> transactions;
    private Lock lock;

    // Constructor to initialize account details
    public Account(String accountNumber, Customer customer, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction("Account opened with initial balance", initialBalance));
        this.lock = new ReentrantLock();
    }

    // Getters for account details
    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // Method to print account details
    public void printAccountDetails() {
        lock.lock();
        try {
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
            System.out.println("Balance: " + balance);
            System.out.println("Transactions: ");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to write account details to a file
    public void writeToFile(String filename) {
        lock.lock();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println("Account Number: " + accountNumber);
            writer.println("Customer ID: " + customer.getCustomerId());
            writer.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
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

    // Method to deposit amount into the account
    public void deposit(double amount) throws InvalidAmountException {
        double depositAmount = (double) amount;
        if (depositAmount <= 0) {
            throw new InvalidAmountException("Invalid amount for deposit: " + depositAmount);
        }
        lock.lock();
        try {
            balance += depositAmount;
            transactions.add(new Transaction("Deposit", depositAmount));
        } finally {
            lock.unlock();
        }
    }

    // Method to withdraw amount from the account
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        double withdrawAmount = (double) amount;
        if (withdrawAmount <= 0) {
            throw new InvalidAmountException("Invalid amount for withdrawal: " + withdrawAmount);
        }
        lock.lock();
        try {
            if (balance >= withdrawAmount) {
                balance -= withdrawAmount;
                transactions.add(new Transaction("Withdrawal", withdrawAmount));
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal: " + withdrawAmount);
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to transfer amount to another account
    public void transfer(Account<?> toAccount, double amount) throws InsufficientFundsException, InvalidAmountException {
        double transferAmount = (double) amount;
        if (transferAmount <= 0) {
            throw new InvalidAmountException("Invalid amount for transfer: " + transferAmount);
        }
        lock.lock();
        try {
            if (balance >= transferAmount) {
                balance -= transferAmount;
                transactions.add(new Transaction("Transfer to " + toAccount.getAccountNumber(), transferAmount));
                toAccount.deposit(transferAmount);
            } else {
                throw new InsufficientFundsException("Insufficient funds for transfer: " + transferAmount);
            }
        } finally {
            lock.unlock();
        }
    }
}
