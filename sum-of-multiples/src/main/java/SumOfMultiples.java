import java.util.*;
class SumOfMultiples {
private HashSet<Integer> HS;
    SumOfMultiples(int number, int[] set) {
         HS = new HashSet<Integer>();
        for(int i=0;i<set.length;i++){
            if(set[i]==0){
                continue;
            }
            for(int j=1;j<number;j++){
                if(j%set[i]==0){
                    HS.add(j);
                }
            }
            
        }
        
    }

    int getSum() {
        int b=0;
        for(Integer a : HS){
            b+=(int)a;
        }
        return b;
    }

}
