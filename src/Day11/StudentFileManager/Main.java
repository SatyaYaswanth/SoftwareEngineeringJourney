package Day11.StudentFileManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void menu(){
        System.out.println("========================");
        System.out.println("\tSTUDENT FILE MANAGER");
        System.out.println("========================");
        System.out.println("1. Add Student");
        System.out.println("2. Display Students");
        System.out.println("3. Search Student");
        System.out.println("4. Exit");
    }
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();

        service.loadStudents();
        while(true){
            menu();
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter id : ");
                    int id = sc.nextInt();;
                    System.out.print("Enter name : ");
                    String name = sc.next();
                    System.out.print("Emter age : ");
                    int age = sc.nextInt();
                    System.out.print("Enter course : ");
                    String course = sc.next();
                    System.out.print("Enter marks : ");
                    Double marks = sc.nextDouble();
                    service.addStudents(id, name, age, course, marks);
                    break;
                case 2:
                    service.displayStudents();
                    break;
                case 3:
                    System.out.print("Enter student id : ");
                    int id1 = sc.nextInt();
                    service.searchStudent(id1);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }

    }
}
