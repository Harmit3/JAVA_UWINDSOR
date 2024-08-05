package Bank;

// InsufficientFundsException.java
// Custom exception for insufficient funds during withdrawals and transfers

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
