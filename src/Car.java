public class Car {
    int Gate;
    int Id;
    int ArriveTime;
    int ParkingTime;
    long waitStartTime = -1;

    Car(int gate, int id, int arriveTime, int parkingTime) {
        this.Gate = gate;
        this.Id = id;
        this.ArriveTime = arriveTime;
        this.ParkingTime = parkingTime;
    }
}
