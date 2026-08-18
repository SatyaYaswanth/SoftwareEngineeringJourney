package Day12;

import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReferencePractice {

    private static Integer square(Integer integer) {
        return integer*integer;
    }

    static class Student {

        String name;

        Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args){
        Consumer<String> printer = System.out::println;
        printer.accept("CodePilot");

        Function<String, String> upper = String::toUpperCase;
        printer.accept(upper.apply("Hello World."));

        Function<Integer, Integer> squareIt = MethodReferencePractice::square;

        System.out.println(squareIt.apply(450));

        Function<String, Student> creator = Student::new;

        Student student = creator.apply("Satya");

        System.out.println(student);
    }
;

}
