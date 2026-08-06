package Day6.SmartHomeSystem.Model;

public class Light implements SmartDevices{

    @Override
    public void turnOn() {
        System.out.println("Light is on.");
    }

    @Override
    public void turnOff() {
        System.out.println("Light is off.");
    }
}
