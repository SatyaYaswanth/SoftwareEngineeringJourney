package Day6.PaymentGateway;

import Day6.PaymentGateway.Model.*;

public class Main {
    public static void main(String[] args){
        Payment p1 = new UPI();
        Payment p2 = new DebitCard();
        Payment p3 = new CreditCard();
        Payment p4 = new NetBanking();

        p1.pay(1000);
        p2.pay(2000);
        p3.pay(3000);
        p4.pay(400);
        p1.printReceipt();
    }

}
