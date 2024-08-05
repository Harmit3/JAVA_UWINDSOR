package Bank;





// Validator.java
// Class responsible for validating user inputs
import java.util.Scanner;

// Validator class to validate user inputs
public class Validator {
    private Scanner scanner = new Scanner(System.in);

    // Validates the name to ensure it's not empty and contains only letters
    public String validateName(String name) {
        while (true) {
            try {
                if (name == null || name.trim().isEmpty()) {
                    throw new InputValidationException("Name cannot be empty.");
                }
                if (!name.matches("[a-zA-Z]+")) {
                    throw new InputValidationException("Name must contain only letters.");
                }
                return name;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                name = scanner.nextLine();
            }
        }
    }

    // Validates phone numbers to ensure they are exactly 10 digits
    public String validatePhoneNumber(String phoneNumber) {
        while (true) {
            try {
                if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                    throw new InputValidationException("Phone number must be exactly 10 digits.");
                }
                return phoneNumber;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                phoneNumber = scanner.nextLine();
            }
        }
    }

    // Validates email to ensure it contains the '@' character
    public String validateEmail(String email) {
        while (true) {
            try {
                if (email == null || !email.contains("@")) {
                    throw new InputValidationException("Email must contain '@' character.");
                }
                return email;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                email = scanner.nextLine();
            }
        }
    }

    // Validates SIN number to ensure it is exactly 9 digits
    public String validateSIN(String sinNumber) {
        while (true) {
            try {
                if (sinNumber == null || !sinNumber.matches("\\d{9}")) {
                    throw new InputValidationException("SIN must be exactly 9 digits.");
                }
                return sinNumber;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                sinNumber = scanner.nextLine();
            }
        }
    }

    // Validates DOB to ensure it is in the format MM/DD/YYYY and represents a valid date
    public String validateDOB(String dob) {
        while (true) {
            try {
                if (dob == null || !dob.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    throw new InputValidationException("DOB must be in the format MM/DD/YYYY.");
                }

                String[] parts = dob.split("/");
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                if (month < 1 || month > 12) {
                    throw new InputValidationException("Month must be between 1 and 12.");
                }

                if (day < 1 || day > 31) {
                    throw new InputValidationException("Day must be between 1 and 31.");
                }

                if (month == 2 && day > 29) {
                    throw new InputValidationException("February cannot have more than 29 days.");
                }

                if (month == 2 && day == 29 && !isLeapYear(year)) {
                    throw new InputValidationException("February 29 is not valid in a non-leap year.");
                }

                if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                    throw new InputValidationException("This month cannot have more than 30 days.");
                }

                return dob;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                dob = scanner.nextLine();
            }
        }
    }

    // Checks if a given year is a leap year
    private boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        if (year % 100 != 0) return true;
        return year % 400 == 0;
    }

    // Validates password to ensure it meets the complexity requirements
    public String validatePassword(String password) {
        while (true) {
            try {
                if (password.length() < 5) {
                    throw new InputValidationException("Password must be at least 5 characters long.");
                }

                boolean hasLetter = false;
                boolean hasDigit = false;
                boolean hasSpecialChar = false;

                for (char c : password.toCharArray()) {
                    if (Character.isLetter(c)) {
                        hasLetter = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    } else if (!Character.isLetterOrDigit(c)) {
                        hasSpecialChar = true;
                    }
                }

                if (!hasLetter || !hasDigit || !hasSpecialChar) {
                    throw new InputValidationException("Password must contain at least one letter, one digit, and one special character.");
                }

                return password;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                password = scanner.nextLine();
            }
        }
    }

    // Validates address format (City,Province,Street,PostCode)
    public String validateAddress(String address) {
        while (true) {
            try {
                if (address == null || !address.matches("[^,]+,[^,]+,[^,]+,[a-zA-Z0-9]{6}")) {
                    throw new InputValidationException("Address must be in the format City,Province,Street,PostCode (PostCode must be 6 characters).");
                }
                return address;
            } catch (InputValidationException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Enter again: ");
                address = scanner.nextLine();
            }
        }
    }

    // Validates the amount to ensure it is a positive number
    public double validateAmount(String input) {
        while (true) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    throw new InvalidAmountException("Amount must be greater than zero.");
                }
                return amount;
            } catch (InvalidAmountException | NumberFormatException e) {
                System.out.println("Invalid amount: " + e.getMessage() + " Enter again: ");
                input = scanner.nextLine();
            }
        }
    }
}





