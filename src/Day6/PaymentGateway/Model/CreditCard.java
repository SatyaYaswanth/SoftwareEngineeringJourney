package Day6.PaymentGateway.Model;

public class CreditCard implements Payment {
    @Override
    public void pay(int  amt) {
        System.out.println("Payment using CreditCard.");
    }
}