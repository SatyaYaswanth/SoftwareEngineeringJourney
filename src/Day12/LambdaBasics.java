package Day12;

public class LambdaBasics {

    public static void main(String[] args) {

//        Greeting g = name ->
//                System.out.println("Hello, " + name + "!");
//
//
//        Greeting g1 = name ->
//                System.out.println("Welcome to " + name);
//
//
//        g.greet("Satya");
//        g1.greet("CodePilot");

        Calculator addition = (a, b)-> (a+b);
        Calculator subtraction = (a, b)-> a-b;
         Calculator multiplication = (a, b)-> a*b;
        Calculator division = (a, b)-> a/b;

        double add = addition.calculate(2,3);
        double sub = subtraction.calculate(2,3);
        double mul = multiplication.calculate(2,3);
        double div = division.calculate(2,3);

System.out.println("add : " + add + "sub : " + sub + "mul : " + mul + "div : " + div);

    }
}