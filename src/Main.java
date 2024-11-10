import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = ".idea/input.txt";
        List<Car> gate1 = new ArrayList<>();
        List<Car> gate2 = new ArrayList<>();
        List<Car> gate3 = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] lineSplit = line.split(",");
                int GateId = Integer.parseInt(lineSplit[0].split(" ")[1]);
                int CarId = Integer.parseInt(lineSplit[1].trim().split(" ")[1]);
                int ArriveId = Integer.parseInt(lineSplit[2].trim().split(" ")[1]);
                int ParkingId = Integer.parseInt(lineSplit[3].trim().split(" ")[1]);
                Car record = new Car(GateId, CarId, ArriveId, ParkingId);
                if (GateId == 1) {
                    gate1.add(record);
                } else if (GateId == 2) {
                    gate2.add(record);
                } else if (GateId == 3) {
                    gate3.add(record);
                }
            }
            Thread g1 = new Thread(new Gate(gate1));
            Thread g2 = new Thread(new Gate(gate2));
            Thread g3 = new Thread(new Gate(gate3));
            g1.start();
            g2.start();
            g3.start();
            Runtime.getRuntime().addShutdownHook(new Thread(ParkingLogger::printTotalCarsServed));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}