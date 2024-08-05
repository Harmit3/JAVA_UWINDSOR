package Bank;

// Bank.java
// Class to manage multiple accounts and provide banking operations
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// Bank class to manage multiple accounts and provide banking operations
public class Bank {
    private HashMap<String, Account<?>> accounts;
    private HashMap<String, Admin> admins;
    private HashMap<String, Employee> employees;
    private HashMap<String, Customer> customers;
    private HashMap<String, String> accountPasswords;
    private int accountCounter;
    private int customerCounter;
    private int adminCounter;

    // Constructor to initialize the bank with predefined data
    public Bank() {
        accounts = new HashMap<>();
        admins = new HashMap<>();
        employees = new HashMap<>();
        customers = new HashMap<>();
        accountPasswords = new HashMap<>();
        accountCounter = 1;
        customerCounter = 1;
        adminCounter = 1;

        // Predefined Admin accounts
        admins.put("admin1", new Admin("admin1", "adminpass1", "FirstAdmin1", "LastAdmin1", "admin1@example.com", "000-000-0001", "SINAdmin1", "DOBAdmin1","CTO", "City1,Province1,Street1,12345"));
        admins.put("admin2", new Admin("admin2", "adminpass2", "FirstAdmin2", "LastAdmin2", "admin2@example.com", "000-000-0002", "SINAdmin2", "DOBAdmin2","CDO", "City2,Province2,Street2,54321"));

        // Predefined Employee accounts with additional details
        employees.put("emp1", new Employee("emp1", "emppass1", "First name1", "Last name1", "email1@example.com", "111-111-1111", "SIN1", "DOB1", "Manager", "City1,Province1,Street1,12345"));
        employees.put("emp2", new Employee("emp2", "emppass2", "First name2", "Last name2", "email2@example.com", "222-222-2222", "SIN2", "DOB2", "Teller", "City2,Province2,Street2,54321"));
        employees.put("emp3", new Employee("emp3", "emppass3", "First name3", "Last name3", "email3@example.com", "333-333-3333", "SIN3", "DOB3", "Loan Officer", "City3,Province3,Street3,67890"));
        employees.put("emp4", new Employee("emp4", "emppass4", "First name4", "Last name4", "email4@example.com", "444-444-4444", "SIN4", "DOB4", "Auditor", "City4,Province4,Street4,09876"));
        employees.put("emp5", new Employee("emp5", "emppass5", "First name5", "Last name5", "email5@example.com", "555-555-5555", "SIN5", "DOB5", "Branch Manager", "City5,Province5,Street5,13579"));
        employees.put("emp6", new Employee("emp6", "emppass6", "First name6", "Last name6", "email6@example.com", "666-666-6666", "SIN6", "DOB6", "Accountant", "City6,Province6,Street6,97531"));
        employees.put("emp7", new Employee("emp7", "emppass7", "First name7", "Last name7", "email7@example.com", "777-777-7777", "SIN7", "DOB7", "Clerk", "City7,Province7,Street7,24680"));

        // Predefined Customer accounts with additional details and 1000 balance
        customers.put("cust1", new Customer("cust1", "custpass1", "First name1", "Last name1", "email1@example.com", "888-888-8888", "SIN1", "DOB1", "City1,Province1,Street1,12345"));
        customers.put("cust2", new Customer("cust2", "custpass2", "First name2", "Last name2", "email2@example.com", "999-999-9999", "SIN2", "DOB2", "City2,Province2,Street2,54321"));
        customers.put("cust3", new Customer("cust3", "custpass3", "First name3", "Last name3", "email3@example.com", "101-010-1010", "SIN3", "DOB3", "City3,Province3,Street3,67890"));
        customers.put("cust4", new Customer("cust4", "custpass4", "First name4", "Last name4", "email4@example.com", "202-020-2020", "SIN4", "DOB4", "City4,Province4,Street4,09876"));
        customers.put("cust5", new Customer("cust5", "custpass5", "First name5", "Last name5", "email5@example.com", "303-030-3030", "SIN5", "DOB5", "City5,Province5,Street5,13579"));

        openCheckingAccount(customers.get("cust1"), 1000);
        openCheckingAccount(customers.get("cust2"), 1000);
        openCheckingAccount(customers.get("cust3"), 1000);
        openCheckingAccount(customers.get("cust4"), 1000);
        openCheckingAccount(customers.get("cust5"), 1000);
    }

