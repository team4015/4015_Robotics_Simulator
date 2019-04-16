package Robot.components.digital.ports;

import Robot.components.digital.devices.AbstractDigitalDevice;

public class DIOPort {

     private final DIOPortNumbers PORT_NUMBER;

     private final float VOLTAGE_OUT_1 = 5.0f;

     private final float VOLTAGE_OUT_2 = 3.3f;

     private AbstractDigitalDevice deviceFor5V;

     private AbstractDigitalDevice deviceFor3V;

     public DIOPort(DIOPortNumbers PORT_NUMBER) {


        this.PORT_NUMBER = PORT_NUMBER;

     }

     public void connectDeviceTo5V(AbstractDigitalDevice deviceFor5V){
         this.deviceFor5V = deviceFor5V;
     }

     public void connectDeviceTo33V(AbstractDigitalDevice deviceFor3V){
         this.deviceFor3V = deviceFor3V;
     }

     public void signal5VOut(){
         if(deviceFor5V!=null) {
             deviceFor5V.actionOnInputVoltage();
         }
     }

     public void signal3VOut(){
         if(deviceFor3V!=null) {
             deviceFor3V.actionOnInputVoltage();
         }
     }



}
