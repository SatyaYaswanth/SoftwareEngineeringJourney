package Day14.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Account implements Serializable {

    protected String accountNumber;
    protected String customerId;
    protected double balance;

    protected List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber,
                   String customerId,
                   double balance) {

        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        balance += amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}