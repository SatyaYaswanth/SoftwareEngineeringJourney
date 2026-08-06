package Day8.StudentManagementV5;

import Day8.StudentManagementV5.Services.StudentServices;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentServices schoolStudents = new StudentServices();
        Scanner sc = new Scanner(System.in);
        boolean isRunning=true;
        int choice;
        while(isRunning){
            schoolStudents.menu();
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
                    int id;
                    System.out.print("Enter student id : ");
                    schoolStudents.searchStudent(id= sc.nextInt());
                    break;
                case 4:
                    int id1;
                    System.out.print("Enter student id : ");
                    schoolStudents.updateStudent(id1= sc.nextInt());
                    break;
                case 5:
                    int id2;
                    System.out.print("Enter student id : ");
                    schoolStudents.deleteStudent(id2=sc.nextInt());
                    break;
                case 6:
                    isRunning=false;
                    break;
                default:
                    System.out.println("Invalid entry! Try again.");
            }
        }
    }
}
