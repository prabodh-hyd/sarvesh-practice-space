import java.util.*;
public class PangramChecker {

    public boolean isPangram(String input) {

  
        
        String s2 = input.toLowerCase();
        HashSet<Character> HS = new HashSet<>();
        for (int i=0;i<s2.length();i++){
            char a = s2.charAt(i);
            if(Character.isLetter(a)){
                HS.add(a);
            }

        }
        return HS.size()==26;
    }
}

    
