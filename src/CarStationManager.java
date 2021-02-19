import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CarStationManager {
    private Scanner scanner = new Scanner(System.in);
    private Queue<Car> carQueue = new LinkedList<>();
    private static final String FILE_NAME = "carQueue.csv";

    void mainLoop() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            System.out.println("Samochody oczekujące w kolejce: ");
            carQueue = readFile(FILE_NAME);
            System.out.println(carQueue);
        }

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
        for (Car car : carQueue) {
            System.out.println(car);
        }
        if (carQueue.isEmpty()) {
            file.deleteOnExit();
        } else {
            writeQueueToFile(carQueue, FILE_NAME);
        }
    }

    private Queue<Car> readFile(String fileName) {
        Queue<Car> cars = new LinkedList<>();
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(";");
                int year = Integer.parseInt(split[3]);
                int odo = Integer.parseInt(split[4]);
                cars.offer(new Car(split[0], split[1], split[2], year, odo, split[5]));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie udało się odczytać pliku");
        }
        return cars;
    }


    private void writeQueueToFile(Queue<Car> carQueue, String fileName) {
        if (!carQueue.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                for (Car car : carQueue) {
                    writer.write(car.toCsv());
                    writer.newLine();
                }
                writer.close();
                System.out.println("Zapisano pozostałe samochody do pliku");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
