package Day4.Service;
import Day4.model.Student;

import java.util.Scanner;

public class StudentService {
    private Scanner sc = new Scanner(System.in);
    private int count=0;
    private Student[] students = new Student[100];

    public void addStudent(){

        System.out.print("Enter id : ");
        int id = sc.nextInt();
        System.out.print("Enter name : ");
        String name = sc.next();
        System.out.print("Enter age : ");
        int age = sc.nextInt();
        students[count]= new Student(id, name, age);
        count++;

    }

    public void displayStudents(){
        if(count == 0){
            System.out.println("No students found.");
            return;
        }
        for(int i=0; i< count; i++){
            System.out.println("Student " + (i+1) + " Details : ");
            System.out.println("Id : " + students[i].id);
            System.out.println("Name : " + students[i].name);
            System.out.println("Age : " + students[i].age);
        }
    }

    public int search() {
        String name;
        System.out.print("Enter Student name : ");
        name = sc.next();
        int i;
        boolean isFound=false;
        for (i = 0; i < count; i++) {
            if (name.equalsIgnoreCase(students[i].name)) {
                System.out.println("\nStudent Found.");
                System.out.println("Id : " + students[i].id);
                System.out.println("Name : " + students[i].name);
                System.out.println("Age : " + students[i].age);
                System.out.println();
                isFound=true;
                break;
            }
        }
        if(isFound==false){
            return -1;
        }
        return i;
    }

    public void update(){
        int index = search();

        if(index == -1){
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Student id to update : ");
        students[index].id = sc.nextInt();

        System.out.print("Enter Student name to update : ");
        students[index].name = sc.next();

        System.out.print("Enter Student age to update : ");
        students[index].age = sc.nextInt();

        System.out.print("\nStudent details updated.");
    }

    public void delete(){
        int idx = search();

        if(idx == -1){
            System.out.println("Student not found.");
            return;
        }

        for (int i = idx; i < count-1; i++) {
            students[i] = students[i+1];
        }
        count--;


    }
}
