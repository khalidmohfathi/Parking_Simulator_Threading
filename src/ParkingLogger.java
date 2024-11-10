public class ParkingLogger {
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static int carsInLot = 0;
    public static int totalVehiclesServed = 0;

    private static String parkingSpotsUpdate() {
        return String.format(" (Parking Status: %d spots occupied)", carsInLot);
    }

    public static void carArrival(Car vehicle, int arrivalTime) {
        System.out.println(
                ANSI_BLUE + String.format(
                        "Car %d from Gate %d arrived at time %d",
                        vehicle.Id,
                        vehicle.Gate,
                        arrivalTime) + ANSI_RESET);
    }

    public static void carParked(Car vehicle, long waitingTime) {
        carsInLot++;
        totalVehiclesServed++;

        String waitingTimeMessage = waitingTime > 0
                ? String.format(" after waiting for %d units of time", waitingTime)
                : "";

        System.out.println(
                ANSI_GREEN + String.format(
                        "Car %d from Gate %d parked%s.",
                        vehicle.Id,
                        vehicle.Gate,
                        waitingTimeMessage) + ANSI_RESET + parkingSpotsUpdate());
    }

    public static void carLeft(Car vehicle, int parkingTime) {
        carsInLot--;
        System.out.println(
                ANSI_RED + String.format(
                        "Car %d from Gate %d left after %d units of time.",
                        vehicle.Id,
                        vehicle.Gate,
                        parkingTime) + ANSI_RESET + parkingSpotsUpdate());
    }

    public static void carWaiting(Car vehicle) {
        System.out.println(
                ANSI_YELLOW + String.format("Car %d from Gate %d waiting for a spot.", vehicle.Id, vehicle.Gate) + ANSI_RESET);
    }

    public static void printTotalCarsServed() {
        System.out.println("Total Cars Served: " + totalVehiclesServed);
    }
}