package Day9;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args){
        TreeSet<Integer> set = new TreeSet<>();
        set.add(3);
        set.add(4);
        set.add(1);
        set.add(10);
        System.out.println(set);
        set.remove(1);
        System.out.println(set.first());
        System.out.println(set.contains(1));

        System.out.println(set.size());
        System.out.println(set.isEmpty());
        System.out.println(set);
        System.out.println(set.first());
        System.out.println(set.last());

        System.out.println(set.higher(30));
        System.out.println(set.lower(30));

        System.out.println(set.ceiling(25));
        System.out.println(set.floor(25));
    }
}
