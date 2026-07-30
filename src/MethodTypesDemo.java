public class MethodTypesDemo {

    public static void greet() {
        System.out.println("Hello! Welcome to the methods demonstration.");
    }

    public static void printSquare(int n) {
        int square = n * n;
        System.out.println("The square of " + n + " is: " + square);
    }

    public static double getPiValue() {
        return 3.14159;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {

        System.out.println("--- 1. Calling greet() ---");
        greet();

        System.out.println("\n--- 2. Calling printSquare(int n) ---");
        printSquare(5);

        System.out.println("\n--- 3. Calling getPiValue() ---");
        double pi = getPiValue();
        System.out.println("The value of Pi is: " + pi);

        System.out.println("\n--- 4. Calling multiply(int a, int b) ---");
        int result = multiply(7, 8);
        System.out.println("The product of 7 and 8 is: " + result);
    }
}