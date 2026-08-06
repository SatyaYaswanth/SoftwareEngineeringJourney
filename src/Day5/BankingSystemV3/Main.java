package Day5.BankingSystemV3;

import Day5.BankingSystemV3.Model.Account;
import Day5.BankingSystemV3.Model.CurrentAccount;

public class Main {
    public static void main(String[] args){
        Account acc = new CurrentAccount();
        acc.withdraw(4000);
        acc.withdraw(4000);
    }
}
