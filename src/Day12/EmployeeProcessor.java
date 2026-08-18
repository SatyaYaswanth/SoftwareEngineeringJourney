package Day12;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EmployeeProcessor {
    int id;
    String name;
    String dept;
    double salary;
    int exp;

    public EmployeeProcessor(int id, String name, String dept, double salary, int exp) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", salary=" + salary +
                ", exp=" + exp +
                '}';
    }

    public static void main(String[] args){
        EmployeeProcessor emp1 = new EmployeeProcessor(
                1, "Ravi", "IT", 78000, 3);

        EmployeeProcessor emp2 = new EmployeeProcessor(
                2, "Priya", "HR", 55000, 6);

        EmployeeProcessor emp3 = new EmployeeProcessor(
                3, "Manoj", "IT", 95000, 7);

        EmployeeProcessor emp4 = new EmployeeProcessor(
                4, "Anjali", "DEV", 62000, 2);

        EmployeeProcessor emp5 = new EmployeeProcessor(
                5, "Kiran", "IT", 48000, 8);

        EmployeeProcessor emp6 = new EmployeeProcessor(
                6, "Sneha", "FINANCE", 72000, 5);

        EmployeeProcessor emp7 = new EmployeeProcessor(
                7, "Arjun", "DEV", 62000, 6);

        EmployeeProcessor emp8 = new EmployeeProcessor(
                8, "Divya", "IT", 95000, 4);

        EmployeeProcessor emp9 = new EmployeeProcessor(
                9, "Vikram", "HR", 45000, 2);

        EmployeeProcessor emp10 = new EmployeeProcessor(
                10, "Neha", "FINANCE", 72000, 5);

        ArrayList<EmployeeProcessor> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        employees.add(emp6);
        employees.add(emp7);
        employees.add(emp8);
        employees.add(emp9);
        employees.add(emp10);

        Consumer<EmployeeProcessor> print = System.out::println;
        employees.forEach(print);

        Predicate<EmployeeProcessor> highSalary =
                employee -> employee.salary>60000;
        Predicate<EmployeeProcessor> experienced =
                employee -> employee.exp>5;
        Predicate<EmployeeProcessor> isIT =
                employee -> employee.dept.equalsIgnoreCase("IT");

        for (EmployeeProcessor employee : employees) {
            if (highSalary.test(employee)) {
                System.out.println(employee);
            }
        }

        for (EmployeeProcessor employee : employees) {
            if (experienced.test(employee)) {
                System.out.println(employee);
            }
        }

        for (EmployeeProcessor employee : employees) {
            if (isIT.test(employee)) {
                System.out.println(employee);
            }
        }

        Function<EmployeeProcessor, String> getName =
                employee -> employee.name;

        for (EmployeeProcessor employee : employees) {
            System.out.println(getName.apply(employee));
        }

        Supplier<String> message =
                () -> "Employee processing completed!";

        System.out.println(message.get());

        employees.sort((e1, e2) -> {

            int result = Double.compare(e1.salary, e2.salary);

            if (result != 0) {
                return result;
            }

            int result1 = Integer.compare(e1.exp, e2.exp);

            if (result1 != 0) {
                return result1;
            }

            return e2.name.compareTo(e1.name);
        });

        employees.forEach(print);
    }
}
