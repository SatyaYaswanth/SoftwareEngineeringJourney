package Day6.SmartHomeSystem.Model;

public class Fan implements SmartDevices{

    @Override
    public void turnOn() {
        System.out.println("Fan is on.");
    }

    @Override
    public void turnOff() {
        System.out.println("Fan is off.");
    }
}