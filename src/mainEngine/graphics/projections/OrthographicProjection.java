package mainEngine.graphics.projections;


import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.IntBuffer;


public class OrthographicProjection {

    private static Matrix4f currentProjection;

    private static long windowID;

    private static boolean projectionInitialized = false;

    public static void setWindowID(long windowNumber){
        windowID = windowNumber;
    }

    public static void createProjection(){
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(windowID,w,h);

        float width = (float) w.get(0);
        float height = (float) h.get(0);

        currentProjection = new Matrix4f().ortho2D(0.0f,width,0.0f,height);

        projectionInitialized = true;
    }

    public static Matrix4f getCurrentProjection(){
        if(projectionInitialized) {
            return currentProjection;
        }else{
            throw new IllegalStateException("Projection not initialized");
        }
    }



}
