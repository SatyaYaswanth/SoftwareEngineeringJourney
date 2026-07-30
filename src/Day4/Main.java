package Day4;

import Day4.Service.StudentService;
import java.util.Scanner;

public class Main {
    public static void menu(){
        System.out.println("====Student Management Portal====");
        System.out.println("1.Add student.");
        System.out.println("2.Display students.");
        System.out.println("3.Search.");
        System.out.println("4.Update.");
        System.out.println("5.Delete.");
        System.out.println("6.Exit.");
    }
    public static void main(String[] args) {
        StudentService schoolStudents = new StudentService();
        Scanner sc = new Scanner(System.in);
        boolean isRunning=true;
        int choice;
        while(isRunning){
            menu();
            System.out.print("Enter your choice : ");
            choice=sc.nextInt();
            switch (choice){
                case 1:
                    schoolStudents.addStudent();
                    break;
                case 2:
                    schoolStudents.displayStudents();
                    break;
                case 3:
                    schoolStudents.search();
                    break;
                case 4:
                    schoolStudents.update();
                    break;
                case 5:
                    schoolStudents.delete();
                    break;
                case 6:
                    isRunning=false;
                default:
                    System.out.println("Invalid entry! Try again.");
            }
        }
    }
}