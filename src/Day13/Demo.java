package Day13;

import java.util.List;


public class Demo {
    public static Boolean isEven(int n){
        if(n%2==0){
            return true;
        }
        return false;

    }
    public static void main(String[] args){
        List<Integer> numbers = List.of(
                5, 10, 15, 20, 25, 30
        );

        int sum = numbers.stream().filter(n->isEven(n)).map(n-> n*n).reduce(0, (a,b)-> a+b);
        System.out.println(sum);
    }
}
