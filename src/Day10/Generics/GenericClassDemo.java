package Day10.Generics;


import java.lang.reflect.Array;

public class GenericClassDemo {

    public static void main(String[] args){

        Box<String[]> box1 = new Box<>();
        box1.setValue(new String[]{"CodePilot"});
        System.out.println(box1.getValue());
        box1.displayItems(box1.getValue());
        System.out.println(getFirst(box1.getValue()));

        Integer[] age = {34, 56, 66};
        System.out.println(getFirst(age));

        Double[] salary = {34.01, 56.23, 66.45};
        System.out.println(getFirst(salary));
    }

    public static <T> T getFirst(T[] array){
        return array[0];

    }
}
