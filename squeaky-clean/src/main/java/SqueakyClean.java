
class SqueakyClean {
    static String clean(String identifier) {
        StringBuilder sb = new StringBuilder(identifier);
        StringBuilder sb2 = new StringBuilder();
        for(int i=0;i<sb.length();i++){
            
            if(sb.charAt(i)==' '){
                sb2.append('_');
            }
            else if(sb.charAt(i)=='\n' || sb.charAt(i)=='\t' || sb.charAt(i)=='\r' || sb.charAt(i)=='\b' || sb.charAt(i)=='\f' || sb.charAt(i)=='\u007F' || sb.charAt(i)=='\0'){
                sb2.append("CTRL");
            }
            else if(sb.charAt(i)=='-'){
         sb2.append(Character.toUpperCase(sb.charAt(i+1)));
         i++;
     }
                else if(Character.isDigit(sb.charAt(i)) ){
                    continue;
                }
             else{
                sb2.append(sb.charAt(i));
                }
            }
        String s = sb2.toString().replaceAll("\\d","").replaceAll("[α-ω]", "");
        StringBuilder sb3 = new StringBuilder(s);
        StringBuilder sb4 = new StringBuilder();
        
        for(int i=0;i<s.length();i++){
       if(Character.isUnicodeIdentifierPart(sb3.charAt(i))){
           sb4.append(sb3.charAt(i));
        
    }}
        return sb4.toString();
}
}
