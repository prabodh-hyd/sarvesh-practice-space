abstract class Fighter {

    boolean isVulnerable() {
        return false;
    }

    abstract int damagePoints(Fighter fighter);

}

class Warrior extends Fighter {

    @Override
    public String toString() {
        return "Fighter is a Warrior";
    }

    @Override
    int damagePoints(Fighter fighter) {
        if(fighter.isVulnerable()){
            return 10;
        }else{
            return 6;
        }
    }
}

class Wizard extends Fighter {
    public int a=0;
public String toString() {
        return "Fighter is a Wizard";
    }

    @Override
    boolean isVulnerable() {
        if(a==0){
            return true;
        }else{
        return false;}
    }

    @Override
    int damagePoints(Fighter fighter) {
        if(a==0){
            return 3;
        }
        else{
            return 12;
        }
    }

    void prepareSpell() {
        
        a++;
    }

}
