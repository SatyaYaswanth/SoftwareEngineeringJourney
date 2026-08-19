package Day13;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class EmployeeAnalytics {
static class Employee {
    String name;
    double salary;
    int exp;
    String department;

    public Employee(String name, double salary, int exp, String department) {
        this.name = name;
        this.salary = salary;
        this.exp = exp;
        this.department = department;
    }
}
    public static void main(String[] args){
        List<Employee> employees = List.of(
                new Employee("Rahul", 75000, 3, "IT"),
        new Employee("Amit", 55000, 2, "HR"),
        new Employee("Priya", 90000, 5, "IT"),
        new Employee("Ravi", 65000, 4, "Finance"),
        new Employee("Sneha", 85000, 6, "IT")
        );

        //Display the names of employees earning more than ₹70,000.
        employees.stream().filter(e->e.salary>70000).forEach(System.out::println);

        //Display employee names sorted by salary descending.
        employees.stream().sorted((e1,e2)->Double.compare(e2.salary, e1.salary)).map(e->e.name).forEach(System.out::println);

        //Calculate the total salary of all employees.
        double sum  = employees.stream().map(e->e.salary).reduce((double) 0, (a, b) -> a+b);
        System.out.println(sum);

        //Calculate the average salary.

        OptionalDouble avg = employees.stream().mapToDouble(e->e.salary).average();
        System.out.println(avg);

        //Count employees earning more than ₹60,000.
        long count = employees.stream().map(e->e.salary).filter(e->e>60000).count();
        System.out.println(count);

        //Find and print the highest-paid employee's name.
        Optional<Employee> highest = employees.stream()
                .max((e1, e2) -> Double.compare(e1.salary, e2.salary));

        highest.ifPresent(e -> System.out.println(e.name));

        //Check whether all employees have at least 2 years of experience.
        boolean isEligible = employees.stream()
                .allMatch(e -> e.exp >= 2);

        //Check whether any employee earns more than ₹100,000.
        boolean isAny = employees.stream()
                .anyMatch(e -> e.salary > 100000);

        System.out.println(isAny);

        List<String> itEmployees = employees.stream().filter(e->e.salary>70000).sorted((e1,e2)->Double.compare(e2.salary, e1.salary)).map(e->e.name.toUpperCase()).collect(Collectors.toList());



    }
}
