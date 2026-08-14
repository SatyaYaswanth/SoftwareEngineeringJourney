package Day11;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedWriterDemo  {
    public static void main(String[] args) throws IOException{
        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter("data/students.txt", true)
                );
        writer.write("Student details");
        writer.newLine();
        writer.write("----------------");
        writer.newLine();
        writer.write("Satya");
        writer.newLine();
        writer.write("Rahul");
        writer.newLine();
        writer.flush();
        writer.close();
    }
}
