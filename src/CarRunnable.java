public class CarRunnable implements Runnable {
    ParkingManager manager = ParkingManager.getInstance();
    Car ThreadCar;

    CarRunnable(Car car) {
        ThreadCar = car;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadCar.ArriveTime * 1000L);
            ParkingLogger.carArrival(ThreadCar , ThreadCar.ArriveTime);
            manager.Park(ThreadCar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
