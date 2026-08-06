package Day5.Model;

public class Student extends Person{
    private int marks;

    public Student(String name, int age, int phone, int marks){
        super(name, age, phone);
        this.marks=marks;
    }

}
