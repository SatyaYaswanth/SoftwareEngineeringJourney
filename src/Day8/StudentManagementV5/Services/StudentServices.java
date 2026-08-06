package Day8.StudentManagementV5.Services;

import Day8.StudentManagementV5.Model.Student;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentServices {
    private ArrayList<Student> students = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    public static void menu(){
        System.out.println("====Student Management====");
        System.out.println("1.Add student.");
        System.out.println("2.Display students.");
        System.out.println("3.Search.");
        System.out.println("4.Update.");
        System.out.println("5.Delete.");
        System.out.println("6.Exit.");
    }

    public void addStudent() {

        int id;
        String name;
        int age;
        System.out.print("Enter Student id: ");
        id = sc.nextInt();
        for(Student student : students){
            if(student.getId() == id){
                System.out.println("Student ID already exists.");
                return;
            }
        }
        System.out.print("Enter Student name: ");
        name = sc.next();
        System.out.print("Enter Student age: ");
        age = sc.nextInt();

        students.add(new Student(id, name, age));
    }

    public void displayStudents(){
        if(students.isEmpty()){
            System.out.println("No students found.");
            return;
        }
        for(Student studentDetails: students){
            System.out.println(studentDetails);
        }
    }

    public void searchStudent(int id) {
        for(Student studentDetails : students){
            if(studentDetails.getId()==id){
                System.out.println(studentDetails);
                return;
            }
        }
        System.out.print("Student not found");
    }

    public void deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                System.out.println("Student Deleted Successfully");
                students.remove(i);
                return;
            }
        }
        System.out.println("Student Not Found");
    }

    public void updateStudent(int id){
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                System.out.print("Enter student new name :");
                students.get(i).setName(sc.next());
                System.out.print("Enter student new age :");
                students.get(i).setAge(sc.nextInt());
                System.out.println("Updated student details.");
                return;
            }
        }
        System.out.println("Student not found.");
    }



}
