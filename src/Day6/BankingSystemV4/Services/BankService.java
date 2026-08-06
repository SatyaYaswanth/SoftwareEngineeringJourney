package Day6.BankingSystemV4.Services;

import Day6.BankingSystemV4.Model.Account;
import Day6.BankingSystemV4.Payment.Payment;

public class BankService {

    public void deposit(Account account, Payment payment, int amount){
        payment.pay(amount);
        account.deposit(amount);
    }
}
