import java.util.Scanner;

public class Program5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=====Area Calculator=====");
        System.out.println("Circle    : 1");
        System.out.println("Rectangle : 2");
        System.out.println("Your Choices : ");
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Enter Radius : ");
            int radius = sc.nextInt();
            System.out.println("Area : " + (3.14 * radius * radius));
        } else if (choice == 2) {
            System.out.println("Enter Length & Breadth : ");
            int length = sc.nextInt();
            int breadth = sc.nextInt();
            System.out.println("Area : " + (length * breadth));
        } else if (choice == 3) {
            System.out.println("Enter Length : ");
            int l = sc.nextInt();
            System.out.println("Area : " + (l * l));
        } else {
            System.out.println("Entered Wrong Number : ");
        }
        sc.close();
    }
}
