package Day15;

public class EnumPractice {

    enum TaskStatus {
        TODO,
        IN_PROGRESS,
        COMPLETED
    }

    enum UserRole {
        USER,
        ADMIN,
        DEVELOPER
    }

    enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public static void main(String[] args) {

        TaskStatus status = TaskStatus.COMPLETED;
        UserRole role = UserRole.USER;
        Priority priority = Priority.MEDIUM;

        System.out.println(status);
        System.out.println(role);
        System.out.println(priority);
    }
}