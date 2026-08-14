package Day11;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo {
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("data/student1.txt", true);
        writer.write("Satya\n");
        writer.write("Rahul\n");
        writer.close();


    }


}
