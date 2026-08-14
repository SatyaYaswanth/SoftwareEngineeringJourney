package Day10.EmployeeManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;

public class EmployeeManagement {

    static List<Employee> empList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void menu(){
        System.out.println("1. Add Employee");
        System.out.println("2. Display Employees");
        System.out.println("3. Sort by Salary");
        System.out.println("4. Sort by Name");
        System.out.println("5. Sort by Experience");
        System.out.println("6. Sort by Department");
        System.out.println("7. Exit");
    }

    public static void main(String[] args){
     while(true){
         menu();
         System.out.print("Enter your Choice : ");
         int choice = sc.nextInt();
         switch (choice){
             case 1:
                 addEmployee();
                 break;
             case 2:
                 displayEmp();
                 break;
             case 3:
                 sortBySal();
                 break;
             case 4:
                 sortByName();
                 break;
             case 5:
                 sortByExp();
                 break;
             case 6:
                 sortByDept();
                 break;
             case 7:
                 return;
             default:
                 System.out.println("Invalid input.");
         }
     }

    }

    public static void addEmployee(){
        System.out.print("Enter id : ");
        int id = sc.nextInt();
        System.out.print("Enter name : ");
        String name = sc.next();
        System.out.print("Enter Department : ");
        String dept = sc.next();
        System.out.print("Enter salary : ");
        double salary = sc.nextDouble();
        System.out.print("Enter Experience : ");
        int exp = sc.nextInt();
        Employee emp1 = new Employee(id, name, dept, salary,exp);
        empList.add(emp1);
    }

    public static void displayEmp(){
        for(Employee item: empList){
            System.out.println(item);
        }
    }

    public static void sortByName(){
        Comparator<Employee> byName =
                Comparator.comparing(Employee::getName);
        empList.sort(byName);
    }
    public static void sortByExp(){
        Comparator<Employee> byExp =
                Comparator.comparing(Employee::getExperience);
        empList.sort(byExp);
    }
    public static void sortByDept(){
        Comparator<Employee> byDept =
                Comparator.comparing(Employee::getDepartment);
        empList.sort(byDept);
    }
    public static void sortBySal(){
        empList.sort(null);
    }

    public static void rankEmployees() {

        // your multi-level Comparator
        Comparator<Employee> ranking =
                Comparator.comparingDouble(Employee::getSalary)
                        .reversed()
                        .thenComparing(
                                Comparator.comparingInt(Employee::getExperience)
                                        .reversed()
                        )
                        .thenComparing(Employee::getName);
        // sort empList
        empList.sort(ranking);
        // display employees
        for (Employee item: empList){
            System.out.println(item);
        }
    }
}
