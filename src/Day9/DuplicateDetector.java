package Day9;

import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

public class DuplicateDetector {
    public static void main(String[] args){
        HashSet<Integer> NumsSeen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        Boolean isRunning = true;
        int num;
        while(isRunning){
            System.out.print("Enter number(-1 to exit) : ");
            num = sc.nextInt();
            if(num==-1){
                isRunning=false;
                break;
            }
            if(NumsSeen.contains(num)){
                duplicates.add(num);
            }else{
                NumsSeen.add(num);
            }

        }

        System.out.print(duplicates);
    }
}
