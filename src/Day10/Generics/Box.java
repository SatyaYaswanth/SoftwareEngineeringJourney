package Day10.Generics;

public class Box<T> {

    private T value;
    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }

    public static <T> void displayItems(T[] item){
        for(T val: item){
            System.out.println(val);
        }
    }


}
