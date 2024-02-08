import java.util.*;

class ProteinTranslator {

    List<String> translate(String rnaSequence) {
HashMap<String,String>HM = new HashMap<String,String>();
        HM.put("AUG","Methionine");
        HM.put("UUU","Phenylalanine");
         HM.put("UUC","Phenylalanine");
        HM.put("UUG","Leucine");
         HM.put("UUA","Leucine");
        HM.put("UCU","Serine");
        HM.put("UCC","Serine");
        HM.put("UCA","Serine");
        HM.put("UCG","Serine");
        HM.put("UAU","Tyrosine");
        HM.put("UAC","Tyrosine");
        HM.put("UGU","Cysteine");
        HM.put("UGC","Cysteine");
        HM.put("UGG","Tryptophan");
        HM.put("UAA","0");
        HM.put("UAG","0");
        HM.put("UGA","0"); 

        String s = HM.get(rnaSequence);
        LinkedList<String> LL = new LinkedList<String>();
       /* LL.offer(s);
        return LL; */
        if(rnaSequence.length()>9){
            rnaSequence=rnaSequence.substring(0,9);
        }
        if(rnaSequence.length()%3 !=0){
            throw new IllegalArgumentException("Invalid codon");
        }
        for(int i=0;i<(rnaSequence.length()-2);i+=3){
            if("0".equals(HM.get(rnaSequence.substring(i,i+3)))){
                return LL;
            }
            else if(Objects.isNull(HM.get(rnaSequence.substring(i,i+3)))){
throw new IllegalArgumentException("Invalid codon"); 
                }else{
            LL.offer(HM.get(rnaSequence.substring(i,i+3)));
        } }
       
    
         return  LL;
}
}