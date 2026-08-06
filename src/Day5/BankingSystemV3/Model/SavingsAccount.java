package Day5.BankingSystemV3.Model;

public class SavingsAccount extends Account{
    @Override
    public void withdraw(int amt) {
        if(super.getBalance()-amt<1000){
            System.out.println("Min Balance reached.");
            return;
        }

        setBalance(getBalance()-amt);
        System.out.println("Amount  debeted.");
    }
}
