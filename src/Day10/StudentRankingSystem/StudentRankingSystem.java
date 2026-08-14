package Day10.StudentRankingSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentRankingSystem {

    static List<Student> students = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== STUDENT RANKING SYSTEM =====");

            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Sort by Marks");
            System.out.println("4. Sort by Name");
            System.out.println("5. Sort by Age");
            System.out.println("6. Search Student");
            System.out.println("7. Student Ranking.");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    sortByMarks();
                    break;

                case 4:
                    sortByName();
                    break;

                case 5:
                    sortByAge();
                    break;

                case 6:
                    searchStudent();
                    break;

                case 7:
                    studentRanking();
                    return;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        sc.nextLine(); // consume leftover newline

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Student Age: ");
        int age = sc.nextInt();

        System.out.print("Enter Student Marks: ");
        int marks = sc.nextInt();

        Student student = new Student(id, name, age, marks);

        students.add(student);

        System.out.println("Student added successfully!");
    }

    public static void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n===== STUDENT LIST =====");

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static void sortByMarks() {

        students.sort(null);

        System.out.println("Students sorted by marks.");
    }

    public static void sortByName() {

        Comparator<Student> byName =
                Comparator.comparing(Student::getName);

        students.sort(byName);

        System.out.println("Students sorted by name.");
    }

    public static void sortByAge(){
        Comparator<Student> byAge = Comparator.comparingInt(Student::getAge);
        students.sort(byAge);
        System.out.println("Students sorted by age.");
    }

    public static void searchStudent() {
        System.out.print("Enter id to search : ");
        int id = sc.nextInt();

        for (Student item : students){
            if(id== item.getId()){
                System.out.print(item);
                return;
            }
        }
        System.out.println("Student not found.");

    }

    public static void studentRanking(){
        Comparator<Student> ranking = Comparator.comparingInt(Student::getMarks).reversed().thenComparing(Student::getName);
        students.sort(ranking);
        for (Student student : students) {
            System.out.println(student);
        }

    }
}