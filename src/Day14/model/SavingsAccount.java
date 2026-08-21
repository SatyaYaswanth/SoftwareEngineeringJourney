package Day14.model;

import Day14.exception.InsufficientBalanceException;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber,
                          String customerId,
                          double balance,
                          double interestRate) {

        super(accountNumber, customerId, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        balance -= amount;
    }

    public double getInterestRate() {
        return interestRate;
    }
}