package mainEngine.graphics.projections;

import org.joml.*;
import org.lwjgl.BufferUtils;
import mainEngine.callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.lang.Math;
import java.nio.IntBuffer;

public class PerspectiveProjection implements updateCallback{

    public PerspectiveProjection(){
        CallbackHandler.registerUpdateCallback(this);
    }

    private static long windowID;

    private static Matrix4f currentProjection;

    private static boolean projectionCreated = false;

    public static void setWindowID(long windowID){
        PerspectiveProjection.windowID = windowID;

    }

    private static float randomFloat = 0.0f;
    private static float randomFloatY = 0.0f;
    private static float randomFloatZ = 0.0f;

    public static void createNewProjection(float fov, float nearZ, float farZ){
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowID,w,h);

        float width = (float) w.get(0);
        float height = (float) h.get(0);


       currentProjection = new Matrix4f().perspective(fov,width/height,nearZ,farZ);
       projectionCreated= true;
    }

    @Override
    public void update() {

    }

    public static Matrix4f getCurrentProjection(){
        if(!projectionCreated){
            throw new IllegalStateException("No projection initialized, please initialize first!") ;
        }
        return  currentProjection;
    }



}
