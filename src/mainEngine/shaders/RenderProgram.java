package mainEngine.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class RenderProgram {

    int programID;

    boolean programInUse = false;

    int projectionMatLocation = 0;

    VertShader vertShader;
    FragShader fragShader;

    public RenderProgram(VertShader vertShader, FragShader fragShader) {
        this.vertShader = vertShader;
        this.fragShader = fragShader;
        linkProgram();
    }

    private void linkProgram(){

        programID = glCreateProgram();

        glAttachShader(programID,vertShader.shaderID);
        glAttachShader(programID,fragShader.shaderID);

        glLinkProgram(programID);


        checkLink();


    }

    public void useProgram(){
        glUseProgram(programID);
        programInUse = true;
    }

    public void finishUse(){
        if(!programInUse){
            throw new IllegalStateException("Program not in use!");
        }

        glUseProgram(0);
        programInUse = false;
    }

    public int getUniformLocation(String uniformVar){
        int uniformLocation = glGetUniformLocation(programID,uniformVar);
        return uniformLocation;
    }

    public void setupProjectionMatrix(Matrix4f projectionMatrix){
        int projectionMatLocation = getUniformLocation("projection");
        FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
        projectionMatrix.get(matrix);
        glUniformMatrix4fv(projectionMatLocation,false,matrix);
    }

    public void setupViewMatrix(Matrix4f viewMatrix){
        int viewLocation = getUniformLocation("view");
        FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
        viewMatrix.get(matrix);
        glUniformMatrix4fv(viewLocation,false,matrix);
    }

    public void setupModelMatrix(Matrix4f modelMatrix){
        int modelLocation = getUniformLocation("model");
        FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
        modelMatrix.get(matrix);
        glUniformMatrix4fv(modelLocation,false,matrix);
    }

    public void setUniformFloat (float value, String floatName){
        int floatLocation = getUniformLocation(floatName);
        glUniform1f(floatLocation,value);
    }

    public void setUniform3fv(Vector3f vector, String name){
        int location = getUniformLocation(name);
        glUniform3fv(location,new float[]{vector.x,vector.y,vector.z});
    }

    private void checkLink(){

        try(MemoryStack memoryStack = stackPush()) {

            IntBuffer errorMessage = memoryStack.mallocInt(1);

            glGetProgramiv(programID, GL_LINK_STATUS, errorMessage);
            String err1 = glGetProgramInfoLog(programID);
            if(errorMessage.get(0)!= GL_TRUE){
                String err = glGetProgramInfoLog(programID);
                System.out.println("Failed to Link Program: "+err);
            }
        }
    }

}
