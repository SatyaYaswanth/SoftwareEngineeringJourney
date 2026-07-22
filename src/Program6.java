import java.util.Scanner;

public class Program6 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your age : ");
        int age=sc.nextInt();
        int age = 65;

        if (age < 0) {
            System.out.println("Invalid");
        }
        else if (age < 18) {
            System.out.println("Minor");
        }
        else if (age < 60) {
            System.out.println("Adult");
        }
        else {
            System.out.println("Senior");
        }
        sc.close();
    }
}
