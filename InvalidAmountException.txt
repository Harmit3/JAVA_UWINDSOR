package Bank;

// InvalidAmountException.java
// Custom exception for invalid transaction amounts
// Custom exception for invalid amounts in transactions
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
