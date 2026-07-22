import java.util.Scanner;

public class Program4 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Tem in Celsius : ");
        float celsius= sc.nextFloat();
        float fahrenheit;

        fahrenheit = (celsius * ((float) 9 / 5)) + 32;

        System.out.print("Tem in Fahrenheit : " + fahrenheit);

        sc.close();
    }
}
