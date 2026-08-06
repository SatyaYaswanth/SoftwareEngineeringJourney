package Day5;

import Day5.Model.Student;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Student student = new Student("arjun", 35, 677776, 77);

        System.out.println(student.getAge());
        System.out.println(student.getName());
        System.out.println(student.getPhone());
    }
}
