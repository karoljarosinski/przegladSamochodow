public class Car {
    private String type;
    private String brand;
    private String model;
    private int year;
    private int odo;
    private String vin;

    public Car(String type, String brand, String model, int year, int odo, String vin) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.odo = odo;
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Pojazd " + type + ", Marki " + brand + ", model " + model + ", Numer VIN: " + vin;
    }
}
