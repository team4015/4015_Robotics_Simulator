package Robot.components.piston;

public class AirTank {

    private final float MAX_CAPACITY;

    private float currCapacity;

    public AirTank(float MAX_CAPACITY) {
        this.MAX_CAPACITY = MAX_CAPACITY;
        currCapacity = 0.0f;
    }

    public void fillAir(float amount){
        currCapacity+=amount;
    }


    public boolean requiresAir(){
        return (currCapacity > MAX_CAPACITY - MAX_CAPACITY*0.03f);
    }


    public void useAir(float amount){
        currCapacity-=amount;
    }



}
