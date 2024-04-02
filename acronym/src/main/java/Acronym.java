class Acronym {
    private String s;
        Acronym(String phrase) {
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(phrase.charAt(0)));
            for(int i=1;i<phrase.length();i++){
                if(phrase.charAt(i)==' ' || phrase.charAt(i)=='-'){
    
    if(Character.isLetter(phrase.charAt(i+1))){
                    sb.append(Character.toUpperCase(phrase.charAt(i+1)));}
                    else if(Character.isLetter(phrase.charAt(i+2))){
                   sb.append(Character.toUpperCase(phrase.charAt(i+2)));
           //Okavela space,hifen okesari vaste..loop will run twice...to overcome that...we need to increase value of i.             
                        i++;
                    }
                }
            }
            s=sb.toString();
        }
    
        String get() {
            return s;
        }
    
    }
    