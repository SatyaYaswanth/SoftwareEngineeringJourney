package Day6.BankingSystemV4;

import Day6.BankingSystemV4.Model.Account;
import Day6.BankingSystemV4.Model.CurrentAccount;
import Day6.BankingSystemV4.Model.SavingsAccount;
import Day6.BankingSystemV4.Payment.Payment;
import Day6.BankingSystemV4.Payment.UPI;
import Day6.BankingSystemV4.Services.BankService;

public class Main {
    public static void main(String[] args){
        Account account = new CurrentAccount();
        Payment payment = new UPI();
        System.out.print("Enter amount : ");
        int amount=300;
        BankService bankService = new BankService();
        bankService.deposit(account, payment, amount);
    }
}
