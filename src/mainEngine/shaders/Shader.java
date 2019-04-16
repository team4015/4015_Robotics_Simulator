package mainEngine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL33.*;


public abstract class Shader {

    int shaderID;
    String pathToShader;

    public Shader(String pathToShader) {
        this.pathToShader = pathToShader;
    }

    public int getVertShaderID() {
        return shaderID;
    }


    abstract void compileShaderCode();

    public void delete(){
        glDeleteShader(shaderID);
    }

    protected void checkIfCompiled(){
        int success = glGetShaderi(shaderID,GL_COMPILE_STATUS);
        if(success != GL_TRUE){
            throw new RuntimeException(glGetShaderInfoLog(shaderID));
        }
    }

    protected String readFile(){
        StringBuffer code = new StringBuffer();

        String line;

        try(BufferedReader br = new BufferedReader(new FileReader(pathToShader))){
            while((line = br.readLine()) != null){
                code.append(line);
                code.append("\n");
            }


        }catch (IOException ex){
            System.out.println("Shader File "+pathToShader+" not found");
        }

        return code.toString();
    }
}
