package Day15;

import java.util.Optional;

public class OptionalPractice {

    public Optional<String> findUserEmail(String string){
        if (string.equalsIgnoreCase("satya")){
            return Optional.of("Satya@gmail.com");
        }
        if (string.equalsIgnoreCase("Rahul")){
            return Optional.of("Rahul@gmail.com");
        }
        return Optional.empty();
    }

    public static void main(String[] args) {

        Optional<String> name = Optional.of("Satya");

        String email = null;

        Optional<String> result = Optional.ofNullable(email);

        String name1 = result.orElse("Not Found.");

        name.ifPresent(System.out::println);

        System.out.println(name1);

        OptionalPractice op = new OptionalPractice();
        String satya = op.findUserEmail("satya").orElse("Not Found.");
        String Rahul = op.findUserEmail("Rahul").orElse("Not Found.");
        System.out.println(satya);
        System.out.println(Rahul);
        String mandatory = op.findUserEmail("Unknown").orElseThrow();

    }
}