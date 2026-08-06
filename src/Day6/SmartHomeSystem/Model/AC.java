package Day6.SmartHomeSystem.Model;

public class AC implements SmartDevices{

    @Override
    public void turnOn() {
        System.out.println("AC is on.");
    }

    @Override
    public void turnOff() {
        System.out.println("AC is off.");
    }
}
