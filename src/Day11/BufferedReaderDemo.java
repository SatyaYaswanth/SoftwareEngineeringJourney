package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class BufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/students.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }

        }
    }
}
