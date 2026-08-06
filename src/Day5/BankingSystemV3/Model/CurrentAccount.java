package Day5.BankingSystemV3.Model;

public class CurrentAccount extends Account{
    @Override
    public void withdraw(int amt) {
        if(super.getBalance()-amt<-5000){
            System.out.println("Max debit limit reached.");
            return;
        }

        setBalance(getBalance()-amt);
        System.out.println("Amount  debeted.");
    }
}
