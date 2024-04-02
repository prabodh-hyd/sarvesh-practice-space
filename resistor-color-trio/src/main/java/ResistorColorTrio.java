
import java.util.*;
class ResistorColorTrio {
    String label(String[] colors) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String[] COLOURS ={"black","brown","red","orange","yellow","green","blue","violet","grey","white"};
        int[] a = {0,1,2,3,4,5,6,7,8,9};
        HashMap<String,Integer> HM = new HashMap<String,Integer>();
        for(int i=0;i<10;i++){
          HM.put(COLOURS[i],a[i]);  
        }
        
        for(int i=0;i<2;i++){
            if(colors[0]=="black" && colors[1]=="black"){
                return "0 ohms";
            }
             if(colors[i].equals("black")){
                 if(i==0){
                     sb.append("");
                 }else{
            sb.append("0");}
        }
       else if(colors[i].equals("brown")){
sb.append("1");
        }
        else if(colors[i].equals("red")){
            sb.append("2");
        }
        else if(colors[i].equals("orange")){
            sb.append("3");
        }
        else if(colors[i].equals("yellow")){
            sb.append("4");
        }
        else if(colors[i].equals("green")){
            sb.append("5");
        }
        else if(colors[i].equals("blue")){
            sb.append("6");
        }
        else if(colors[i].equals("violet")){
            sb.append("7");
        }
        else if(colors[i].equals("grey")){
            sb.append("8");
        }
        else if(colors[i].equals("white")){
            sb.append("9");
        }
            else{
sb.append("");}
        }


        

        
            
            int ab= HM.get(colors[2]);

        
            for(int j=0;j<ab;j++){
            sb.append("0");}
    
        int count = 0;
        for(int i=sb.length()-1;i>=0;i--){
if(sb.charAt(i)=='0'){
count++;}
            else{
break;}}
    

if(count >= 3 && count < 6){
sb2.append(sb.substring(0, sb.length()-3)+" kiloohms");
    } 
        else if(count >= 6 && count < 9){
sb2.append(sb.substring(0, sb.length()-count)+" megaohms");
    }
       else if(count >= 9){
sb2.append(sb.substring(0, sb.length()-count)+" gigaohms");
    }
           else if(count==1){
               sb2.append(sb.substring(0,3)+" ohms");
           }
            else{
                sb2.append(sb.substring(0,sb.length()-count));
                sb2.append(" ohms");

} 
        
sb.append(" ohms");
            
        
        return sb2.toString();
    }
}






