class Darts {
    int score(double xOfDart, double yOfDart) {
        double a = Math.sqrt((xOfDart * xOfDart) + yOfDart * yOfDart);
        if(a<=1){
            return 10;
        }
            else if(a<=5){
                return 5;
            }
                else if(a<=10){
                    return 1;
                }
        else{
return 0;}
        
    }
}

