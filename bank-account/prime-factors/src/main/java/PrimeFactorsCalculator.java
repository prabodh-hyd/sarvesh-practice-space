import java.util.*;

class PrimeFactorsCalculator {

    List<Long> calculatePrimeFactorsOf(long number) {
        List<Long> AL = new ArrayList<Long>();
        if(number<2){
            return AL;
        }
        
            for(long i=2;i<=(long)Math.sqrt(number);i++){
            while(isprime(i) && number%i==0){
                
                   number = number/i;
    
                    AL.add(i);
                
            }
            
        }
        if(number>1){
            AL.add(number);
        }
        return AL;
    }
public static boolean isprime(long number){

    int c=0;
    for(long i=1;i<=Math.sqrt(number);i++){
        if(number%i==0){
            c++;
        }
    }
    return c==1;
}
}