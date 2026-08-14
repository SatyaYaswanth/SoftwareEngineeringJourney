package Day11.Serialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationDemo {
    public static void main(String[] args) throws IOException {
        Employee emp = new Employee(1, "sd", "CSE", 435353, 3);
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("data/employee.dat"))) {

            // write the Employee object
            writer.writeObject(emp);

        }
    }
}
