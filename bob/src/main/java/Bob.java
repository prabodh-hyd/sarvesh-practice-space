class Bob {

    String hey(String input) {
        input = input.trim();
          if(input.equals("") || input.trim().isEmpty()){
            return "Fine. Be that way!";
        }
        
         if(input.endsWith("?") && input.length()>1 && !input.trim().isEmpty()){
             
            if(input.equals(input.toUpperCase()) && input.matches(".*[a-zA-Z].*") ){
            return "Calm down, I know what I'm doing!";
        }
                
             else if(input.endsWith("?")){
                 return "Sure.";
             }
                 
             else{
                 return "Sure";
             }
             
        }
              if(input.equals(input.toUpperCase()) && input.length()>1 && !input.trim().isEmpty() && !input.matches("[\\d-\\d,\\s]+")){
                 return "Whoa, chill out!";
             }
       
        else{
            return "Whatever.";
        }
    }
}