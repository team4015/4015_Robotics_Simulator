package mainEngine.callbacks.joystick;

import java.nio.FloatBuffer;

import org.lwjgl.glfw.*;
import mainEngine.callbacks.*;

public class Joystick implements updateCallback{

    FloatBuffer joystickAxis;

    private float currentJoyXPos;
    private float currentJoyYPos;
    private float currentJoyZPos;

    private float changeXAxis;
    private float changeYAxis;
    private float changeZAxis;


    int joystickID;

    public Joystick(int joystickID){
        this.joystickID = joystickID;
        CallbackHandler.registerUpdateCallback(this);
    }

    @Override
    public void update() {
       joystickAxis =  GLFW.glfwGetJoystickAxes(joystickID);

       if(joystickAxis != null) {
           changeXAxis = currentJoyXPos - joystickAxis.get(0);
           changeYAxis = currentJoyXPos - joystickAxis.get(1);
           changeZAxis = currentJoyZPos - joystickAxis.get(2);

           currentJoyXPos = joystickAxis.get(0);
           currentJoyYPos = joystickAxis.get(1);
           currentJoyZPos = joystickAxis.get(2);
       }

    }

    public float getCurrentJoyXPos() {
        return currentJoyXPos;
    }

    public float getCurrentJoyYPos() {
        return currentJoyYPos;
    }

    public float getCurrentJoyZPos() {
        return currentJoyZPos;
    }

    public float getChangeXAxis() {
        return changeXAxis;
    }

    public float getChangeYAxis() {
        return changeYAxis;
    }

    public float getChangeZAxis() {
        return changeZAxis;
    }
}
