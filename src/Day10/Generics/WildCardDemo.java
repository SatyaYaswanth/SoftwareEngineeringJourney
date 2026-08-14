package Day10.Generics;

import java.util.ArrayList;
import java.util.List;

public class WildCardDemo {
    public static void main(String[] args){

        List<Object> names = new ArrayList<>();
        Box<Integer> box1 = new Box();
        names.add(box1);
        names.add(45.45);
        names.add(34.000);


        List<Number> numbers = new ArrayList<>();

        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        displayArr(names);
        displayArr(numbers);
    }

    public static void displayArr(List<? super Integer> args){
        System.out.println("List contains: " + args.size());
        for(Object item : args){
            System.out.println(item);
        }
        return ;
    }
}
