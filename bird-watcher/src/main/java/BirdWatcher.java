
class BirdWatcher {
    private final int[] birdsPerDay;

    public BirdWatcher(int[] birdsPerDay) {
        this.birdsPerDay = birdsPerDay.clone();
    }

    public int[] getLastWeek() {
    
        return birdsPerDay.clone();
    }  

    public int getToday() {
        int a = birdsPerDay.length;
        return birdsPerDay[a-1];
    }  

    public void incrementTodaysCount() {
        int a = birdsPerDay.length;
        birdsPerDay[a-1]+=1;
    }

    public boolean hasDayWithoutBirds() {
        int a=0;
        for(int i=0;i<birdsPerDay.length;i++){
            if(birdsPerDay[i]==0){
                a++;
            }
            
        }
       if(a==0) {
           return false; 
        }else{
           return true;
        }
        
    }

    public int getCountForFirstDays(int numberOfDays) {
        
        if(numberOfDays<=birdsPerDay.length){
        int a=0;
        for(int i=0;i<numberOfDays;i++){
            a+=birdsPerDay[i];
        }
        return a;
    }
    else{
        numberOfDays = birdsPerDay.length;
       return getCountForFirstDays(numberOfDays);
    }}

    public int getBusyDays() {
        int a=0;
        for(int i=0;i<birdsPerDay.length;i++){
            if(birdsPerDay[i]>=5){
                a++;
            }
        }
        return a;
        
    }
}