    // Method to open a checking account for a customer
    public Account<?> openCheckingAccount(Customer customer, double initialBalance) {
        String accountNumber = "CHK" + accountCounter++;
        Account<?> account = new CheckingAccount(accountNumber, customer, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    // Method to open a savings account for a customer
    public Account<?> openSavingsAccount(Customer customer, double initialBalance) {
        String accountNumber = "SAV" + accountCounter++;
        Account<?> account = new SavingsAccount(accountNumber, customer, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    // Method to add a new customer and return the customer ID
    public String addNewCustomer(String password, String firstName, String lastName, String email,
                                 String phoneNumber, String sinNumber, String dob, String address) {
        String customerId = "cust" + customerCounter++;
        Customer customer = new Customer(customerId, password, firstName, lastName, email, phoneNumber, sinNumber, dob, address);
        customers.put(customerId, customer);
        return customerId;
    }

    // Adds an account password to the accountPasswords map
    public void addAccountPassword(String accountNumber, String password) {
        accountPasswords.put(accountNumber, password);
    }

    // Retrieves the account password from the accountPasswords map
    public String getAccountPassword(String accountNumber) {
        return accountPasswords.get(accountNumber);
    }

    // Generates a report containing information about admins and employees
    public void generateBankInfoReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Bank Information Report:");

            writer.println("\nAdmin Accounts:");
            for (Admin admin : admins.values()) {
                writer.println("Name: " + admin.getFirstName() + " " + admin.getLastName());
                writer.println("Email: " + admin.getEmail());
                writer.println("Phone Number: " + admin.getPhoneNumber());
                writer.println("Position: " + admin.getPosition());
                writer.println("Address: " + admin.getAddress());
                writer.println();
            }

            writer.println("\nEmployee Accounts:");
            for (Employee employee : employees.values()) {
                writer.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                writer.println("Email: " + employee.getEmail());
                writer.println("Phone Number: " + employee.getPhoneNumber());
                writer.println("Position: " + employee.getPosition());
                writer.println("Address: " + employee.getAddress());
                writer.println();
            }

            System.out.println("Bank information report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating bank info report: " + e.getMessage());
        }
    }

    // Generates a report containing information about all customers
    public void generateAllCustomerReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Customer Accounts:");
            for (Customer customer : customers.values()) {
                writer.println("Customer ID: " + customer.getCustomerId());
                writer.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
                writer.println("Email: " + customer.getEmail());
                writer.println("Phone Number: " + customer.getPhoneNumber());
                writer.println("SIN: " + customer.getSinNumber());
                writer.println("DOB: " + customer.getDob());
                writer.println("Address: " + customer.getAddress());
                List<Account<?>> customerAccounts = findAccountsByCustomer(customer);
                for (Account<?> account : customerAccounts) {
                    writer.println("Account Number: " + account.getAccountNumber());
                    writer.println("Balance: " + account.getBalance());
                }
                writer.println();
            }

            System.out.println("Customer information report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating customer report: " + e.getMessage());
        }
    }

    // Generates a report containing information about both employees and customers
    public void generateReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Employee Accounts:");
            for (Employee employee : employees.values()) {
                writer.println("Employee ID: " + employee.getEmployeeId());
                writer.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                writer.println("Email: " + employee.getEmail());
                writer.println("Phone Number: " + employee.getPhoneNumber());
                writer.println("SIN: " + employee.getSinNumber());
                writer.println("DOB: " + employee.getDob());
                writer.println("Position: " + employee.getPosition());
                writer.println("Address: " + employee.getAddress());
                writer.println();
            }

