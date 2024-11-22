import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ParkingManager {
    private static ParkingManager instance;
    private static final int MaxSlots = 4;
    private int OccupiedSlots = 0;
    private final MySemaphore semaphore;
    private final Queue<Car> CarQueue = new LinkedList<>();

    private ParkingManager() {
        semaphore = new MySemaphore(MaxSlots, true);
    }

    public static synchronized ParkingManager getInstance() {
        if (instance == null) {
            instance = new ParkingManager();
        }
        return instance;
    }

    public void Park(Car car) {
        try {
            if (semaphore.tryAcquire() && OccupiedSlots < MaxSlots) {
                OccupiedSlots++;
                long waitingTime = car.waitStartTime == -1 ? 0 : (System.currentTimeMillis() - car.waitStartTime) / 1000;
                ParkingLogger.carParked(car , waitingTime);
                Thread.sleep(car.ParkingTime * 1000L);
                Leave(car);
            } else {
                Wait(car);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Leave(Car car) {
        try {
            OccupiedSlots--;
            ParkingLogger.carLeft(car, car.ParkingTime);
            semaphore.release();
            if (!CarQueue.isEmpty()) {
                Park(CarQueue.poll());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void Wait(Car car) {
        try {
            car.waitStartTime = System.currentTimeMillis();
            CarQueue.add(car);
            ParkingLogger.carWaiting(car);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
