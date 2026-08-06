package Day6.SmartHomeSystem;

import Day6.SmartHomeSystem.Model.*;

public class Main {
    public static void main(String[] args){
        SmartDevices[] SD = {new AC(), new Fan(), new Light(), new Tv()};
        for (SmartDevices device : SD) {
            device.turnOn();
            device.turnOff();
        }
    }
}
