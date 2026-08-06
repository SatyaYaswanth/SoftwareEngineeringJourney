package Day8.CollectionHierarchy;

import java.util.ArrayList;

public class ArrayDemo {
    public static void main(String[] args){
        ArrayList<String> names = new ArrayList<>();

        names.add("Hello");
        names.add("jai");
        names.add("welcome");
        System.out.println(names);
        names.remove(0);
        System.out.println(names);
        names.remove("jai");
        System.out.println(names);


    }
}
