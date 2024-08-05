package Bank;// Main.java
// Main class to run the banking application

import java.util.InputMismatchException;
import java.util.Scanner;

// Main class to run the banking application
public class Main {
    public static void main(String[] args) {
        // ANSI escape codes for bold text
        String bold = "\033[1m";
        String reset = "\033[0m";

        // Simple, large-looking text representation
        String welcomeScreen =
                "***********************************************\n" +
                        "*                                             *\n" +
                        "*     W E L C O M E   T O   B A N K I N G     *\n" +
                        "*              M A N A G E M E N T            *\n" +
                        "*                S Y S T E M                  *\n" +
                        "*                                             *\n" +
                        "***********************************************\n";

        // Printing the text in bold
        System.out.println(bold + welcomeScreen + reset);
        // Print the welcome message in bold

        System.out.println();
        System.out.println("Follow the instructions below:");

        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator(); // Validator instance
        boolean authenticated = false;
        String userType = "";
        String customerId = null;

        while (true) {
            try{
                System.out.println("\n1. Login");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int initialChoice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                if (initialChoice == 1) {
                    break;  // Proceed to the login screen
                } else if (initialChoice == 2) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input, please enter a number");
                scanner.nextLine();
            }
        }

        while (true) {
            try{
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
            }catch (InputMismatchException e){
                System.out.println("Invalid input, please enter a number");
                scanner.nextLine();
            }
            try{
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
                            System.out.println("9. Generate All Customer Report");
                            System.out.println("10. Show Bank Info");
                            System.out.println("11. Search Account");
                            System.out.println("12. Update Customer Info");
                            System.out.println("13. Logout");
                            System.out.print("Enter your choice: ");
                            int operation = scanner.nextInt();
                            scanner.nextLine();

                            try {
                                switch (operation) {
                                    case 1: // Open Account
                                        System.out.println("1. Open Checking Account");
                                        System.out.println("2. Open Savings Account");
                                        System.out.print("Enter your choice: ");
                                        int accountType = scanner.nextInt();
                                        scanner.nextLine();

                                        if (accountType != 1 && accountType != 2) {
                                            System.out.println("Invalid choice.");
                                            break;
                                        }

                                        System.out.print("Enter first name: ");
                                        String firstName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter last name: ");
                                        String lastName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter password: ");
                                        String accountPassword = validator.validatePassword(scanner.nextLine());

                                        System.out.print("Enter email: ");
                                        String email = validator.validateEmail(scanner.nextLine());

                                        System.out.print("Enter phone number: ");
                                        String phoneNumber = validator.validatePhoneNumber(scanner.nextLine());

                                        System.out.print("Enter SIN number: ");
                                        String sinNumber = validator.validateSIN(scanner.nextLine());

                                        System.out.print("Enter DOB (MM/DD/YYYY): ");
                                        String dob = validator.validateDOB(scanner.nextLine());

                                        System.out.print("Enter address (City,Province,Street,PostCode): ");
                                        String address = validator.validateAddress(scanner.nextLine());

                                        System.out.print("Enter initial balance: ");
                                        double initialBalance = validator.validateAmount(scanner.nextLine());

                                        String customerIdNew = bank.addNewCustomer(accountPassword, firstName, lastName, email, phoneNumber, sinNumber, dob, address);
                                        Customer newCustomer = bank.getCustomer(customerIdNew);

                                        Account<?> newAccount;
                                        if (accountType == 1) {
                                            newAccount = bank.openCheckingAccount(newCustomer, initialBalance);
                                            System.out.println("Checking account opened with account number: " + newAccount.getAccountNumber());
                                        } else {
                                            newAccount = bank.openSavingsAccount(newCustomer, initialBalance);
                                            System.out.println("Savings account opened with account number: " + newAccount.getAccountNumber());
                                        }
                                        System.out.println("Customer ID: " + customerIdNew);

                                        // Store the password associated with the new account
                                        bank.addAccountPassword(newAccount.getAccountNumber(), accountPassword);
                                        break;

                                    case 2: // Add Employee
                                        System.out.print("Enter new Employee ID: ");
                                        String newEmpId = scanner.nextLine();
                                        if (bank.getEmployee(newEmpId) != null) {
                                            System.out.println("Employee ID already exists. Please choose a different ID.");
                                            break;
                                        }

                                        System.out.print("Enter password: ");
                                        String newPassword = validator.validatePassword(scanner.nextLine());

                                        System.out.print("Enter first name: ");
                                        String newFirstName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter last name: ");
                                        String newLastName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter email: ");
                                        String newEmail = validator.validateEmail(scanner.nextLine());

                                        System.out.print("Enter phone number: ");
                                        String newPhoneNumber = validator.validatePhoneNumber(scanner.nextLine());

                                        System.out.print("Enter SIN number: ");
                                        String newSinNumber = validator.validateSIN(scanner.nextLine());

                                        System.out.print("Enter DOB(MM/DD/YYYY): ");
                                        String newDob = validator.validateDOB(scanner.nextLine());

                                        System.out.print("Enter position: ");
                                        String newPosition = scanner.nextLine();

                                        System.out.print("Enter address (City,Province,Street,PostCode): ");
                                        String newAddress = validator.validateAddress(scanner.nextLine());

                                        Employee newEmployee = new Employee(newEmpId, newPassword, newFirstName, newLastName, newEmail, newPhoneNumber, newSinNumber, newDob, newPosition, newAddress);
                                        bank.addEmployee(newEmployee);
                                        System.out.println("Employee added successfully.");
                                        break;
                                    case 3:
                                        System.out.print("Enter account number: ");
                                        String accountNumber = scanner.nextLine();
                                        System.out.print("Enter amount to deposit: ");
                                        double amount = validator.validateAmount(scanner.nextLine());
                                        Account<?> account = bank.getAccount(accountNumber);
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
                                        amount = validator.validateAmount(scanner.nextLine());
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
                                        amount = validator.validateAmount(scanner.nextLine());
                                        Account<?> fromAccount = bank.getAccount(fromAccountNumber);
                                        Account<?> toAccount = bank.getAccount(toAccountNumber);
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
                                        } else if (deleteChoice == 2) {
                                            System.out.print("Enter Customer ID to delete: ");
                                            String custIdToDelete = scanner.nextLine();
                                            bank.removeCustomer(custIdToDelete); // 调用删除 Customer 方法
                                        } else {
                                            System.out.println("Invalid choice.");
                                        }
                                        break;

                                    case 9: // Generate All Customer Report
                                        System.out.print("Enter report filename: ");
                                        String filename1 = scanner.nextLine();
                                        bank.generateAllCustomerReport(filename1);
                                        break;
                                    case 10: // Show Bank Info
                                        System.out.print("Enter report filename: ");
                                        String filename2 = scanner.nextLine();
                                        bank.generateBankInfoReport(filename2);
                                        break;
                                    case 11: // Search Account
                                        System.out.println("Select criteria to search by:");
                                        System.out.println("1. Name");
                                        System.out.println("2. DOB");
                                        System.out.println("3. SIN");
                                        System.out.print("Enter your choice: ");
                                        int searchChoice = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline

                                        String criteria = "";
                                        String value = "";

                                        switch (searchChoice) {
                                            case 1:
                                                criteria = "name";
                                                System.out.print("Enter name: ");
                                                value = validator.validateName(scanner.nextLine());
                                                break;
                                            case 2:
                                                criteria = "dob";
                                                System.out.print("Enter DOB (MM/DD/YYYY): ");
                                                value = validator.validateDOB(scanner.nextLine());
                                                break;
                                            case 3:
                                                criteria = "sin";
                                                System.out.print("Enter SIN number: ");
                                                value = validator.validateSIN(scanner.nextLine());
                                                break;
                                            default:
                                                System.out.println("Invalid choice.");
                                                break;
                                        }

                                        if (!value.isEmpty()) {
                                            bank.searchAccount(criteria, value);
                                        }
                                        break;
                                    case 12: // Update Customer Info
                                        System.out.print("Enter Customer ID to update: ");
                                        String customerIdUpdate = scanner.nextLine();
                                        System.out.print("Enter first name: ");
                                        String firstNameUpdate = validator.validateName(scanner.nextLine());
                                        System.out.print("Enter last name: ");
                                        String lastNameUpdate = validator.validateName(scanner.nextLine());
                                        System.out.print("Enter DOB: ");
                                        String dobUpdate = validator.validateDOB(scanner.nextLine());
                                        System.out.println("Select field to update:");
                                        System.out.println("1. Phone Number");
                                        System.out.println("2. Address");
                                        System.out.println("3. Password");
                                        System.out.print("Enter your choice: ");
                                        int fieldChoice = scanner.nextInt();
                                        scanner.nextLine();

                                        String updateField = "";
                                        String newValue = "";

                                        switch (fieldChoice) {
                                            case 1:
                                                updateField = "phone number";
                                                System.out.print("Enter new phone number: ");
                                                newValue = validator.validatePhoneNumber(scanner.nextLine());
                                                break;
                                            case 2:
                                                updateField = "address";
                                                System.out.print("Enter new address (City,Province,Street,PostCode): ");
                                                newValue = validator.validateAddress(scanner.nextLine());
                                                break;
                                            case 3:
                                                updateField = "password";
                                                System.out.print("Enter new password: ");
                                                newValue = validator.validatePassword(scanner.nextLine());
                                                break;
                                            default:
                                                System.out.println("Invalid choice.");
                                                break;
                                        }

                                        if (!newValue.isEmpty()) {
                                            bank.updateCustomerInfo(customerIdUpdate, firstNameUpdate, lastNameUpdate, dobUpdate, updateField, newValue);
                                        }
                                        break;
                                    case 13:
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
                            System.out.println("8. Search Account");
                            System.out.println("9. Update Customer Info");
                            System.out.println("10. Logout");
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
                                        scanner.nextLine();

                                        if (accountType != 1 && accountType != 2) {
                                            System.out.println("Invalid choice.");
                                            break;
                                        }

                                        System.out.print("Enter first name: ");
                                        String firstName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter last name: ");
                                        String lastName = validator.validateName(scanner.nextLine());

                                        System.out.print("Enter password: ");
                                        String accountPassword = validator.validatePassword(scanner.nextLine());

                                        System.out.print("Enter email: ");
                                        String email = validator.validateEmail(scanner.nextLine());

                                        System.out.print("Enter phone number: ");
                                        String phoneNumber = validator.validatePhoneNumber(scanner.nextLine());

                                        System.out.print("Enter SIN number: ");
                                        String sinNumber = validator.validateSIN(scanner.nextLine());

                                        System.out.print("Enter DOB: ");
                                        String dob = validator.validateDOB(scanner.nextLine());

                                        System.out.print("Enter address (City,Province,Street,PostCode): ");
                                        String address = validator.validateAddress(scanner.nextLine());

                                        System.out.print("Enter initial balance: ");
                                        double initialBalance = validator.validateAmount(scanner.nextLine());

                                        String customerIdNew = bank.addNewCustomer(accountPassword, firstName, lastName, email, phoneNumber, sinNumber, dob, address);
                                        Customer newCustomer = bank.getCustomer(customerIdNew);

                                        Account<?> newAccount;
                                        if (accountType == 1) {
                                            newAccount = bank.openCheckingAccount(newCustomer, initialBalance);
                                            System.out.println("Checking account opened with account number: " + newAccount.getAccountNumber());
                                        } else {
                                            newAccount = bank.openSavingsAccount(newCustomer, initialBalance);
                                            System.out.println("Savings account opened with account number: " + newAccount.getAccountNumber());
                                        }
                                        System.out.println("Customer ID: " + customerIdNew);

                                        // Store the password associated with the new account
                                        bank.addAccountPassword(newAccount.getAccountNumber(), accountPassword);
                                        break;

                                    case 2:
                                        System.out.print("Enter account number: ");
                                        String accountNumber = scanner.nextLine();
                                        System.out.print("Enter amount to deposit: ");
                                        double amount = validator.validateAmount(scanner.nextLine());
                                        Account<?> account = bank.getAccount(accountNumber);
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
                                        amount = validator.validateAmount(scanner.nextLine());
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
                                        amount = validator.validateAmount(scanner.nextLine());
                                        Account<?> fromAccount = bank.getAccount(fromAccountNumber);
                                        Account<?> toAccount = bank.getAccount(toAccountNumber);
                                        if (fromAccount != null && toAccount != null) {
                                            fromAccount.transfer(toAccount, amount);
                                            System.out.println("Transfer successful. New balance: " + fromAccount.getBalance());
                                        } else {
                                            System.out.println("One or both accounts not found.");
                                        }
                                        break;
                                    case 5:
                                        System.out.print("Enter Customer ID to delete: ");
                                        String custIdToDelete = scanner.nextLine();
                                        bank.removeCustomer(custIdToDelete);
                                        break;
                                    case 6:
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
                                    case 7:
                                        System.out.print("Enter report filename: ");
                                        String filename1 = scanner.nextLine();
                                        bank.generateBankInfoReport(filename1);
                                        break;
                                    case 8: // Search Account
                                        System.out.println("Select criteria to search by:");
                                        System.out.println("1. Name");
                                        System.out.println("2. DOB");
                                        System.out.println("3. SIN");
                                        System.out.print("Enter your choice: ");
                                        int searchChoice = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline

                                        String criteria = "";
                                        String value = "";

                                        switch (searchChoice) {
                                            case 1:
                                                criteria = "name";
                                                System.out.print("Enter name: ");
                                                value = validator.validateName(scanner.nextLine());
                                                break;
                                            case 2:
                                                criteria = "dob";
                                                System.out.print("Enter DOB (MM/DD/YYYY): ");
                                                value = validator.validateDOB(scanner.nextLine());
                                                break;
                                            case 3:
                                                criteria = "sin";
                                                System.out.print("Enter SIN number: ");
                                                value = validator.validateSIN(scanner.nextLine());
                                                break;
                                            default:
                                                System.out.println("Invalid choice.");
                                                break;
                                        }

                                        if (!value.isEmpty()) {
                                            bank.searchAccount(criteria, value);
                                        }
                                        break;
                                    case 9: // Update Customer Info
                                        System.out.print("Enter Customer ID to update: ");
                                        String customerIdUpdate = scanner.nextLine();
                                        System.out.print("Enter first name: ");
                                        String firstNameUpdate = validator.validateName(scanner.nextLine());
                                        System.out.print("Enter last name: ");
                                        String lastNameUpdate = validator.validateName(scanner.nextLine());
                                        System.out.print("Enter DOB: ");
                                        String dobUpdate = validator.validateDOB(scanner.nextLine());
                                        System.out.println("Select field to update:");
                                        System.out.println("1. Phone Number");
                                        System.out.println("2. Address");
                                        System.out.println("3. Password");
                                        System.out.print("Enter your choice: ");
                                        int fieldChoice = scanner.nextInt();
                                        scanner.nextLine();

                                        String updateField = "";
                                        String newValue = "";

                                        switch (fieldChoice) {
                                            case 1:
                                                updateField = "phone number";
                                                System.out.print("Enter new phone number: ");
                                                newValue = validator.validatePhoneNumber(scanner.nextLine());
                                                break;
                                            case 2:
                                                updateField = "address";
                                                System.out.print("Enter new address (City,Province,Street,PostCode): ");
                                                newValue = validator.validateAddress(scanner.nextLine());
                                                break;
                                            case 3:
                                                updateField = "password";
                                                System.out.print("Enter new password: ");
                                                newValue = validator.validatePassword(scanner.nextLine());
                                                break;
                                            default:
                                                System.out.println("Invalid choice.");
                                                break;
                                        }

                                        if (!newValue.isEmpty()) {
                                            bank.updateCustomerInfo(customerIdUpdate, firstNameUpdate, lastNameUpdate, dobUpdate, updateField, newValue);
                                        }
                                        break;
                                    case 10:
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

                            if (!authenticated) break;
                        }
                    } else if (userType.equals("Customer")) {
                        Customer customer = bank.getCustomer(customerId);
                        while (true) {
                            System.out.println("\nCustomer Banking Operations:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer");
                            System.out.println("4. Print Account Details");
                            System.out.println("5. Generate Report");
                            System.out.println("6. Contact us");
                            System.out.println("7. Logout");
                            System.out.print("Enter your choice: ");
                            int operation = scanner.nextInt();
                            scanner.nextLine();

                            try {
                                switch (operation) {
                                    case 1:
                                        Account<?> customerAccountDeposit = bank.findAccountsByCustomer(customer).get(0);
                                        System.out.print("Enter amount to deposit: ");
                                        double amount = validator.validateAmount(scanner.nextLine());
                                        if (customerAccountDeposit != null) {
                                            customerAccountDeposit.deposit(amount);
                                            System.out.println("Deposit successful. New balance: " + customerAccountDeposit.getBalance());
                                        } else {
                                            System.out.println("Account not found.");
                                        }
                                        break;
                                    case 2:
                                        Account<?> customerAccountWithdraw = bank.findAccountsByCustomer(customer).get(0);
                                        System.out.print("Enter amount to withdraw: ");
                                        amount = validator.validateAmount(scanner.nextLine());
                                        if (customerAccountWithdraw != null) {
                                            customerAccountWithdraw.withdraw(amount);
                                            System.out.println("Withdrawal successful. New balance: " + customerAccountWithdraw.getBalance());
                                        } else {
                                            System.out.println("Account not found.");
                                        }
                                        break;
                                    case 3:
                                        Account<?> customerAccountTransfer = bank.findAccountsByCustomer(customer).get(0);
                                        System.out.print("Enter recipient account number: ");
                                        String toAccountNumber = scanner.nextLine();
                                        System.out.print("Enter amount to transfer: ");
                                        amount = validator.validateAmount(scanner.nextLine());
                                        Account<?> toAccount = bank.getAccount(toAccountNumber);
                                        if (customerAccountTransfer != null && toAccount != null) {
                                            customerAccountTransfer.transfer(toAccount, amount);
                                            System.out.println("Transfer successful. New balance: " + customerAccountTransfer.getBalance());
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
                                        System.out.print("Enter report filename: ");
                                        String filename1 = scanner.nextLine();
                                        bank.generateBankInfoReport(filename1);
                                        break;
                                    case 7:
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

                            if (!authenticated) break;
                        }
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input, please enter a number");
                scanner.nextLine();
            }
        }
    }
}
