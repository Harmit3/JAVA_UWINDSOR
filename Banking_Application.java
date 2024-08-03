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
    private ArrayList<Transaction> transactions;
    private Lock lock;

    public Account(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction("Account opened with initial balance", initialBalance));
        this.lock = new ReentrantLock();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

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

// Admin class for handling admin login and authentication
class Admin {
    private String adminId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String sinNumber;
    private String dob;
    private String position; // New field

    public Admin(String adminId, String password, String firstName, String lastName,
                 String email, String phoneNumber, String sinNumber, String dob, String position) {
        this.adminId = adminId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.dob = dob;
        this.position = position;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getPosition() {
        return position;
    }

    public boolean validate(String adminId, String password) {
        return this.adminId.equals(adminId) && this.password.equals(password);
    }

    public void deleteAccount(Bank bank, String accountId) {
        if (bank.getEmployee(accountId) != null) {
            bank.removeEmployee(accountId);
            System.out.println("Employee account with ID " + accountId + " has been deleted.");
        } else if (bank.getCustomer(accountId) != null) {
            bank.removeCustomer(accountId);
            System.out.println("Customer account with ID " + accountId + " has been deleted.");
        } else {
            System.out.println("Account with ID " + accountId + " not found.");
        }
    }
}

// Employee class for handling employee information
class Employee {
    private String employeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String sinNumber;
    private String dob;
    private String position;

    public Employee(String employeeId, String password, String firstName, String lastName,
                    String email, String phoneNumber, String sinNumber, String dob, String position) {
        this.employeeId = employeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.dob = dob;
        this.position = position;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getPosition() {
        return position;
    }

    public boolean validate(String employeeId, String password) {
        return this.employeeId.equals(employeeId) && this.password.equals(password);
    }
}

// Customer class for handling customer actions
class Customer {
    private String customerId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String sinNumber;
    private String dob;

    public Customer(String customerId, String password, String firstName, String lastName,
                    String email, String phoneNumber, String sinNumber, String dob) {
        this.customerId = customerId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.dob = dob;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public String getDob() {
        return dob;
    }

    public boolean validate(String customerId, String password) {
        return this.customerId.equals(customerId) && this.password.equals(password);
    }

    // Print account details for this specific customer
    public void printCustomerAccountDetails(Bank bank) {
        List<Account> accounts = bank.findAccountsByCustomerName(this.getFirstName() + " " + this.getLastName());

        if (accounts.isEmpty()) {
            System.out.println("No accounts found for customer: " + this.getFirstName() + " " + this.getLastName());
        } else {
            for (Account account : accounts) {
                account.printAccountDetails();
            }
        }
    }

    // Generate report for this specific customer
    public void generateCustomerReport(Bank bank, String filename) {
        List<Account> accounts = bank.findAccountsByCustomerName(this.getFirstName() + " " + this.getLastName());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for customer: " + this.getFirstName() + " " + this.getLastName());
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (Account account : accounts) {
                    writer.println("Account Number: " + account.getAccountNumber());
                    writer.println("Name: " + account.getName());
                    writer.println("Balance: " + account.getBalance());
                    writer.println("Transactions: ");
                    for (Transaction transaction : account.getTransactions()) {
                        writer.println(transaction);
                    }
                    writer.println();
                }
                System.out.println("Report generated successfully.");
            } catch (IOException e) {
                System.out.println("Error generating report: " + e.getMessage());
            }
        }
    }
}

// Bank class to manage multiple accounts and provide banking operations
class Bank {
    private HashMap<String, Account> accounts;
    private HashMap<String, Admin> admins;
    private HashMap<String, Employee> employees;
    private HashMap<String, Customer> customers;
    private int accountCounter;
    private int adminCounter;

