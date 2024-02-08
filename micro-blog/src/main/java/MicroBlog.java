/*class MicroBlog {
    public String truncate(String input) {
        StringBuilder sb = new StringBuilder();
        int j=0;
        
             while(j<5){
                sb.append(input.charAt(j));
                j++;
                 }
                 return sb.toString();
                 
        
        
    }
}*/
class MicroBlog {
    public  String truncate(String input) {
        if(input==null || input.equals("")){
            return input;
        }
        int[] a = input.codePoints().toArray();
        int[] b = new int[Math.min(5,a.length)];
        System.arraycopy(a,0,b,0,b.length);
        return new String(b,0,b.length);
    }
}
    


 