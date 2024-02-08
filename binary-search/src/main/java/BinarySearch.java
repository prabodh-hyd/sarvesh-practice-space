import java.util.*;

class BinarySearch {
    List<Integer>AL = new ArrayList<Integer>();
    BinarySearch(List<Integer> items) {
        this.AL = items;
    
    }

    int indexOf(int item) throws ValueNotFoundException {
        
        int left=0;
        int right=AL.size()-1;

        while(left<=right){
            int mid = left + (right - left) / 2;
            if(AL.get(mid)==item){
                return mid;
            }
            else if(AL.get(mid)>item){
                right=mid-1;
            }
            else if(AL.get(mid)<item){
                
                left=mid+1;
            }
        }
        throw new ValueNotFoundException("Value not in array");
    }
}
