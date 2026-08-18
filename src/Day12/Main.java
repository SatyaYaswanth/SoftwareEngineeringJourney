package Day12;

public class Main {
    public static void main(String[] args){
        LambdaBasics lb = new LambdaBasics();

        Greeting greet = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.print("Hello " + name);
            }
        };
    }
}