    public Bank() {
        accounts = new HashMap<>();
        admins = new HashMap<>();
        employees = new HashMap<>();
        customers = new HashMap<>();
        accountCounter = 1;
        adminCounter = 1;

        // Predefined Admin accounts
        admins.put("admin1", new Admin("admin1", "adminpass1", "FirstAdmin1", "LastAdmin1", "admin1@example.com", "000-000-0001", "SINAdmin1", "DOBAdmin1","CTO"));
        admins.put("admin2", new Admin("admin2", "adminpass2", "FirstAdmin2", "LastAdmin2", "admin2@example.com", "000-000-0002", "SINAdmin2", "DOBAdmin2","CDO"));

        // Predefined Employee accounts with additional details
        employees.put("emp1", new Employee("emp1", "emppass1", "First name1", "Last name1", "email1@example.com", "111-111-1111", "SIN1", "DOB1", "Manager"));
        employees.put("emp2", new Employee("emp2", "emppass2", "First name2", "Last name2", "email2@example.com", "222-222-2222", "SIN2", "DOB2", "Teller"));
        employees.put("emp3", new Employee("emp3", "emppass3", "First name3", "Last name3", "email3@example.com", "333-333-3333", "SIN3", "DOB3", "Loan Officer"));
        employees.put("emp4", new Employee("emp4", "emppass4", "First name4", "Last name4", "email4@example.com", "444-444-4444", "SIN4", "DOB4", "Auditor"));
        employees.put("emp5", new Employee("emp5", "emppass5", "First name5", "Last name5", "email5@example.com", "555-555-5555", "SIN5", "DOB5", "Branch Manager"));
        employees.put("emp6", new Employee("emp6", "emppass6", "First name6", "Last name6", "email6@example.com", "666-666-6666", "SIN6", "DOB6", "Accountant"));
        employees.put("emp7", new Employee("emp7", "emppass7", "First name7", "Last name7", "email7@example.com", "777-777-7777", "SIN7", "DOB7", "Clerk"));

        // Predefined Customer accounts with additional details and 1000 balance
        customers.put("cust1", new Customer("cust1", "custpass1", "First name1", "Last name1", "email1@example.com", "888-888-8888", "SIN1", "DOB1"));
        customers.put("cust2", new Customer("cust2", "custpass2", "First name2", "Last name2", "email2@example.com", "999-999-9999", "SIN2", "DOB2"));
        customers.put("cust3", new Customer("cust3", "custpass3", "First name3", "Last name3", "email3@example.com", "101-010-1010", "SIN3", "DOB3"));
        customers.put("cust4", new Customer("cust4", "custpass4", "First name4", "Last name4", "email4@example.com", "202-020-2020", "SIN4", "DOB4"));
        customers.put("cust5", new Customer("cust5", "custpass5", "First name5", "Last name5", "email5@example.com", "303-030-3030", "SIN5", "DOB5"));

        openCheckingAccount("First name1 Last name1", 1000);
        openCheckingAccount("First name2 Last name2", 1000);
        openCheckingAccount("First name3 Last name3", 1000);
        openCheckingAccount("First name4 Last name4", 1000);
        openCheckingAccount("First name5 Last name5", 1000);
    }

    public Account openCheckingAccount(String name, double initialBalance) {
        String accountNumber = "CHK" + accountCounter++;
        Account account = new CheckingAccount(accountNumber, name, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    public void generateBankInfoReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Bank Information Report:");

            writer.println("\nAdmin Accounts:");
            for (Admin admin : admins.values()) {
                writer.println("Name: " + admin.getFirstName() + " " + admin.getLastName());
                writer.println("Email: " + admin.getEmail());
                writer.println("Phone Number: " + admin.getPhoneNumber());
                writer.println("Position: " + admin.getPosition());
                writer.println();
            }

            writer.println("\nEmployee Accounts:");
            for (Employee employee : employees.values()) {
                writer.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                writer.println("Email: " + employee.getEmail());
                writer.println("Phone Number: " + employee.getPhoneNumber());
                writer.println("Position: " + employee.getPosition());
                writer.println();
            }

            System.out.println("Bank information report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating bank info report: " + e.getMessage());
        }
    }

    public void generateAdminReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Admin Accounts:");
            for (Admin admin : admins.values()) {
                writer.println("Admin ID: " + admin.getAdminId());
                writer.println("Name: " + admin.getFirstName() + " " + admin.getLastName());
                writer.println("Email: " + admin.getEmail());
                writer.println("Phone Number: " + admin.getPhoneNumber());
                writer.println("Position: " + admin.getPosition()); // New field in the report
                writer.println();
            }
            System.out.println("Admin report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating admin report: " + e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
    }

    public Collection<Employee> getAllEmployees() {
        return employees.values();
    }

