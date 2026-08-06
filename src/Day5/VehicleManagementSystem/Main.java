package Day5.VehicleManagementSystem;
import Day5.VehicleManagementSystem.Model.Bike;
import Day5.VehicleManagementSystem.Model.Car;
import Day5.VehicleManagementSystem.Model.Truck;
import Day5.VehicleManagementSystem.Model.Vehicle;

public class Main {

    public static void main(String[] args) {

        Vehicle[] vehicles = {
                new Car(),
                new Bike(),
                new Truck()
        };

        for (Vehicle v : vehicles) {
            v.startEngine();
        }

    }
}