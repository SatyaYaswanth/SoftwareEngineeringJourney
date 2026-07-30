import java.util.Scanner;

public class StudentManagementSystem {

    public static void displayAllStudents(String[] names, int[] marks) {
        System.out.println("\n--- All Students ---");
        for (int i = 0; i < names.length; i++) {
            System.out.println("Name: " + names[i] + " | Marks: " + marks[i]);
        }
    }

    public static void searchStudents(String Sname, String[] names, int[] marks) {
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(Sname)) {
                System.out.println("\n" + names[i] + " is found. Marks: " + marks[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\nStudent not found.");
        }
    }

    public static void updateMarks(String[] names, int[] marks, Scanner sc) {
        System.out.print("Enter Student Name to update: ");
        String Sname = sc.next();
        boolean found = false;

        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(Sname)) {
                System.out.print("Enter new marks for " + names[i] + ": ");
                marks[i] = sc.nextInt();
                System.out.println("Marks updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }

    public static void classAverage(int[] marks) {
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        double average = (double) sum / marks.length;
        System.out.println("\nClass Average: " + average);
    }

    public static void findTopper(String[] names, int[] marks) {
        int maxIndex = 0;
        for (int i = 1; i < marks.length; i++) {
            if (marks[i] > marks[maxIndex]) {
                maxIndex = i;
            }
        }
        System.out.println("\nClass Topper is " + names[maxIndex] + " with " + marks[maxIndex] + " marks.");
    }

    public static void displayMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Display all students");
        System.out.println("2. Search student by name");
        System.out.println("3. Update marks");
        System.out.println("4. Calculate class average");
        System.out.println("5. Find topper");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        String[] name = {"Anil", "Balu", "Chakri", "Dhanush", "Bhanu"};
        int[] marks = {70, 89, 46, 66, 73};
        boolean isRunning = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        String Sname;

        while (isRunning) {
            displayMenu();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayAllStudents(name, marks);
                    break;
                case 2:
                    System.out.print("Enter Student Name: ");
                    Sname = sc.next();
                    searchStudents(Sname, name, marks);
                    break;
                case 3:
                    updateMarks(name, marks, sc);
                    break;
                case 4:
                    classAverage(marks);
                    break;
                case 5:
                    findTopper(name, marks);
                    break;
                case 6:
                    isRunning = false;
                    System.out.println("Exiting System... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}