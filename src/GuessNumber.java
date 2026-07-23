import java.util.Scanner;
import java.util.Random;

public class GuessNumber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int guess=-1;
        int secretNum= random.nextInt(100)+1;
        int attempts = 0;

        while(guess!=secretNum){
            System.out.print("Guess(1 to 100) : ");
            guess = sc.nextInt();
            if(guess<1 || guess>100){
                System.out.println("Invalid input, try again...");
                continue;
            }
            attempts++;
            if(secretNum<guess){
                System.out.println("Too high");
            }else{
                System.out.println("Too low");
            }
        }
        System.out.println("Congratulations!");
        System.out.println("Attempts : " + attempts);

        sc.close();

    }
}
