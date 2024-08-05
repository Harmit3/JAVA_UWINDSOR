package Bank;

// Customer.java
// Class representing customer users
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Customer class for handling customer actions
public class Customer {
    private String customerId;
    String password;
    private String firstName;
    private String lastName;
    private String email;
    String phoneNumber;
    private String sinNumber;
    private String dob;
    String address;

    // Constructor to initialize customer details
    public Customer(String customerId, String password, String firstName, String lastName,
                    String email, String phoneNumber, String sinNumber, String dob, String address) {
        this.customerId = customerId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.dob = dob;
        this.address = address;
    }

    // Getters for customer details
    public String getCustomerId() {
        return customerId;
    }

    public String getPassword() {
        return password;
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

    public String getAddress() {
        return address;
    }

    // Validates customer login credentials
    public boolean validate(String customerId, String password) {
        return this.customerId.equals(customerId) && this.password.equals(password);
    }

    // Prints account details for this specific customer
    public void printCustomerAccountDetails(Bank bank) {
        List<Account<?>> accounts = bank.findAccountsByCustomer(this);

        if (accounts.isEmpty()) {
            System.out.println("No accounts found for customer: " + this.getFirstName() + " " + this.getLastName());
        } else {
            for (Account<?> account : accounts) {
                account.printAccountDetails();
            }
        }
    }

    // Generates a report for this specific customer
    public void generateCustomerReport(Bank bank, String filename) {
        List<Account<?>> accounts = bank.findAccountsByCustomer(this);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for customer: " + this.getFirstName() + " " + this.getLastName());
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (Account<?> account : accounts) {
                    writer.println("Account Number: " + account.getAccountNumber());
                    writer.println("Customer ID: " + this.getCustomerId());
                    writer.println("Name: " + this.getFirstName() + " " + this.getLastName());
                    writer.println("Email: " + this.getEmail());
                    writer.println("Phone Number: " + this.getPhoneNumber());
                    writer.println("SIN: " + this.getSinNumber());
                    writer.println("DOB: " + this.getDob());
                    writer.println("Address: " + this.getAddress());
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
