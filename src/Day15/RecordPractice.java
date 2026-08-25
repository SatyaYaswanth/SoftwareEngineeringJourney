package Day15;

import java.lang.invoke.TypeDescriptor;

public class RecordPractice {
    record UserResponse(
            int id,
            String name,
            String email,
            UserRole role){}

    enum UserRole{
        USER,
        ADMIN,
        CUSTOMER
    }

    record TaskResponse(
            int id,
            String title,
            Status status,
            Priority priority
    ){}

    enum Status{
        COMPLETED,
        IN_PROGRESS,
        TODO
    }

    enum Priority{
        HIGH,
        MEDIUM,
        LOW
    }

    UserResponse user = new UserResponse(3, "Rahul", "HVYVU0", UserRole.USER);

    public static void main(String[] args){
        UserResponse user = new UserResponse(3, "Rahul", "HVYVU0", UserRole.USER);
        System.out.println(user);

        TaskResponse task = new TaskResponse(44, "jbkj", Status.COMPLETED, Priority.HIGH);
        System.out.println(task);

    }
}
