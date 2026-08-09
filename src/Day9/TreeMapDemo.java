package Day9;

import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args){
        TreeMap<Integer, String> students = new TreeMap<>();

        students.put(105, "Ravi");
        students.put(101, "Satya");
        students.put(103, "Rahul");
        students.put(102, "Arjun");

        System.out.println(students);

        System.out.println(students.firstKey());
        System.out.println(students.lastKey());

        System.out.println(students.higherKey(102));
        System.out.println(students.lowerKey(102));

        System.out.println(students.ceilingKey(104));
        System.out.println(students.floorKey(104));
    }
}
