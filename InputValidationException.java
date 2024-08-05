package Bank;


// InputValidationException.java
// Custom exception for input validation errors
public class InputValidationException extends Exception {
    public InputValidationException(String message) {
        super(message);
    }
}