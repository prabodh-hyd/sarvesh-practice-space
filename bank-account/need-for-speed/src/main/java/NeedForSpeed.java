class NeedForSpeed {
    private int speed;
    private static int batteryDrain;
    int distance=0;
    int battery=100;
    NeedForSpeed(int speed, int batteryDrain) {
        this.speed=speed;
        this.batteryDrain=batteryDrain;
    }

    public boolean batteryDrained() {
        if(battery>0){
            return false;
        }
        else{
            return true;
        }
    }

    public int distanceDriven() {
        return distance;
    }

    public void drive() {
        if(battery>=batteryDrain){
        distance+=speed;
        battery-=batteryDrain;}
        
        
    }

    public static NeedForSpeed nitro() {
        return new NeedForSpeed(50,4);
    }
}

class RaceTrack {
    private int distance;
    RaceTrack(int distance) {
        this.distance = distance;
    }

    public boolean tryFinishTrack(NeedForSpeed car) {
        while(!car.batteryDrained() && car.distance<distance){
            car.drive();}
        return car.distance>=distance;
    }
}
