class TwelveDays {
    String[] num = {"","first","second","third","fourth","fifth","sixth","seventh","eighth","ninth","tenth","eleventh","twelfth"};
    String[] lyrics = {""," a Partridge in a Pear Tree."," two Turtle Doves, and a Partridge in a Pear Tree."," three French Hens,"," four Calling Birds,"," five Gold Rings,"," six Geese-a-Laying,"," seven Swans-a-Swimming,"," eight Maids-a-Milking,"," nine Ladies Dancing,"," ten Lords-a-Leaping,"," eleven Pipers Piping,"," twelve Drummers Drumming,"};
    String verse(int verseNumber) {
        StringBuilder sb = new StringBuilder();
        
            sb.append("On the "+num[verseNumber] +" day of Christmas my true love gave to me:");
            if(verseNumber==1){
                sb.append(lyrics[1]);
            }
           else if(verseNumber==2){
                sb.append(lyrics[2]);
            }
            else{
                for(int j=verseNumber;j>1;j--){
                    sb.append(lyrics[j]);
                }
            }
        
        return sb.append("\n").toString();
        }  

    String verses(int startVerse, int endVerse) {
        StringBuilder sb = new StringBuilder();
        for(int i=startVerse;i<=endVerse;i++){
        sb.append("On the "+num[i] +" day of Christmas my true love gave to me:");
            if(i==1){
                sb.append(lyrics[1]);
                sb.append("\n\n");
            }
           else if(i==2){
                sb.append(lyrics[2]);
                sb.append("\n\n");
            }
            else{
                for(int j=i;j>1;j--){
                    sb.append(lyrics[j]);
                }
                if(i==endVerse){
                    sb.append("\n");
                }else{
                
                sb.append("\n\n");}
            }
            
    } return sb.toString();
        }
    
    String sing()  { 
        return verses(1,12);
    }
}
