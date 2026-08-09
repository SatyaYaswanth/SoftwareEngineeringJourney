package Day9;

import java.util.LinkedHashMap;

public class LinkedHashMapDemo {
    public static void main(String[] args){
        LinkedHashMap<Integer, String> students = new LinkedHashMap<>();

        students.put(103, "Arjun");
        students.put(101, "Satya");
        students.put(102, "Rahul");
        students.put(104, "Ravi");
        students.put(101, "Suresh");
        System.out.println(students);
    }
}
