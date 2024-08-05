package Bank;

// SavingsAccount.java
// SavingsAccount class inheriting from Account for savings accounts
public class SavingsAccount extends Account<Double> {
    public SavingsAccount(String accountNumber, Customer customer, double initialBalance) {
        super(accountNumber, customer, initialBalance);
    }
}


