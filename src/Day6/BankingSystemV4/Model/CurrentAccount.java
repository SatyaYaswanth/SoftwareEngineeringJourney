package Day6.BankingSystemV4.Model;

import Day6.BankingSystemV4.Model.Account;

public class CurrentAccount extends Account {
    @Override
    public void debit(int amt) {
        if(super.getBalance()-amt<-5000){
            System.out.println("Max debit limit reached.");
            return;
        }

        setBalance(getBalance()-amt);
        System.out.println("Amount debited.");
    }
}
