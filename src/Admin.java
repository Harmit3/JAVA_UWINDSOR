package Bank;

// Admin.java
import java.util.Scanner;

// Admin class for handling admin login and authentication
public class Admin {
    private String adminId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String sinNumber;
    private String dob;
    private String position;
    private String address;

    // Constructor to initialize admin details
    public Admin(String adminId, String password, String firstName, String lastName,
                 String email, String phoneNumber, String sinNumber, String dob, String position, String address) {
        this.adminId = adminId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.dob = dob;
        this.position = position;
        this.address = address;
    }

    // Getters for admin details
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

    public String getAddress() {
        return address;
    }

    // Validates admin login credentials
    public boolean validate(String adminId, String password) {
        return this.adminId.equals(adminId) && this.password.equals(password);
    }

    // Method to delete an account (either employee or customer)
    public void deleteAccount(Bank bank, String accountId) {
        while (true) {
            try {
                if (bank.getEmployee(accountId) != null) {
                    bank.removeEmployee(accountId);
                    System.out.println("Employee account with ID " + accountId + " has been deleted.");
                } else if (bank.getCustomer(accountId) != null) {
                    bank.removeCustomer(accountId);
                    System.out.println("Customer account with ID " + accountId + " has been deleted.");
                } else {
                    throw new InputValidationException("Account with ID " + accountId + " not found. Please enter a valid ID.");
                }
                break;
            } catch (InputValidationException e) {
                System.out.println(e.getMessage());
                Scanner scanner = new Scanner(System.in);
                System.out.print("Re-enter Account ID: ");
                accountId = scanner.nextLine();
            }
        }
    }
}
