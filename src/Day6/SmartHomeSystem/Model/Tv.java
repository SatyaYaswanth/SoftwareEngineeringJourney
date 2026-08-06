package Day6.SmartHomeSystem.Model;

public class Tv implements SmartDevices{

    @Override
    public void turnOn() {
        System.out.println("TV is on.");
    }

    @Override
    public void turnOff() {
        System.out.println("TV is off.");
    }
}
