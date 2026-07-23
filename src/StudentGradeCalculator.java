import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student Name:");
        String name = sc.nextLine();

        System.out.print("Enter Roll Number:");
        int rollNumber = sc.nextInt();

        int total = 0;
        boolean isPassed = true;

        for (int i = 1; i <= 5; i++) {
            System.out.print("Enter marks for Subject " + i + ":");
            int marks = sc.nextInt();

            total += marks;

            if (marks < 35) {
                isPassed = false;
            }
        }

        double average = total / 5.0;
        double percentage = total / 5.0;

        System.out.println("\n-------------------------");
        System.out.println("Student Name : " + name);
        System.out.println("Roll Number  : " + rollNumber);
        System.out.println("Total        : " + total);
        System.out.println("Average      : " + average);
        System.out.println("Percentage   : " + percentage + "%");

        if (!isPassed) {
            System.out.println("Result       : FAIL");
            System.out.println("Grade        : N/A");
        } else {
            System.out.println("Result       : PASS");

            if (percentage >= 90) {
                System.out.println("Grade        : A");
            } else if (percentage >= 80) {
                System.out.println("Grade        : B");
            } else if (percentage >= 70) {
                System.out.println("Grade        : C");
            } else if (percentage >= 60) {
                System.out.println("Grade        : D");
            } else {
                System.out.println("Grade        : E");
            }
        }
        System.out.println("-------------------------");

        sc.close();
    }
}