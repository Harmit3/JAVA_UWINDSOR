package Bank;

// CheckingAccount.java
// CheckingAccount class inheriting from Account for checking accounts
public class CheckingAccount extends Account<Double> {
    public CheckingAccount(String accountNumber, Customer customer, double initialBalance) {
        super(accountNumber, customer, initialBalance);
    }
}

