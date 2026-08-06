package Day6.PaymentGateway.Model;

public class NetBanking implements Payment {
    @Override
    public void pay(int amt) {
        System.out.println("Payment using NetBanking.");
    }
}
