import java.util.*;

class Sieve {
    List<Integer> ll = new LinkedList<Integer>();
    Sieve(int maxPrime) {
        for(int i=1;i<=maxPrime;i++){
            int a=0;
            for(int j=1;j<=maxPrime;j++){
                if((i%j)==0){
                    a++;
                }
            }
            if(a==2){
                ll.add(i);
            }
        }
    }

    List<Integer> getPrimes() {
        return ll;
    }
}
