/*
public class Say {

    public String say(long number) {
        throw new UnsupportedOperationException("Delete this statement and write your own implementation.");
    }
}*/
public class Say {
    String[] DIGITS = {"","hundred","thousand","million","billion"};
    String[] LessThanTwenty = {"","one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen", "seventeen", "eighteen", "nineteen"};
    String[] TENS = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    public String say(long number) {
        
        if(number==0){
            return "zero";
        }
            else if(number<0 || number>999999999){
                throw new IllegalArgumentException();
            }  
        else if(number<20){
            return LessThanTwenty[(int)number].toString();
        }
            else if(number%10==0 && number<=90){
                return TENS[(int)number/10];
            }
        else if(number<100){
            
            return (TENS[(int)number/10]+"-" +LessThanTwenty[(int)number%10]).toString();
        }
        else if(number%100==0 && number<1000){
            return (LessThanTwenty[(int)number/100]+" "+DIGITS[1]);
        }
        else if(number<1000){
            return (LessThanTwenty[(int)number/100]+" "+DIGITS[1]+" "+TENS[(int)(number/10)%10]+"-"+LessThanTwenty[(int)number%10]).toString();
        }
        else if(number%1000 ==0 && number%10==0 && (number%100)/10==0 && (number%1000)/100==0 && number<=19000){
    return (LessThanTwenty[(int)number/1000]+" "+DIGITS[2]);}

            
        else if (number<10000) {
            return (LessThanTwenty[(int)number/1000]+" "+DIGITS[2]+" "+LessThanTwenty[(int)(number%1000)/100]+" "+DIGITS[1]+" "+TENS[(int)(number%100)/10]+"-"+LessThanTwenty[(int)number%10]).toString();
        }
      
            else if(number<=99000 && number%10==0 && (number%100)/10==0 && (number%1000)/100==0){
                return TENS[(int)number/10000]+LessThanTwenty[(int)(number%10000)/1000]+" "+DIGITS[3];
            }  
                else if(number<1000000 && number%10==0 && (number%100)/10==0 && (number%1000)/100==0 && (number%10000)/1000==0 ){
                    return LessThanTwenty[(int)number/100000]+DIGITS[1]+DIGITS[2];
                }
                else if(number<1000000 ){
                   return LessThanTwenty[(int)number/100000]+DIGITS[1]+TENS[(int)(number%100000)/10000]+LessThanTwenty[(int)(number%10000)/1000]+DIGITS[2]+say(number%1000);
                }
        else if(number==1000000){
            return  LessThanTwenty[1]+" "+DIGITS[3];
        }
            else if(number<19000000 && number%10==0 && (number%100)/10==0 && (number%1000)/100==0 && (number%10000)/1000==0 && (number%100000)/10000==0 && (number%1000000)/100000==0){
                return LessThanTwenty[(int)number/1000000]+" "+DIGITS[3];
            }
                else if(number<=19000000){
                    return LessThanTwenty[(int)number/1000000]+" "+DIGITS[3]+" "+say(number%100000);
                }
             /*       else if(number<=1000000000){
                    return LessThanTwenty[(int)number/1000000]+" "+DIGITS[3]+" "+say(number%100000);
                        } */
                    else if(number<=99000000 && number%10==0 && (number%100)/10==0 && (number%1000)/100==0 && (number%10000)/1000==0 && (number%100000)/10000==0 && (number%1000000)/100000==0){
                return say(number/1000000)+" "+DIGITS[3];
            }
                      else if(number<1000000000){
                    return say(number/10000000)+" "+DIGITS[3]+" "+say(number%1000000);}  
                          else if(number == 1000000000L){
                              return "one billion";
                          }
                        
        else{
            throw new IllegalArgumentException();
        }
    }
    
}


