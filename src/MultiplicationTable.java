public class MultiplicationTable {
    public static void main(String[] args){
        final int MAX_TABLE = 10;
        final int MAX_MULTIPLIER = 10;
        for(int i=2; i<=MAX_TABLE;i++){
            for(int j=1; j<=MAX_MULTIPLIER;j++){
                System.out.println(i + "*" + j + "=" + (i*j));
            }
            System.out.println();
        }
    }
}
