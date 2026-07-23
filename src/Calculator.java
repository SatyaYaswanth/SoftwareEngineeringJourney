import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("========== Calculator ==========");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Power");
            System.out.println("6. Square Root");
            System.out.println("7. Exit");
            System.out.println();

            System.out.print("Enter Choice: ");
            int choice = scanner.nextInt();

            System.out.println();

            if (choice == 7) {
                System.out.print("Thanks for using Calculator");
                running = false;
                continue;
            }

            double result = 0;
            double num1, num2;

            switch (choice) {
                case 1:
                    System.out.print("Enter First Number: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Enter Second Number: ");
                    num2 = scanner.nextDouble();
                    result = num1 + num2;
                    System.out.println("\nResult: " + result );
                    break;
                case 2:
                    System.out.print("Enter First Number: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Enter Second Number: ");
                    num2 = scanner.nextDouble();
                    result = num1 - num2;
                    System.out.println("\nResult: " + result );
                    break;
                case 3:
                    System.out.print("Enter First Number: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Enter Second Number: ");
                    num2 = scanner.nextDouble();
                    result = num1 * num2;
                    System.out.println("\nResult: " + result );
                    break;
                case 4:
                    System.out.print("Enter Dividend: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Enter Divisor: ");
                    num2 = scanner.nextDouble();
                    if (num2 == 0) {
                        System.out.println("\nError: Cannot divide by zero.");
                    } else {
                        result = num1 / num2;
                        System.out.println("\nResult: " + result );
                    }
                    break;
                case 5:
                    System.out.print("Enter Base: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Enter Exponent: ");
                    num2 = scanner.nextDouble();
                    result = Math.pow(num1, num2);
                    System.out.println("\nResult: " + result );
                    break;
                case 6:
                    System.out.print("Enter Number: ");
                    num1 = scanner.nextDouble();
                    result = Math.sqrt(num1);
                    System.out.println("\nResult: " + result );
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }

        scanner.close();
    }
}