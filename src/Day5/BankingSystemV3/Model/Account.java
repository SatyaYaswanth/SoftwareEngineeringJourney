package Day5.BankingSystemV3.Model;
import java.util.Scanner;

public class Account {
    private int AccNumber;
    private String AccHolder;
    private int Balance = 2000;
    Scanner sc = new Scanner(System.in);
    public void deposit(){
        int amt;
        System.out.print("Enter amount to deposit : ");
        amt = sc.nextInt();
        amt+=getBalance();
        setBalance(amt);
    }
    public void withdraw(int ant){

    }
    public double displayBalance(){
        return getBalance();
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public int getAccNumber() {
        return AccNumber;
    }

    public void setAccNumber(int accNumber) {
        AccNumber = accNumber;
    }

    public String getAccHolder() {
        return AccHolder;
    }

    public void setAccHolder(String accHolder) {
        AccHolder = accHolder;
    }
}
