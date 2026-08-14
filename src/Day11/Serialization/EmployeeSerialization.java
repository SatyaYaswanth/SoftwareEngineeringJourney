package Day11.Serialization;

import java.io.*;
import java.util.ArrayList;

public class EmployeeSerialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {



        ArrayList<Employee> employees = new ArrayList<>();
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("data/employees.dat"));

        Employee emp1 = new Employee(4, "Yash", "CSE", 67676, 8);
        employees.add(emp1);
        Employee emp2 = new Employee(5, "Ram", "CSE", 67676, 8);
        employees.add(emp2);
        Employee emp3 = new Employee(6, "Charan", "CSE", 67676, 8);
        employees.add(emp3);

        writer.writeObject(employees);

    }
}
