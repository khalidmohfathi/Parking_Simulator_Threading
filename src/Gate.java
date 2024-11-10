import java.util.List;

public class Gate implements Runnable {

    List<Car> carList;

    Gate(List<Car> cars) {
        carList = cars;
    }

    @Override
    public void run() {
        try {
            for (Car car : carList) {
                Thread t = new Thread(new CarRunnable(car));
                t.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
