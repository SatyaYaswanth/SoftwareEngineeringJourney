package Day10.Sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparableDemo {

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        students.add(new Student("Satya", 85, 34));
        students.add(new Student("Rahul", 91, 45));
        students.add(new Student("Arjun", 91, 56));
        students.add(new Student("Kiran", 78, 67));

        System.out.println("Before sorting:");

        for (Student student : students) {
            System.out.println(student);
        }
        Comparator<Student> byName = Comparator.comparing(Student::getName);
        students.sort(byName);

        Comparator<Student> byAge = Comparator.comparing(Student::getAge);
        students.sort(byAge);

        Comparator<Student> byMarks = (s1, s2) -> Integer.compare(s1.getMarks(), s2.getMarks());
        students.sort(byMarks);

        Comparator<Student> ranking =
                Comparator.comparingInt(Student::getMarks)
                        .reversed()
                        .thenComparing(Student::getName);
        students.sort(ranking);
        students.sort(byMarks);

        System.out.println("\nAfter sorting:");

        for (Student student : students) {
            System.out.println(student);
        }
    }
}