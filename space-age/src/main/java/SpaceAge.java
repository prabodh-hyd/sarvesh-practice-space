class SpaceAge {
private double seconds;
    SpaceAge(double seconds) {
        
        this.seconds = seconds;
    }

    double getSeconds(int a) {
        return (double)a*(3600*24*365.25);
    }

    double onEarth() {
        return (double) seconds/(3600*24*365.25);
    }

    double onMercury() {
        double period = 0.2408467 * 31557600 ;
        
        return (double)(seconds /period);
        
    }

    double onVenus() {
         double period = 0.61519726 * 31557600 ;
        
        return (double)seconds / period;
    }

    double onMars() {
         double period = 1.8808158 * 31557600 ;
        
        return (double)seconds / period;
    }

    double onJupiter() {
        double period = 11.862615 * 31557600 ;
        
        return (double)seconds / period;
    }

    double onSaturn() {
         double period = 29.447498 * 31557600 ;
        
        return (double)seconds / period;
    }

    double onUranus() {
        double period = 84.016846 * 31557600 ;
        
        return (double) seconds / period;
    }

    double onNeptune() {
        double period = 164.79132 * 31557600 ;
        
        return (double)seconds / period;
    }

}
