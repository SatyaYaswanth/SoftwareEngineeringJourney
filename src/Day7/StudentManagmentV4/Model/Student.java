package Day7.StudentManagmentV4.Model;

import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    Scanner sc = new Scanner(System.in);
    ArrayList<Student> students = new ArrayList<>();
    private int id;
    String name;
    int age;
    Student(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public void displayStudents(Student student){
        for(Student studentDetails: students){
            System.out.println(studentDetails);
        }
    }
    public void addStudent(Student student){
        students.add(student);
    }

    public void searchStudent(Student student, int id) {
        for(Student studentDetails : students){
            if(studentDetails.getId()==id){
                System.out.println("Student Found\n" + student);
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
                students.get(i).name=sc.next();
                System.out.print("Enter student new age :");
                students.get(i).age=sc.nextInt();
                System.out.println("Updated student details.");
                return;
            }
        }
        System.out.println("Student not found.");
    }
    public int getId() {
        return id;
    }
}
