package Day6.PaymentGateway.Model;

public class UPI implements Payment {
    @Override
    public void pay(int amt) {
        System.out.println("Payment using UPI.");
    }
}
