import java.util.Scanner;

public class ArrayCalculator {
    public static void displayArray(int[] arr){
        System.out.print("Array : ");
        for(int num: arr){
            System.out.print(num + " ");
        }
    }

    public static int sum(int[] arr){
        int temp=0;
        for(int num: arr){
            temp+=num;
        }
        return temp;
    }

    public static void avg(int[] arr){
        System.out.print("Average : " + (double) sum(arr)/arr.length);
    }

    public static int maximum(int[] arr){
        int max=arr[0];
        for(int num: arr){
            max = Math.max(max,num);
        }
        return max;
    }

    public static int minimum(int[] arr){
        int min=arr[0];
        for(int num: arr){
            min = Math.min(min,num);
        }
        return min;
    }

    public static void menu(){
        System.out.println("=====Array Calculator=====");
        System.out.println("1. Display Array");
        System.out.println("2. Sum");
        System.out.println("3. Average");
        System.out.println("4. Maximum");
        System.out.println("5. Minimum");
        System.out.println("6. Exit");
        System.out.println("Enter your choice : ");
    }

    public static void main(String[] args){
        int[] nums={10, 20, 30, 40, 50};
        boolean isrunning = true;
        int choice;
        Scanner sc = new Scanner(System.in);
        while(isrunning){
            menu();
            choice=sc.nextInt();
            switch (choice){
                case 1:
                    displayArray(nums);
                    break;
                case 2:
                    System.out.println("Sum : " + sum(nums));
                    break;
                case 4:
                    System.out.println("Max : " + maximum(nums));
                    break;
                case 5:
                    System.out.println("Min : " + minimum(nums));
                    break;
                case 3:
                    avg(nums);
                    break;
                case 6:
                    System.out.println("Thanks for using Calculator!");
                    isrunning=false;
                    break;
                default:
                    System.out.println("Invalid input, Try again");
            }
        }
        sc.close();
    }
}
