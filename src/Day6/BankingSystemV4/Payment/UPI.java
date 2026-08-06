package Day6.BankingSystemV4.Payment;

public class UPI implements Payment{
    @Override
    public void pay(int amount) {
        System.out.println("Payment of " + amount + " done through UPI.");
    }
}
