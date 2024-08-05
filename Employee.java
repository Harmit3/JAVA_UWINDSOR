package Bank;

// Employee.java
// Class representing employee users
// Employee class for handling employee information
public class Employee {
    private String employeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String sinNumber;
    private String dob;
    private String position;
    private String address;

    // Constructor to initialize employee details
    public Employee(String employeeId, String password, String firstName, String lastName,
                    String email, String phoneNumber, String sinNumber, String dob, String position, String address) {
        this.employeeId = employeeId;
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

    // Getters for employee details
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

    public String getAddress() {
        return address;
    }

    // Validates employee login credentials
    public boolean validate(String employeeId, String password) {
        return this.employeeId.equals(employeeId) && this.password.equals(password);
    }
}


