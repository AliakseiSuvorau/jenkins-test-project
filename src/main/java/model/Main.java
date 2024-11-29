package model;

public class Main {
    public static void main(String[] args) {
        GarageImpl garage = new GarageImpl();

        Owner owner = new Owner("John", "Snow", 35);
        int ownerId = owner.getId();

        Car car = new Car(1, "Toyota", "Corolla", 200, 150, ownerId);

        garage.addNewCar(car, owner);
    }
}
