//public class ArraysDemo {
//
//    public static void main(String[] args) {
//
//        int[] arr = {1, 2, 3, 4, 5};
//
//        System.out.println(arr[0]);
//        System.out.println(arr[1]);
//        System.out.println(arr[2]);
//        System.out.println(arr[3]);
//        System.out.println(arr[4]);
//
//        arr[2] = 300;
//
//        System.out.println("\nAfter Updating:");
//
//        System.out.println(arr[0]);
//        System.out.println(arr[1]);
//        System.out.println(arr[2]);
//        System.out.println(arr[3]);
//        System.out.println(arr[4]);
//
//        System.out.println("\nLength = " + arr.length);
//    }
//}


//public class ArraysDemo {
//
//    public static void main(String[] args) {
//
//        int[] arr = {10, 20, 30, 40, 50};
//        for(int i=0;i<arr.length;i++){
//            System.out.print(arr[i] + " ");
//        }
//        for(int i=0;i<arr.length;i++){
//            arr[i]=arr[i]*2;
//        }
//        System.out.print("\nUpdated array : ");
//        for(int i=0;i<arr.length;i++){
//            System.out.print(arr[i] + " ");
//        }
//    }
//}

public class ArraysDemo {
    public static void displayArray(int[] arr){
        for(int num: arr){
            System.out.print(num + " ");
        }
    }
    public static int findSum(int[] arr){
        int sum=0;
        for(int num: arr){
            sum+=num;
        }
        return sum;
    }
    public static void main(String[] args) {

        int[] arr = {10, 20, 30, 40, 50};
        displayArray(arr);
        System.out.print("\nSum of Array : " + findSum(arr));

    }
}