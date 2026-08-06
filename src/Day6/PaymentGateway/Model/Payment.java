package Day6.PaymentGateway.Model;

public interface Payment {
    void pay(int amt);
    default void printReceipt() {
        System.out.println("Transaction Successful");
    }
}
