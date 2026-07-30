import java.util.Scanner;

public class MarksAnalyzer {
    public static void menu(){
        System.out.println("====Mark Analyzer====");
        System.out.println("1.Total Marks Obtained.");
        System.out.println("2.Average");
        System.out.println("3.Highest Mark");
        System.out.println("4.Lowest Mark");
        System.out.println("5.P/F");
        System.out.println("6.Grade Distribution.");
        System.out.println("7.Exit.");

    }

    public static int totalMarks(int[] marks){
        int sum=0;
        for(int i: marks){
            sum+=i;
        }
        return sum;
    }

    public static double averageMarks(int[] marks){
        return (double) totalMarks(marks)/marks.length;
    }

    public static int highestMarks(int[] marks){
        int max = marks[0];
        for(int i: marks){
            if(i>max){
                max=i;
            }
        }
        return max;
    }

    public static int lowestMarks(int[] marks){
        int min = marks[0];
        for(int i: marks){
            if(i<min){
                min=i;
            }
        }
        return min;
    }

    public static void pfcount(int[] marks){
        int p=0;
        int f=0;
        for(int i: marks){
            if(i<35){
                f++;
            }else{
                p++;
            }
        }
        System.out.println("P count : " + p + "\nF count : " + f);
    }

    public static void grade(int[] marks){
        totalMarks(marks);
    }


    public static void main(String[] args) {
        int[] marks= {56, 78, 34, 58, 54};
        Scanner sc = new Scanner(System.in);
        boolean isRunning=true;
        int choice;
        while(isRunning){
            menu();
            System.out.print("Enter your choice : ");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Total Marks : " + totalMarks(marks));
                    break;
                case 2:
                    System.out.println("Average Marks : " + averageMarks(marks));
                    break;
                case 3:
                    System.out.println("Highest Marks : " + highestMarks(marks));
                    break;
                case 4:
                    System.out.println("Lowest Marks : " + lowestMarks(marks));
                    break;
                case 5:
                    pfcount(marks);
                    break;
                case 6:
                    grade(marks);
                    break;
                case 7:
                    isRunning=false;
                    break;

            }
        }


    }
}




