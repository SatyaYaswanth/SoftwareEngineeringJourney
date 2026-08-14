package Day10.Sorting;

import java.util.Comparator;

public class Student implements Comparable<Student> {

    private String name;
    private int marks;
    private int age;

    public Student(String name, int marks, int age) {
        this.name = name;
        this.marks = marks;
        this.age= age;
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }

    public int getAge() {
        return age;
    }


    @Override
    public int compareTo(Student other) {
        return CharSequence.compare(other.name, this.name);
    }

    @Override
    public String toString() {
        return name + " - " + marks + " - " + age;
    }
//
//    @Override
//    public int compare(Student o1, Student o2) {
//        return 0;
//    }
}