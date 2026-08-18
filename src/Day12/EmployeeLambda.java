package Day12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeLambda {

    static class Employee {
        int id;
        String name;
        String department;
        double salary;
        int experience;

        public Employee(int id, String department, String name, double salary, int experience) {
            this.id = id;
            this.department = department;
            this.name = name;
            this.salary = salary;
            this.experience = experience;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    ", experience=" + experience +
                    '}';
        }
    }

    public static void main(String[] args){
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee(1, "IT", "Rahul", 45666, 3);
        Employee emp2 = new Employee(2, "DEV", "Hari", 456466, 3);
        Employee emp3 = new Employee(3, "DEV", "Manoj", 456566, 5);
        Employee emp4 = new Employee(4, "IT", "Bandi", 4566, 3);
        Employee emp5 = new Employee(5, "ANALYST", "Rahul", 5666, 7);
        Employee emp6 = new Employee(6, "IT", "Rahul", 45660, 3);
        Employee emp7 = new Employee(7, "ANALYST", "Rahul", 95666, 3);
        Employee emp8 = new Employee(8, "IT", "Rahul", 45666, 3);
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        employees.add(emp5);
        employees.add(emp6);
        employees.add(emp7);
        employees.add(emp8);

        employees.forEach(System.out::println);

        for(Employee emp : employees){
            if(emp.salary>4000){
                System.out.println(emp);
            }
        }

        for(Employee emp : employees){
            if(emp.experience>3){
                System.out.println(emp);
            }
        }

        for(Employee emp : employees){
            if(emp.department.equalsIgnoreCase("IT")){
                System.out.println(emp);
            }
        }

        employees.forEach(emp -> System.out.println(emp.name));

        Comparator<Employee> comparator =
                (e1, e2) -> Double.compare(e1.salary, e2.salary);

        employees.sort(
                (e1, e2) -> Double.compare(e2.salary, e1.salary)
        );

        System.out.println(employees);

        employees.sort(
                (e1, e2) -> Double.compare(e1.experience, e2.experience)
        );
        System.out.println(employees);

        employees.sort(
                (e1, e2) -> e1.name.compareTo(e2.name)
        );
        System.out.println(employees);

        employees.sort((e1, e2) -> {

            int result = Double.compare(e1.salary, e2.salary);

            if (result != 0) {
                return result;
            }

            int result1 = Integer.compare(e1.experience, e2.experience);

            if (result1 != 0) {
                return result1;
            }

            return e2.name.compareTo(e1.name);
        });

        System.out.println(employees);

    }
}
