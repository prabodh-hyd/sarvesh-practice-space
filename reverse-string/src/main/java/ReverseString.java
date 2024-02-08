class ReverseString {

    String reverse(String inputString) {
       /*  StringBuilder sb = new StringBuilder(inputString);
        StringBuilder sb2 = new StringBuilder();
        for(int i=sb.length()-1;i>=0;i--){
            sb2.append(sb.charAt(i));
        }
        return sb2.toString();  */
        StringBuilder sb = new StringBuilder(inputString);
        return sb.reverse().toString();
    }
  
}
