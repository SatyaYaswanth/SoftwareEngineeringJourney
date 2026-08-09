package Day9;

import java.util.HashMap;
import java.util.Scanner;

public class WordFrequencyFinder {
    public static void main(String[] args){
        HashMap<String, Integer> counter = new HashMap<>();
        String statement;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a statement : ");
        statement = sc.nextLine();

        String[] words = statement.split(" ");

        for (String word : words) {
            System.out.print(word);
            if(counter.containsKey(word)){
                counter.put(word, counter.get(word)+1);
            }else{
                counter.put(word, 1);
            }
        }

        System.out.print(counter);

    }
}
