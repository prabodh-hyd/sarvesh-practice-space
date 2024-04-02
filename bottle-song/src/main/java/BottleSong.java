class BottleSong {

  String recite(int startBottles, int takeDown) {
    String[]  Digits ={"no","one","two","three","four","five","six","seven","eight","nine","ten"};
      StringBuilder sb = new StringBuilder();
       for(int i=0;i<takeDown;i++){
           if(startBottles!=1){
            sb.append( cap(Digits[startBottles]) +" green bottles hanging on the wall,\n");
           sb.append(cap(Digits[startBottles])+ " green bottles hanging on the wall,\n");}
           else{
               sb.append( cap(Digits[startBottles]) +" green bottle hanging on the wall,\n");
           sb.append(cap(Digits[startBottles])+ " green bottle hanging on the wall,\n");}
           
           sb.append("And if one green bottle should accidentally fall,\n");
           if(startBottles-1==1){
           sb.append("There'll be "+Digits[startBottles-1] +" green bottle hanging on the wall.\n");}
           else{
               sb.append("There'll be "+Digits[startBottles-1] +" green bottles hanging on the wall.\n");
}
           if(i<takeDown-1){
               sb.append("\n");
           }
          startBottles--;
       }
return sb.toString();
      }
  public static String cap(String s){
      return Character.toUpperCase(s.charAt(0))+s.substring(1);
  }
}