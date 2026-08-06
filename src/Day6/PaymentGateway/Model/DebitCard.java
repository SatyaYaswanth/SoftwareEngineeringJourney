package Day6.PaymentGateway.Model;

public class DebitCard implements Payment {
    @Override
    public void pay(int amt) {
        System.out.println("Payment using DebitCard.");
    }
}
