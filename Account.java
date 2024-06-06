package Bank;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Account {
   protected String accountNumber;
   protected String customerName;
   protected double balance;


   public Account(String accountNumber, String customerName, double balance) {
       this.accountNumber = accountNumber;
       this.customerName = customerName;
       this.balance = balance;
   }


   // Getters
   public String getAccountNumber() {
       return accountNumber;
   }

   public String getCustomerName() {
       return customerName;
   }

   public double getBalance() {
       return balance;
   }

   // Setters
   public void setAccountNumber(String accountNumber) {
       this.accountNumber = accountNumber;
   }

   public void setCustomerName(String customerName) {
       this.customerName = customerName;
   }

   public void setBalance(double balance) {
       this.balance = balance;
   }
}



class Bank {
   private List<Account> accounts;
   private Scanner scanner;


   public Bank() {
       accounts = new ArrayList<>();
       scanner = new Scanner(System.in);
   }


   public void run() {
       boolean exit = false;
       while (!exit) {
           displayMenu();
           int choice = scanner.nextInt();
           scanner.nextLine(); // Consume newline character


           switch (choice) {
               case 1:
                   createAccount();
                   break;
               case 2:
                   deposit();
                   break;
               case 3:
                   withdraw();
                   break;
               case 4:
                   transfer();
                   break;
               case 5:
                   updateAccountInfo();
                   break;
               case 6:
                   viewBalance();
                   break;
               case 7:
                   searchAccounts();
                   break;
               case 8:
                   generateReport();
                   break;
               case 9:
                   exit = true;
                   System.out.println("Exiting the banking system...");
                   break;
               default:
                   System.out.println("Invalid choice. Please try again.");
           }
       }
   }


   private void displayMenu() {
       System.out.println("\nBanking System Menu");
       System.out.println("1. Create Account");
       System.out.println("2. Deposit");
       System.out.println("3. Withdraw");
       System.out.println("4. Transfer");
       System.out.println("5. Update Account Information");
       System.out.println("6. View Balance");
       System.out.println("7. Search Accounts");
       System.out.println("8. Generate Report");
       System.out.println("9. Exit");
       System.out.print("Enter your choice: ");
   }


   private void createAccount() {
       System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
       System.out.print("Enter customer name: ");
       String customerName = scanner.nextLine();
       System.out.print("Enter initial balance: ");
       double balance = scanner.nextDouble();
       scanner.nextLine(); // Consume newline character


       Account account = new Account(accountNumber, customerName, balance);
       accounts.add(account);
       System.out.println("Account created successfully.");
   }


   private void deposit() {
       System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
       Account account = findAccount(accountNumber);
       if (account != null) {
           System.out.print("Enter amount to deposit: ");
           double amount = scanner.nextDouble();
           scanner.nextLine(); // Consume newline character
           account.setBalance(account.getBalance() + amount);
           System.out.println("Deposit successful. New balance: " + account.getBalance());
       } else {
           System.out.println("Account not found.");
       }
   }


   private void withdraw() {
       System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
       Account account = findAccount(accountNumber);
       if (account != null) {
           System.out.print("Enter amount to withdraw: ");
           double amount = scanner.nextDouble();
           scanner.nextLine(); // Consume newline character
           if (account.getBalance() >= amount) {
               account.setBalance(account.getBalance() - amount);
               System.out.println("Withdrawal successful. New balance: " + account.getBalance());
           } else {
               System.out.println("Insufficient balance.");
           }
       } else {
           System.out.println("Account not found.");
       }
   }


   private void transfer() {
       System.out.print("Enter source account number: ");
       String sourceAccountNumber = scanner.nextLine();
       Account sourceAccount = findAccount(sourceAccountNumber);
       if (sourceAccount != null) {
           System.out.print("Enter destination account number: ");
           String destinationAccountNumber = scanner.nextLine();
           Account destinationAccount = findAccount(destinationAccountNumber);
           if (destinationAccount != null) {
               System.out.print("Enter amount to transfer: ");
               double amount = scanner.nextDouble();
               scanner.nextLine(); // Consume newline character
               if (sourceAccount.getBalance() >= amount) {
                   sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                   destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                   System.out.println("Transfer successful.");
               } else {
                   System.out.println("Insufficient balance.");
               }
           } else {
               System.out.println("Destination account not found.");
           }
       } else {
           System.out.println("Source account not found.");
       }
   }


   private void updateAccountInfo() {
       System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
       Account account = findAccount(accountNumber);
       if (account != null) {
           System.out.print("Enter new customer name: ");
           String newCustomerName = scanner.nextLine();
           account.setCustomerName(newCustomerName);
           System.out.println("Account information updated successfully.");
       } else {
           System.out.println("Account not found.");
       }
   }


   private void viewBalance() {
       System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
       Account account = findAccount(accountNumber);
       if (account != null) {
           System.out.println("Account Balance: " + account.getBalance());
       } else {
           System.out.println("Account not found.");
       }
   }


   private void searchAccounts() {
       System.out.print("Enter search criteria (name or account number): ");
       String searchCriteria = scanner.nextLine();
       List<Account> matchingAccounts = new ArrayList<>();
       for (Account account : accounts) {
           if (account.getCustomerName().contains(searchCriteria) || account.getAccountNumber().contains(searchCriteria)) {
               matchingAccounts.add(account);
           }
       }
       if (matchingAccounts.isEmpty()) {
           System.out.println("No matching accounts found.");
       } else {
           System.out.println("Matching accounts:");
           for (Account account : matchingAccounts) {
               System.out.println("Account Number: " + account.getAccountNumber() + ", Customer Name: " + account.getCustomerName() + ", Balance: " + account.getBalance());
           }
       }
   }


   private void generateReport() {
       System.out.print("Enter report filename (e.g., report.txt): ");
       String filename = scanner.nextLine();
       try {
           FileWriter writer = new FileWriter(filename);
           writer.write("Bank Account Report\n\n");
           for (Account account : accounts) {
               writer.write("Account Number: " + account.getAccountNumber() + ", Customer Name: " + account.getCustomerName() + ", Balance: " + account.getBalance() + "\n");
           }
           writer.close();
           System.out.println("Report generated successfully.");
       } catch (IOException e) {
           System.out.println("An error occurred while generating the report: " + e.getMessage());
       }
   }


   private Account findAccount(String accountNumber) {
       for (Account account : accounts) {
           if (account.getAccountNumber().equals(accountNumber)) {
               return account;
           }
       }
       return null;
   }



   public static void main(String[] args) {
       Bank bank = new Bank();
       bank.run();
   }
}
