
import java.util.*;

class DiamondPrinter {

    List<String> printToList(char a) {
    List<String> s = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        a=Character.toUpperCase(a);
int A = ((int)a - 64);
A=(A*2)-1;
        int spaces = (A/2);
        int stars = 1;
        char c= 'A';
        for(int i=0;i<A;i++){
            for(int j=0;j<spaces;j++){
                sb.append(" ");
            }
            for (int j=0;j<stars;j++){
                if(j==0 || j==stars-1) {
                    sb.append(c);

                }
                else {
                    sb.append(" ");
                }
            }

            for(int j=0;j<spaces;j++){
                sb.append(" ");
            }

            s.add(sb.toString());
            sb = new StringBuilder();  

            if(i<(A/2)){
                spaces--;
                stars+=2;
                c++;
            }else{
                spaces++;
                stars-=2;
                c--;
            }
            if(A%2==0){
            for(int k=0;k<spaces;k++){
                sb.append(" ");
            }
             sb.append(c);
            for(int k=0;k<spaces;k++){
                 sb.append(" ");
            }
        }
        }
            
return s;
        
    }

}
