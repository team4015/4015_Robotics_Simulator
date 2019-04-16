package mainEngine.callbacks.mouse;

import mainEngine.callbacks.*;
import mainEngine.timer.SimpleDeltaTimer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse implements updateCallback,AfterDraw{

    private static Mouse mouseInstance = new Mouse();

    private double currentMouseX;
    private double currentMouseY;

    private double lastMouseX;
    private double lastMouseY;

    private double dy;
    private double dx;

    boolean mouseCoolDown;
    boolean mouseLeftPressed;
    boolean mouseRightPressed;

    SimpleDeltaTimer mouseReset = new SimpleDeltaTimer();

    private boolean initialized = false;

    private DoubleBuffer xPos;
    private DoubleBuffer yPos;
    private IntBuffer windowWidth;
    private IntBuffer windowHeight;

    private long windowID;

    private Mouse(){
        CallbackHandler.registerUpdateCallback(this);
        CallbackHandler.registerAfterDrawCallback(this);
        xPos = BufferUtils.createDoubleBuffer(1);
        yPos = BufferUtils.createDoubleBuffer(1);
        windowWidth = BufferUtils.createIntBuffer(1);
        windowHeight = BufferUtils.createIntBuffer(1);
        mouseReset.startTimerUse();
        mouseCoolDown = false;

    }

    GLFWMouseButtonCallback mcb = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_1){
               mouseLeftPressed = true;
            }else if(action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_2){
                mouseRightPressed = true;
            }
        }
    };

    public static Mouse getInstance(){
        return mouseInstance;
    }

    public void initWindow(long windowID){
        this.windowID = windowID;
        initialized = true;
        GLFW.glfwSetMouseButtonCallback(windowID,mcb);
    }

    @Override
    public void update() {
        if(initialized){
            handleTimer();
            cursorCallback();
        }else{
            throw new IllegalStateException("Mouse not initialized!");
        }

    }

    @Override
    public void afterDraw() {
       // handleTimer();
    }

    private void handleTimer(){

    }

    private void cursorCallback(){



        if(GLFW.glfwGetWindowAttrib(windowID,GLFW.GLFW_FOCUSED) == 1) {
            GLFW.glfwGetCursorPos(windowID, xPos, yPos);


            glfwGetWindowSize(windowID,windowWidth,windowHeight);


            currentMouseX = xPos.get(0);
            currentMouseY = windowHeight.get(0) - yPos.get(0);

            dx = currentMouseX - lastMouseX;
            dy = currentMouseY - lastMouseY;

            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;

            currentMouseX = clamp(0,windowWidth.get(0),currentMouseX);
            currentMouseY = clamp(0,windowHeight.get(0),currentMouseY);
           // System.out.printf("Current Mouse X %.2f dx %.2f\n",currentMouseX,dx);
        }else{
            dy=0.0;
            dx = 0.0;
        }

    }

    public double getCurrentMouseX() {
        return currentMouseX;
    }

    public double getCurrentMouseY() {
        return currentMouseY;
    }

    public double getLastMouseX() {
        return lastMouseX;
    }

    public double getLastMouseY() {
        return lastMouseY;
    }

    public boolean getLeftButton(){
        boolean value = mouseLeftPressed;
        mouseLeftPressed = false;
      return value;
    }

    public boolean getLeftButtonReleased(){return glfwGetMouseButton(windowID,GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE; }

    public boolean getLeftImmediate(){
        return glfwGetMouseButton(windowID,GLFW_MOUSE_BUTTON_LEFT) == 1;
    }

    public boolean getRightImmediate(){
        return glfwGetMouseButton(windowID,GLFW_MOUSE_BUTTON_RIGHT) == 1;
    }


    public boolean getRightButton(){
        return mouseRightPressed;
    }

    public double getDy() {
        return dy;
    }

    public double getDx() {
        return dx;
    }



    private double clamp(double min, double max, double value){
        if(value<= min){return min;}
        if(value>= max){return max;}
        return value;
    }


}
