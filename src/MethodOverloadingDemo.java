public class MethodOverloadingDemo {

    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        int sum1 = add(10, 20);
        System.out.println("Result of add(10, 20): " + sum1);

        double sum2 = add(15.5, 20.2);
        System.out.println("Result of add(15.5, 20.2): " + sum2);

        int sum3 = add(5, 10, 15);
        System.out.println("Result of add(5, 10, 15): " + sum3);
    }
}