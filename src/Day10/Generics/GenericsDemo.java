package Day10.Generics;

import java.util.ArrayList;

public class GenericsDemo {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();

        names.add("Satya");
        names.add("Rahul");
        names.add("Arjun");
        names.add("Ram");

        for (String name : names) {
            System.out.println(name);
        }
    }
}
