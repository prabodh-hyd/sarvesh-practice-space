import java.util.*;

class HighScores {
private List<Integer> l = new ArrayList<>();
    public HighScores(List<Integer> highScores) {
        for(int i=0;i<highScores.size();i++){
            l.add(highScores.get(i));
        }
    }

    List<Integer> scores() {
        return l;
    }

    Integer latest() {
        return l.get(l.size()-1);
    }

    Integer personalBest() {
        List<Integer> a = new ArrayList<>();
        a = personalTopThree();
        return a.get(0);
    }

    List<Integer> personalTopThree() {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        
        for(int i=0;i<l.size();i++){
            l1.add(l.get(i));
        }
        Collections.sort(l1,Collections.reverseOrder());
        if(l.size()==1){
            l2.add(l.get(0));
            return l2;
        }
        else if(l.size()==2){
            return l1;
        }
        else{
            
        }
        for(int i=0;i<3;i++){
            l2.add(l1.get(i));
        }
        return l2;
    }

}
