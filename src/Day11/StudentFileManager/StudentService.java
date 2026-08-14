package Day11.StudentFileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentService {
    private ArrayList<Student> students = new ArrayList<>();

    public void saveStudent(Student student) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/students.txt", true));
        String line = student.getId() + "," +
                student.getName() + "," +
                student.getAge() + "," +
                student.getCourse() + "," +
                student.getMarks();
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    public void addStudents(int id, String name, int age, String course, double marks) throws IOException {
        Student student = new Student(id, name, age, course, marks);
        students.add(student);
        saveStudent(student);
    }

    public void searchStudent(int id){

        for( Student item : students){
            if(item.getId()==id) {
                System.out.println(item);
            }
        }


    }

    public Student loadStudent(String line) {

        String[] data = line.split(",");

        int id = Integer.parseInt(data[0]);
        String name = data[1];
        int age = Integer.parseInt(data[2]);
        String course = data[3];
        double marks = Double.parseDouble(data[4]);

        return new Student(id, name, age, course, marks);
    }

    public void loadStudents() throws IOException {

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader("data/students.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                Student student = loadStudent(line);

                students.add(student);
            }
        }
    }

    public void displayStudents(){
        for( Student item : students){
            System.out.println(item);
        }
    }
}
