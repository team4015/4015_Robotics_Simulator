package Robot.components.pwm.port;

import Robot.components.pwm.PWMMotorController;

public class PWMPort {

    private final PWMPortNumber portID;


    private PWMMotorController outDevice;

    public PWMPort(PWMPortNumber portID) {
        this.portID = portID;
    }

    public void setOutDevice(PWMMotorController outDevice){
        this.outDevice = outDevice;
    }

    public void pulseDevice(){

    }



}
