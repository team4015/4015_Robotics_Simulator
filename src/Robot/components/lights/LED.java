package Robot.components.lights;

import java.awt.*;

public class LED {

    Color LEDColor;

    boolean ledOn;

    String ledName;

    public LED(String ledName,Color LEDColor) {
        this.LEDColor = LEDColor;
        ledOn = false;
        this.ledName = ledName;
    }


    public void turnLedOn(){
        ledOn = true;
    }

    public void turnLedOff(){
        ledOn = false;
    }




}
