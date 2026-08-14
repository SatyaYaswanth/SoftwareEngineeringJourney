package Day11.Serialization;

import java.io.*;

public class DeserializationDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("data/employee.dat"))) {
            Employee emp = (Employee) reader.readObject();
            System.out.println(emp);
        }
    }
}
