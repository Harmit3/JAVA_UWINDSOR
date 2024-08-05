package Bank;

// Transaction.java
// Class to manage individual transactions

// Transaction class to manage individual transactions
public class Transaction {
    private String description;
    private double amount;

    // Constructor to initialize transaction details
    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    // Returns transaction details as a string
    @Override
    public String toString() {
        return description + ": " + amount;
    }
}
