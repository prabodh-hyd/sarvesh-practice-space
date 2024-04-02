public class Hamming {
    private String leftStrand;
    private String rightStrand;
    public Hamming(String leftStrand, String rightStrand) {
        if( rightStrand.length()!=leftStrand.length() && (leftStrand.isEmpty() || rightStrand.isEmpty())){
    throw new IllegalArgumentException("strands must be of equal length");}
        
        if(rightStrand.length()==leftStrand.length()){
        this.leftStrand=leftStrand;
        this.rightStrand=rightStrand;
            }
    else{
        throw new IllegalArgumentException("strands must be of equal length");
    }
    }
    

    public int getHammingDistance() {
        

       if(rightStrand.isEmpty() && leftStrand.isEmpty() ){
           return 0;
       }
        int a=0;
         
            
        for(int i=0;i<leftStrand.length();i++){
            
            if(leftStrand.charAt(i)!=rightStrand.charAt(i)){
                a++;
            }
        }
            
        
        return a;
    }
}
