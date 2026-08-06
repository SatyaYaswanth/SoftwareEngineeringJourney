package Day6.BankingSystemV4.Model;

public abstract class Account {
    private int balance=500;
    public void debit(int amt){

    }
    public void deposit(int amt){
        setBalance(balance+amt);
        System.out.println("Amount deposited.");
        System.out.println("Current Balance :" + getBalance());
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int amt) {
        balance=amt;
    }
}
