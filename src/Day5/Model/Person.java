package Day5.Model;

public class Person {

    private String name;
    private int age;
    private int phone;

    public Person(String name, int age, int phone){
        this.name=name;
        this.age=age;
        this.phone=phone;
        System.out.println("Created.");
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }
}
