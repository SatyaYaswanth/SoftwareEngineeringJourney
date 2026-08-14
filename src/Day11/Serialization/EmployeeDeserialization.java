package Day11.Serialization;

import java.io.*;
import java.util.ArrayList;

public class EmployeeDeserialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("data/employees.dat"));

        ArrayList<Employee> emp = (ArrayList<Employee>) reader.readObject();
        System.out.println(emp);
    }
}
