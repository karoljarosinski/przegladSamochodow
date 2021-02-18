import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CarStationManager {
    private Scanner scanner = new Scanner(System.in);
    private Queue<Car> carQueue = new LinkedList<>();


    void mainLoop() {
        Option option;
        do {
            printOptions();
            System.out.println("Wybierz opcje: ");
            option = Option.createFromInt(scanner.nextInt());
            scanner.nextLine();
            switch (option) {
                case EXIT:
                    System.out.println("Zakończono działanie programu");
                    break;
                case ADD:
                    carQueue.offer(readAndCreateCar());
                    System.out.println("Dodano pojazd do kolejki");
                    break;
                case TAKE:
                    if (carQueue.isEmpty()) {
                        System.out.println("Brak samochodów w kolejce");
                    } else {
                        carQueue.poll();
                        System.out.println("Obsłużono kolejny pojazd");
                    }
                    break;
            }
        } while (option != Option.EXIT);
    }

    private Car readAndCreateCar() {
        System.out.println("Podaj rodzaj pojazdu:");
        String type = scanner.nextLine();
        System.out.println("Podaj markę pojazdu:");
        String brand = scanner.nextLine();
        System.out.println("Podaj model pojazdu:");
        String model = scanner.nextLine();
        System.out.println("Podaj rocznik pojazdu:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj stan licznika:");
        int odo = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj vin:");
        String vin = scanner.nextLine();
        return new Car(type, brand, model, year, odo, vin);
    }

    private void printOptions() {
        Option[] options = Option.values();
        for (Option option : options) {
            System.out.println(option);
        }
    }

    private enum Option {
        EXIT(0, "Wyjdź z programu"),
        ADD(1, "Dodaj samochód do kolejki"),
        TAKE(2, "Weź kolejny samochód z kolejki");

        int option;
        String description;

        Option(int option, String description) {
            this.option = option;
            this.description = description;
        }

        static Option createFromInt(int option) {
            return values()[option];
        }

        @Override
        public String toString() {
            return option + " " + description;
        }
    }
}
