package Day9;

import java.util.HashSet;
import java.util.Scanner;

public class UniqueNamesManager {

    public static void menu(){
        System.out.println("Enter your choice: ");
        System.out.println("1. Add Name");
        System.out.println("2. Remove Name");
        System.out.println("3. Search Name");
        System.out.println("4. Display Names");
        System.out.println("5. Exit");
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        HashSet<String> names = new HashSet<>();
        int choice;
        boolean isRunning = true;
        String temp;

        while(isRunning){
            menu();
            System.out.print("Enter your choice : ");
            choice= sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter name : ");
                    temp=sc.next();

                    if(!(names.contains(temp))) {
                        names.add(temp);
                    }else{
                        System.out.println("Name already exist.");
                    }
                    break;
                case 2:
                    System.out.print("Enter name to remove: ");
                    if(names.contains(temp=sc.nextLine())) {
                        names.remove(temp);
                        System.out.print("removed");
                    }
                    else{
                        System.out.print("Name not found");
                    }
                    break;
                case 3:
                    System.out.print("Enter name to search: ");
                    temp=sc.next();
                    if(names.contains(temp)) {
                        System.out.println("name found");
                    }
                    else{
                        System.out.println("Name not found");
                    }
                    break;
                case 4:
                    System.out.print(names);
                    break;
                case 5:
                    isRunning=false;
                    System.out.print("Exiting...");
                    break;
                default:
                    System.out.print("Invalid choice, try again!");
            }
        }
    }
}
