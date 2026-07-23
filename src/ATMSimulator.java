import java.util.Scanner;

public class ATMSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int balance = 10000;
        int choice;
        boolean loop=true;
        int temp=0;

        while (loop) {

            System.out.println("\n========== ATM ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Balance : " + balance);
                    break;

                case 2:
                    System.out.print("Deposit Amount :");
                    temp = sc.nextInt();
                    balance+=temp;
                    System.out.println(" Updated Balance : " + balance);
                    break;

                case 3:
                    System.out.print("Amount to withdraw: ");
                    temp = sc.nextInt();

                    if (temp <= balance) {
                        balance -= temp;
                        System.out.println("Withdrawal Successful");
                        System.out.println("Updated Balance: " + balance);
                    } else {
                        System.out.println("Insufficient Balance");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using our ATM!");
                    loop=false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }

    }
}