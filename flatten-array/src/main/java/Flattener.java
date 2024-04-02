import java.util.*;

class Flattener {

    <T> List<T> flatten(List<T> list) {
        List<T> l = new ArrayList<>();
        for(T s : list){
            if(s != null){
                if(s instanceof List){
                    List<T> temp = flatten((List<T>)s);
                    l.addAll(temp);
                }
                else if(s != null){
                l.add(s);}
            }
            
        }
        return l;
    }

}