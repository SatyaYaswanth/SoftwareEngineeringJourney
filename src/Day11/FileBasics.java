package Day11;
import java.io.File;
import java.io.IOException;

public class FileBasics {
    public static void main(String[] args) throws IOException {

        File directory = new File("data");
        directory.mkdir();
        File file = new File("data/students1.txt");
        file.createNewFile();

        if (file.createNewFile()) {
            System.out.println("File created!");
        } else {
            System.out.println("File already exists.");
        }

        System.out.println(file.mkdir());
        System.out.println(file.exists());
        System.out.println(file.createNewFile());
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.length());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
    }
}
