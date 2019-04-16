package Robot.components;

import Robot.components.digital.ports.DIOPort;
import Robot.components.digital.ports.DIOPortNumbers;
import Robot.components.lights.LED;
import Robot.components.pwm.port.PWMPort;
import Robot.components.pwm.port.PWMPortNumber;

import java.awt.*;

/**
 * This class provides the base simulation codebase
 * for a simple simulation of the roborio functionality
 */

public class Roborio {

    private final int RIO_ID;

    DIOPort port0;
    DIOPort port1;
    DIOPort port2;
    DIOPort port3;
    DIOPort port4;
    DIOPort port5;
    DIOPort port6;
    DIOPort port7;
    DIOPort port8;
    DIOPort port9;

    PWMPort port0PWM;
    PWMPort port1PWM;
    PWMPort port2PWM;
    PWMPort port3PWM;
    PWMPort port4PWM;
    PWMPort port5PWM;
    PWMPort port6PWM;
    PWMPort port7PWM;
    PWMPort port8PWM;
    PWMPort port9PWM;

    LED powerLED;
    LED statusLED;
    LED radioLED;
    LED commLED;
    LED modeLED;
    LED rslLED;

    boolean rioHasPower;

    public Roborio(int RIO_ID){
        this.RIO_ID = RIO_ID;
        System.out.printf("Roborio %d Iniitializing\n",RIO_ID);
        initializePorts();
        initLeds();
        rioHasPower = false;
    }


    public void nextCycleRio(){

    }































    private void initializePorts(){
        port0 = new DIOPort(DIOPortNumbers.PORT0);
        port1 = new DIOPort(DIOPortNumbers.PORT1);
        port2 = new DIOPort(DIOPortNumbers.PORT2);
        port3 = new DIOPort(DIOPortNumbers.PORT3);
        port4 = new DIOPort(DIOPortNumbers.PORT4);
        port5 = new DIOPort(DIOPortNumbers.PORT5);
        port6 = new DIOPort(DIOPortNumbers.PORT6);
        port7 = new DIOPort(DIOPortNumbers.PORT7);
        port8 = new DIOPort(DIOPortNumbers.PORT8);
        port9 = new DIOPort(DIOPortNumbers.PORT9);

        port0PWM = new PWMPort(PWMPortNumber.PORT0);
        port1PWM = new PWMPort(PWMPortNumber.PORT1);
        port2PWM = new PWMPort(PWMPortNumber.PORT2);
        port3PWM = new PWMPort(PWMPortNumber.PORT3);
        port4PWM = new PWMPort(PWMPortNumber.PORT4);
        port5PWM = new PWMPort(PWMPortNumber.PORT5);
        port6PWM = new PWMPort(PWMPortNumber.PORT6);
        port7PWM = new PWMPort(PWMPortNumber.PORT7);
        port8PWM = new PWMPort(PWMPortNumber.PORT8);
        port9PWM = new PWMPort(PWMPortNumber.PORT9);

    }

    private void initLeds(){
        powerLED = new LED("Power",Color.orange);
        statusLED = new LED("Status",Color.orange);
        radioLED = new LED("Radio",Color.orange);
        commLED = new LED("Comm",Color.orange);
        modeLED = new LED("Mode",Color.RED);
        rslLED = new LED("RSL",Color.orange);
    }



}
