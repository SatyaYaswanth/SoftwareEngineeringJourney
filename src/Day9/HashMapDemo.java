package Day9;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args){
        HashMap<Integer, String> students = new HashMap<>();

        students.put(101, "Satya");
        students.put(102, "Rahul");
        students.put(103, "Arjun");

        System.out.println(students);
        System.out.println(students.get(101));
        System.out.println(students.get(999));

        students.remove(102);

        System.out.println(students.containsKey(101));

        System.out.println(students.containsKey(999));

        System.out.println(students.containsValue("Arjun"));

        System.out.println(students.size());

        System.out.println(students.isEmpty());

        students.clear();

        System.out.println(students);

        students.put(101, "Satya");
        students.put(102, "Rahul");

        students.put(101, "Ravi");

        System.out.println(students);
    }
}
