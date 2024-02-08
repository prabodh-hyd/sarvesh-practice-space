class RotationalCipher {
    public int key;
    RotationalCipher(int shiftKey) {
        this.key=shiftKey;
    }

    String rotate(String data) {
        StringBuilder sb = new StringBuilder(data);
        StringBuilder sb2 = new StringBuilder();
        if(key>26){
            key%=26;
        }
        for(int i=0;i<sb.length();i++){
            if(!Character.isLetter(sb.charAt(i))){
                sb2.append(sb.charAt(i));
            }
            else if(Character.isLowerCase(sb.charAt(i))){
                sb2.append((char)('a'+(sb.charAt(i)-'a'+key)%26));
            }
            else if(Character.isUpperCase(sb.charAt(i))){
                sb2.append((char)('A'+(sb.charAt(i)-'A'+key)%26));
            }
            else{
                sb2.append(sb.charAt(i));
            }
        }
        return sb2.toString();
    }

}
