public class ElonsToyCar {
    public  int a=0;
    public  int b=100;
    public static ElonsToyCar buy() {
        return new ElonsToyCar();
    }

    public String distanceDisplay() {
        if(a<=2000){
        return "Driven "+a+" meters";}
        else{
            return "Driven 2000 meters";
        }
    }

    public String batteryDisplay() {
        if(b>0){
        return "Battery at "+b+"%";}
        else{
            return "Battery empty";
        }
    }

    public void drive() {
        a+=20;
        if(b>0){
        b--;
            }
    }
}
