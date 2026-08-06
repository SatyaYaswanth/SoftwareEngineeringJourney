package Day6.BankingSystemV4.Model;

public class SavingsAccount extends Account{
    @Override
    public void debit(int amt) {
        if (getBalance()-amt<1000){
            System.out.println("Insufficeint balance.");
            return;
        }
        setBalance(getBalance()-amt);
        System.out.println("Amount debited.");
    }
}