    public Account openSavingsAccount(String name, double initialBalance) {
        String accountNumber = "SAV" + accountCounter++;
        Account account = new SavingsAccount(accountNumber, name, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Admin getAdmin(String adminId) {
        return admins.get(adminId);
    }

    public Employee getEmployee(String employeeId) {
        return employees.get(employeeId);
    }

    public void removeEmployee(String employeeId) {
        employees.remove(employeeId);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    public void removeCustomer(String customerId) {
        customers.remove(customerId);
        accounts.entrySet().removeIf(entry -> entry.getValue().getName().equals(customerId));
    }

    public List<Account> findAccountsByCustomerName(String name) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                result.add(account);
            }
        }
        return result;
    }

    public List<Account> findAccountsByAccountNumber(String accountNumber) {
        List<Account> result = new ArrayList<>();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            result.add(account);
        }
        return result;
    }

    public List<Account> findAccountsByBalance(double balance) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getBalance() == balance) {
                result.add(account);
            }
        }
        return result;
    }

    public void printAllAccounts() {
        for (Account account : accounts.values()) {
            account.printAccountDetails();
            System.out.println();
        }
    }

    public void generateReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // 添加 Employee 信息
            writer.println("Employee Accounts:");
            for (Employee employee : employees.values()) {
                writer.println("Employee ID: " + employee.getEmployeeId());
                writer.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                writer.println("Email: " + employee.getEmail());
                writer.println("Phone Number: " + employee.getPhoneNumber());
                writer.println("SIN: " + employee.getSinNumber());
                writer.println("DOB: " + employee.getDob());
                writer.println("Position: " + employee.getPosition());
                // 获取 Employee 账户的余额
                List<Account> employeeAccounts = findAccountsByCustomerName(employee.getFirstName() + " " + employee.getLastName());
                for (Account account : employeeAccounts) {
                    writer.println("Account Number: " + account.getAccountNumber());
                    writer.println("Balance: " + account.getBalance());
                }
                writer.println();
            }

            // 添加 Customer 信息
            writer.println("Customer Accounts:");
            for (Customer customer : customers.values()) {
                writer.println("Customer ID: " + customer.getCustomerId());
                writer.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
                writer.println("Email: " + customer.getEmail());
                writer.println("Phone Number: " + customer.getPhoneNumber());
                writer.println("SIN: " + customer.getSinNumber());
                writer.println("DOB: " + customer.getDob());
                // 获取 Customer 账户的余额
                List<Account> customerAccounts = findAccountsByCustomerName(customer.getFirstName() + " " + customer.getLastName());
                for (Account account : customerAccounts) {
                    writer.println("Account Number: " + account.getAccountNumber());
                    writer.println("Balance: " + account.getBalance());
                }
                writer.println();
            }

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}

// Main class to run the banking application
public class Banking_Application {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean authenticated = false;
        String userType = "";
        String customerId = null; // Define customerId here

