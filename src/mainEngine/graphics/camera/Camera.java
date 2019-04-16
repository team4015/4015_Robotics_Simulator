package mainEngine.graphics.camera;

import mainEngine.callbacks.joystick.Joystick;
import mainEngine.callbacks.mouse.Mouse;
import org.lwjgl.glfw.*;
import org.joml.*;



import mainEngine.callbacks.*;

import mainEngine.timer.*;
import org.lwjgl.system.CallbackI;
import net.java.games.input.*;
import net.java.games.input.Component.Identifier.Key;


import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class Camera implements updateCallback,KeyboardCallback {

    private static Camera cameraInstance = new Camera();

    public static Camera getInstance(){
        return cameraInstance;
    }

    private long windowID;

    SimpleDeltaTimer timer;

    Joystick joy1 = new Joystick(GLFW.GLFW_JOYSTICK_1);

    private boolean joyStickEnabled = false;

    Matrix4f viewMatrix;

    Vector3f cameraPosition;

    Vector3f cameraTarget;

    Vector3f cameraDirectionVector;

    Vector3f upPosition;



    boolean recalcRequired = false;

    //Variables for euler angles
    float pitch, yaw;



    private Camera() {
       // initializeCallbacks();
        keysToBeCalledFor.addAll(Arrays.asList(Key.W,Key.S,Key.A,Key.D,Key.R,Key.F));
        cameraPosition   = new Vector3f(0.0f,1.0f,3.0f);
        cameraTarget = new Vector3f(1.0f,0.0f,0.0f);
        cameraDirectionVector = new Vector3f(new Vector3f(cameraPosition).sub(cameraTarget));
        upPosition = new Vector3f(0.0f,1.0f,0.0f);
        viewMatrix = new Matrix4f().lookAt(cameraPosition,new Vector3f(cameraPosition).add(cameraDirectionVector),upPosition);
        timer = new SimpleDeltaTimer();
        timer.startTimerUse();
    }


    public void setWindowID(long windowID){
        this.windowID = windowID;
        initializeCallbacks();
    }

    private void initializeCallbacks(){
        KeyboardHandler.getInstance().registerCallback(this);
        CallbackHandler.registerUpdateCallback(this);

    }

    private void calculatePitch(double dy,boolean mousePressed){
        if(mousePressed) {
            float pitchChange = (float) dy * 0.1f;
            pitch += pitchChange;
            pitch = clamp(-89, 89, pitch);
        }
    }

    private void calculateYaw(double dx,boolean mousePressed){
        if(mousePressed) {
            float yawChange = (float) dx * 0.1f;
            yaw += yawChange;
        }
    }

    private void calculateDirectionVector(){
        Vector3f newDirection = new Vector3f();
        newDirection.x = (float) (Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)));
        newDirection.y = (float) (Math.sin(Math.toRadians(pitch)));
        newDirection.z = (float) (Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)));
        newDirection.normalize();
        cameraDirectionVector = new Vector3f(newDirection);
        recalcRequired = true;
    }



    @Override
    public void keyPressed(Key key) {
       // System.out.println("Key pressed");
        recalcRequired = true;
        if(key == Key.S){
            Vector3f tempVector = new Vector3f(cameraDirectionVector);
            tempVector.mul(2.5f * (float)timer.getTimeElapsed());
           // tempVector.normalize();
            cameraPosition = cameraPosition.sub(tempVector);
        }
        if(key == Key.W){
            Vector3f tempVector = new Vector3f(cameraDirectionVector);
            tempVector.mul(2.5f * (float)timer.getTimeElapsed());
           // tempVector.normalize();
            cameraPosition = cameraPosition.add(tempVector);
        }

        if(key == Key.A){
            Vector3f tempVector = new Vector3f(cameraDirectionVector);
            tempVector.cross(upPosition);
            tempVector.mul(2.5f * (float)timer.getTimeElapsed());

            cameraPosition.sub(tempVector);
        }

        if(key == Key.D){
            Vector3f tempVector = new Vector3f(cameraDirectionVector);
            tempVector.cross(upPosition);
            tempVector.mul(2.5f * (float)timer.getTimeElapsed());
            cameraPosition = cameraPosition.add(tempVector);
        }

        if(key == Key.R){
            cameraPosition.y += 0.05f;
        }

        if(key == Key.F){
            cameraPosition.y -= 0.05f;
        }
    }

    private void calculateZoom(){

    }


    private void handleJoyInput(){
        double changeX = joy1.getCurrentJoyXPos();

        recalcRequired = true;
    }


    private void calculateViewMatrix(){
        viewMatrix= new Matrix4f().lookAt(cameraPosition,new Vector3f(cameraPosition).add(cameraDirectionVector),upPosition);
        recalcRequired = false;
    }

    public void setCameraPosition(Vector3f position){
        cameraPosition = position;
        recalcRequired = true;
    }


    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }

    @Override
    public void update() {
        if(recalcRequired){
            calculateViewMatrix();
        }

        if(joyStickEnabled){
           handleJoyInput();
        }

        Mouse mouse = Mouse.getInstance();

        calculatePitch(mouse.getDy(),mouse.getRightImmediate());
        calculateYaw(mouse.getDx(),mouse.getRightImmediate());
        calculateDirectionVector();

     // System.out.println(cameraPosition.x+" "+cameraPosition.y+" "+cameraPosition.z);
    }

    private float clamp(float min, float max, float value){
        if(value <=min){ return min;}
        if(value >=max){ return max;}
        return value;
    }

}
