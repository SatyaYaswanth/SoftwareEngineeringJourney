package Day12;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;

public class FunctionalInterfacePractice {

    public static void main(String[] args){
        Predicate<Integer> isGT100 = number -> number>100;
        Consumer<String> name = naam -> System.out.println("Employee : " + naam);
        Supplier<String> welcome = ()-> "Welcome to codepilot.";
        Function<Integer, Integer> square = a -> a*a;

        System.out.println(isGT100.test(400));
        name.accept("Rahul");
        System.out.println(welcome.get());
        System.out.println(square.apply(3));
    }
}
