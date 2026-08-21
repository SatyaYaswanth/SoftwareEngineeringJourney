package Day14.model;

public class CurrentAccount extends Account {

    private double minimumBalance;

    public CurrentAccount(String accountNumber,
                          String customerId,
                          double balance,
                          double minimumBalance) {

        super(accountNumber, customerId, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) {

        if (balance - amount < minimumBalance) {
            throw new RuntimeException(
                    "Minimum balance requirement violated"
            );
        }

        balance -= amount;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}