            writer.println("Customer Accounts:");
            for (Customer customer : customers.values()) {
                writer.println("Customer ID: " + customer.getCustomerId());
                writer.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
                writer.println("Email: " + customer.getEmail());
                writer.println("Phone Number: " + customer.getPhoneNumber());
                writer.println("SIN: " + customer.getSinNumber());
                writer.println("DOB: " + customer.getDob());
                writer.println("Address: " + customer.getAddress());
                List<Account<?>> customerAccounts = findAccountsByCustomer(customer);
                for (Account<?> account : customerAccounts) {
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

    // Adds an employee to the employees map
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
    }

    // Returns a collection of all employees
    public Collection<Employee> getAllEmployees() {
        return employees.values();
    }

    // Retrieves an account by account number
    public Account<?> getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Retrieves an admin by admin ID
    public Admin getAdmin(String adminId) {
        return admins.get(adminId);
    }

    // Retrieves an employee by employee ID
    public Employee getEmployee(String employeeId) {
        return employees.get(employeeId);
    }

    // Removes an employee by employee ID
    public void removeEmployee(String employeeId) {
        while (true) {
            try {
                if (employees.containsKey(employeeId)) {
                    employees.remove(employeeId);
                    System.out.println("Employee with ID " + employeeId + " has been removed.");
                    break;
                } else {
                    throw new InputValidationException("Employee ID " + employeeId + " not found. Please enter a valid ID.");
                }
            } catch (InputValidationException e) {
                System.out.println(e.getMessage());
                Scanner scanner = new Scanner(System.in);
                System.out.print("Re-enter Employee ID: ");
                employeeId = scanner.nextLine();
            }
        }
    }

    // Retrieves a customer by customer ID
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    // Removes a customer by customer ID
    public void removeCustomer(String customerId) {
        while (true) {
            try {
                if (customers.containsKey(customerId)) {
                    customers.remove(customerId);
                    String finalCustomerId = customerId;
                    accounts.entrySet().removeIf(entry -> entry.getValue().getCustomer().getCustomerId().equals(finalCustomerId));
                    System.out.println("Customer with ID " + customerId + " has been removed.");
                    break;
                } else {
                    throw new InputValidationException("Customer ID " + customerId + " not found. Please enter a valid ID.");
                }
            } catch (InputValidationException e) {
                System.out.println(e.getMessage());
                Scanner scanner = new Scanner(System.in);
                System.out.print("Re-enter Customer ID: ");
                customerId = scanner.nextLine();
            }
        }
    }

    // Finds all accounts belonging to a specific customer
    public List<Account<?>> findAccountsByCustomer(Customer customer) {
        List<Account<?>> result = new ArrayList<>();
        for (Account<?> account : accounts.values()) {
            if (account.getCustomer().equals(customer)) {
                result.add(account);
            }
        }
        return result;
    }

    // Method to search account by selecting either name, DOB, or SIN
    public void searchAccount(String criteria, String value) {
        List<Customer> matchedCustomers = new ArrayList<>();
        switch (criteria.toLowerCase()) {
            case "name":
                for (Customer customer : customers.values()) {
                    if (customer.getFirstName().equalsIgnoreCase(value) || customer.getLastName().equalsIgnoreCase(value)) {
                        matchedCustomers.add(customer);
                    }
                }
                break;
            case "dob":
                for (Customer customer : customers.values()) {
                    if (customer.getDob().equals(value)) {
                        matchedCustomers.add(customer);
                    }
                }
                break;
            case "sin":
                for (Customer customer : customers.values()) {
                    if (customer.getSinNumber().equals(value)) {
                        matchedCustomers.add(customer);
                    }
                }
                break;
            default:
                System.out.println("Invalid search criteria.");
                return;
        }

        if (matchedCustomers.isEmpty()) {
            System.out.println("No customers found with the given " + criteria + ".");
        } else {
            for (Customer customer : matchedCustomers) {
                customer.printCustomerAccountDetails(this);
            }
        }
    }

    // Method to update customer info
    public void updateCustomerInfo(String customerId, String firstName, String lastName, String dob, String field, String newValue) {
        Customer customer = customers.get(customerId);
        if (customer != null && customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName) && customer.getDob().equals(dob)) {
            switch (field.toLowerCase()) {
                case "phone number":
                    customer.phoneNumber = newValue;
                    System.out.println("Phone number updated successfully.");
                    break;
                case "address":
                    customer.address = newValue;
                    System.out.println("Address updated successfully.");
                    break;
                case "password":
                    customer.password = newValue;
                    System.out.println("Password updated successfully.");
                    break;
                default:
                    System.out.println("Invalid field.");
                    break;
            }
        } else {
            System.out.println("Customer information does not match.");
        }
    }
}