        while (true) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. Employee Login");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Admin ID: ");
                    String adminId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String adminPassword = scanner.nextLine();
                    Admin admin = bank.getAdmin(adminId);
                    if (admin != null && admin.validate(adminId, adminPassword)) {
                        authenticated = true;
                        userType = "Admin";
                        System.out.println("Admin login successful.");
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Employee ID: ");
                    String employeeId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String employeePassword = scanner.nextLine();
                    Employee employee = bank.getEmployee(employeeId);
                    if (employee != null && employee.validate(employeeId, employeePassword)) {
                        authenticated = true;
                        userType = "Employee";
                        System.out.println("Employee login successful.");
                    } else {
                        System.out.println("Invalid employee credentials.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Customer ID: ");
                    customerId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String customerPassword = scanner.nextLine();
                    Customer customer = bank.getCustomer(customerId);
                    if (customer != null && customer.validate(customerId, customerPassword)) {
                        authenticated = true;
                        userType = "Customer";
                        System.out.println("Customer login successful.");
                    } else {
                        System.out.println("Invalid customer credentials.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (authenticated) {
                if (userType.equals("Admin")) {
                    while (true) {
                        System.out.println("\nAdmin Operations:");
                        System.out.println("1. Open Account");
                        System.out.println("2. Add New Employee");
                        System.out.println("3. Deposit");
                        System.out.println("4. Withdraw");
                        System.out.println("5. Transfer");
                        System.out.println("6. Print Account Details");
                        System.out.println("7. Generate Report");
                        System.out.println("8. Delete Account");
                        System.out.println("9. show all bankers");
                        System.out.println("10. show bank info for customer");
                        System.out.println("11. Logout");
                        System.out.print("Enter your choice: ");
                        int operation = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            switch (operation) {
                                // 合并后的 Admin 操作菜单
                                case 1: // Open Account
                                    // Inside the Admin and Employee operation cases for "Open Account"
                                    System.out.println("1. Open Checking Account");
                                    System.out.println("2. Open Savings Account");
                                    System.out.print("Enter your choice: ");
                                    int accountType = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    System.out.print("Enter first name: ");
                                    String firstName = scanner.nextLine();
                                    System.out.print("Enter last name: ");
                                    String lastName = scanner.nextLine();
                                    System.out.print("Enter email: ");
                                    String email = scanner.nextLine();
                                    System.out.print("Enter phone number: ");
                                    String phoneNumber = scanner.nextLine();
                                    System.out.print("Enter SIN number: ");
                                    String sinNumber = scanner.nextLine();
                                    System.out.print("Enter DOB: ");
                                    String dob = scanner.nextLine();
                                    System.out.print("Enter initial balance: ");
                                    double initialBalance = scanner.nextDouble();
                                    scanner.nextLine(); // Consume newline

                                    String fullName = firstName + " " + lastName;

                                    if (accountType == 1) {
                                        Account checkingAccount = bank.openCheckingAccount(fullName, initialBalance);
                                        System.out.println("Checking account opened with account number: " + checkingAccount.getAccountNumber());
                                    } else if (accountType == 2) {
                                        Account savingsAccount = bank.openSavingsAccount(fullName, initialBalance);
                                        System.out.println("Savings account opened with account number: " + savingsAccount.getAccountNumber());
                                    } else {
                                        System.out.println("Invalid choice.");
                                    }

                                    break;

                                case 2: // Add Employee
                                    System.out.print("Enter new Employee ID: ");
                                    String newEmpId = scanner.nextLine();

                                    // 检查ID是否已存在
                                    if (bank.getEmployee(newEmpId) != null) {
                                        System.out.println("Employee ID already exists. Please choose a different ID.");
                                        break;
                                    }

                                    System.out.print("Enter password: ");
                                    String newPassword = scanner.nextLine();
                                    System.out.print("Enter first name: ");
                                    String newFirstName = scanner.nextLine();
                                    System.out.print("Enter last name: ");
                                    String newLastName = scanner.nextLine();
                                    System.out.print("Enter email: ");
                                    String newEmail = scanner.nextLine();
                                    System.out.print("Enter phone number: ");
                                    String newPhoneNumber = scanner.nextLine();
                                    System.out.print("Enter SIN number: ");
                                    String newSinNumber = scanner.nextLine();
                                    System.out.print("Enter DOB: ");
                                    String newDob = scanner.nextLine();
                                    System.out.print("Enter position: ");
                                    String newPosition = scanner.nextLine();

                                    // 检查Position是否已存在
                                    boolean positionExists = false;
                                    for (Employee emp : bank.getAllEmployees()) {
                                        if (emp.getPosition().equalsIgnoreCase(newPosition)) {
                                            positionExists = true;
                                            break;
                                        }
                                    }

                                    if (positionExists) {
                                        System.out.println("Position already exists. Please choose a different position.");
                                    } else {
                                        Employee newEmployee = new Employee(newEmpId, newPassword, newFirstName, newLastName, newEmail, newPhoneNumber, newSinNumber, newDob, newPosition);
                                        bank.addEmployee(newEmployee);
                                        System.out.println("Employee added successfully.");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Enter account number: ");
                                    String accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to deposit: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.deposit(amount);
                                        System.out.println("Deposit successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 4:
                                    System.out.print("Enter account number: ");
                                    accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to withdraw: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.withdraw(amount);
                                        System.out.println("Withdrawal successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 5:
                                    System.out.print("Enter your account number: ");
                                    String fromAccountNumber = scanner.nextLine();
                                    System.out.print("Enter recipient account number: ");
                                    String toAccountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to transfer: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account fromAccount = bank.getAccount(fromAccountNumber);
                                    Account toAccount = bank.getAccount(toAccountNumber);
                                    if (fromAccount != null && toAccount != null) {
                                        fromAccount.transfer(toAccount, amount);
                                        System.out.println("Transfer successful. New balance: " + fromAccount.getBalance());
                                    } else {
                                        System.out.println("One or both accounts not found.");
                                    }
                                    break;
                                case 6:
                                    System.out.print("Enter account number: ");
                                    accountNumber = scanner.nextLine();
                                    account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.printAccountDetails();
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 7:
                                    System.out.print("Enter report filename: ");
                                    String filename = scanner.nextLine();
                                    bank.generateReport(filename);
                                    break;
                                case 8: // Delete Account
                                    System.out.println("1. Delete Employee Account");
                                    System.out.println("2. Delete Customer Account");
                                    System.out.print("Enter your choice: ");
                                    int deleteChoice = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    if (deleteChoice == 1) {
                                        System.out.print("Enter Employee ID to delete: ");
                                        String empIdToDelete = scanner.nextLine();
                                        bank.removeEmployee(empIdToDelete); // 调用删除 Employee 方法
                                        System.out.println("Employee account with ID " + empIdToDelete + " has been deleted.");
                                    } else if (deleteChoice == 2) {
                                        System.out.print("Enter Customer ID to delete: ");
                                        String custIdToDelete = scanner.nextLine();
                                        bank.removeCustomer(custIdToDelete); // 调用删除 Customer 方法
                                        System.out.println("Customer account with ID " + custIdToDelete + " has been deleted.");
                                    } else {
                                        System.out.println("Invalid choice.");
                                    }
                                    break;

                                case 9: // Show All Bankers
                                    System.out.print("Enter report filename: ");
                                    String filename1 = scanner.nextLine(); // Ensure no duplicate definitions
                                    bank.generateAdminReport(filename1); // Call this on the bank instance
                                    break;
                                case 10: // Show Bank Info
                                    System.out.print("Enter report filename: ");
                                    String filename2 = scanner.nextLine();
                                    bank.generateBankInfoReport(filename2);
                                    break;
                                case 11:
                                    authenticated = false;
                                    userType = "";
                                    System.out.println("Logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } catch (InvalidAmountException | InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        if (!authenticated) break; // Return to login menu after logout
                    }
                } else if (userType.equals("Employee")) {
                    while (true) {
                        System.out.println("\nEmployee Operations:");
                        System.out.println("1. Open Account");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Transfer");
                        System.out.println("5. Delete Customer Account");
                        System.out.println("6. Generate specific customer report");
                        System.out.println("7. Show Bank Info to customer");
                        System.out.println("8. Logout");
                        System.out.print("Enter your choice: ");
                        int operation = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            switch (operation) {
                                case 1:
                                    System.out.println("1. Open Checking Account");
                                    System.out.println("2. Open Savings Account");
                                    System.out.print("Enter your choice: ");
                                    int accountType = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    System.out.print("Enter first name: ");
                                    String firstName = scanner.nextLine();
                                    System.out.print("Enter last name: ");
                                    String lastName = scanner.nextLine();
                                    System.out.print("Enter email: ");
                                    String email = scanner.nextLine();
                                    System.out.print("Enter phone number: ");
                                    String phoneNumber = scanner.nextLine();
                                    System.out.print("Enter SIN number: ");
                                    String sinNumber = scanner.nextLine();
                                    System.out.print("Enter DOB: ");
                                    String dob = scanner.nextLine();
                                    System.out.print("Enter initial balance: ");
                                    double initialBalance = scanner.nextDouble();
                                    scanner.nextLine(); // Consume newline

                                    String fullName = firstName + " " + lastName;

                                    if (accountType == 1) {
                                        Account checkingAccount = bank.openCheckingAccount(fullName, initialBalance);
                                        System.out.println("Checking account opened with account number: " + checkingAccount.getAccountNumber());
                                    } else if (accountType == 2) {
                                        Account savingsAccount = bank.openSavingsAccount(fullName, initialBalance);
                                        System.out.println("Savings account opened with account number: " + savingsAccount.getAccountNumber());
                                    } else {
                                        System.out.println("Invalid choice.");
                                    }
                                    break;
                                case 2:
                                    System.out.print("Enter account number: ");
                                    String accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to deposit: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.deposit(amount);
                                        System.out.println("Deposit successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Enter account number: ");
                                    accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to withdraw: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.withdraw(amount);
                                        System.out.println("Withdrawal successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 4:
                                    System.out.print("Enter your account number: ");
                                    String fromAccountNumber = scanner.nextLine();
                                    System.out.print("Enter recipient account number: ");
                                    String toAccountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to transfer: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account fromAccount = bank.getAccount(fromAccountNumber);
                                    Account toAccount = bank.getAccount(toAccountNumber);
                                    if (fromAccount != null && toAccount != null) {
                                        fromAccount.transfer(toAccount, amount);
                                        System.out.println("Transfer successful. New balance: " + fromAccount.getBalance());
                                    } else {
                                        System.out.println("One or both accounts not found.");
                                    }
                                    break;
                                case 5: // Delete Customer Account
                                    System.out.print("Enter Customer ID to delete: ");
                                    String custIdToDelete = scanner.nextLine();
                                    bank.removeCustomer(custIdToDelete); // 调用删除 Customer 方法
                                    System.out.println("Customer account with ID " + custIdToDelete + " has been deleted.");
                                    break;

                                case 6: // Generate Specific Customer Report
                                    System.out.print("Enter Customer ID: ");
                                    customerId = scanner.nextLine();
                                    Customer customer = bank.getCustomer(customerId);
                                    if (customer != null) {
                                        System.out.print("Enter report filename: ");
                                        String filename = scanner.nextLine();
                                        customer.generateCustomerReport(bank, filename);
                                    } else {
                                        System.out.println("Customer with ID " + customerId + " not found.");
                                    }
                                    break;


                                case 7: // Show Bank Info
                                    System.out.print("Enter report filename: ");
                                    String filename1 = scanner.nextLine();
                                    bank.generateBankInfoReport(filename1);
                                    System.out.println("Contact us for help");
                                    break;
                                case 8:
                                    authenticated = false;
                                    userType = "";
                                    System.out.println("Logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } catch (InvalidAmountException | InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        if (!authenticated) break; // Return to login menu after logout
                    }
                } else if (userType.equals("Customer")) {
                    Customer customer = bank.getCustomer(customerId); // Ensure customer is retrieved correctly
                    while (true) {
                        System.out.println("\nCustomer Banking Operations:");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Transfer");
                        System.out.println("4. Print Account Details");
                        System.out.println("5. Generate Report");
                        System.out.println("6. Logout");
                        System.out.print("Enter your choice: ");
                        int operation = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            switch (operation) {
                                case 1:
                                    System.out.print("Enter account number: ");
                                    String accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to deposit: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.deposit(amount);
                                        System.out.println("Deposit successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 2:
                                    System.out.print("Enter account number: ");
                                    accountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to withdraw: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    account = bank.getAccount(accountNumber);
                                    if (account != null) {
                                        account.withdraw(amount);
                                        System.out.println("Withdrawal successful. New balance: " + account.getBalance());
                                    } else {
                                        System.out.println("Account not found.");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Enter your account number: ");
                                    String fromAccountNumber = scanner.nextLine();
                                    System.out.print("Enter recipient account number: ");
                                    String toAccountNumber = scanner.nextLine();
                                    System.out.print("Enter amount to transfer: ");
                                    amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    Account fromAccount = bank.getAccount(fromAccountNumber);
                                    Account toAccount = bank.getAccount(toAccountNumber);
                                    if (fromAccount != null && toAccount != null) {
                                        fromAccount.transfer(toAccount, amount);
                                        System.out.println("Transfer successful. New balance: " + fromAccount.getBalance());
                                    } else {
                                        System.out.println("One or both accounts not found.");
                                    }
                                    break;
                                case 4:
                                    customer.printCustomerAccountDetails(bank);
                                    break;
                                case 5:
                                    System.out.print("Enter report filename: ");
                                    String filename = scanner.nextLine();
                                    customer.generateCustomerReport(bank, filename);
                                    break;
                                case 6:
                                    authenticated = false;
                                    userType = "";
                                    System.out.println("Logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } catch (InvalidAmountException | InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        if (!authenticated) break; // Return to login menu after logout
                    }
                }
            }
        }
    }
}
