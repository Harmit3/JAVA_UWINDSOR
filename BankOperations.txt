package Bank;

// BankOperations.java
// Interface for defining common banking operations using generics
public interface BankOperations<T> {
    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;
    void transfer(Account<?> toAccount, double amount) throws InsufficientFundsException, InvalidAmountException;
    void printAccountDetails();
    void writeToFile(String filename);
}
