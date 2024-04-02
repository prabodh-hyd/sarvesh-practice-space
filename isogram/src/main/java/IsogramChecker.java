import java.util.HashSet;
class IsogramChecker {

    boolean isIsogram(String phrase) {
        phrase =phrase.toLowerCase();
        HashSet<Character> HS = new HashSet<Character>();
        
        for(int i=0;i<phrase.length();i++){
            if(phrase.charAt(i)==' ' || phrase.charAt(i)=='-'){
                continue;
            }
            if(!HS.add(phrase.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
