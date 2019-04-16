package mainEngine.shaders;

import org.lwjgl.system.CallbackI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL33.*;


public class VertShader extends Shader{



    public VertShader(String pathToCode) {
        super(pathToCode);
        compileShaderCode();
    }

    @Override
    protected void compileShaderCode(){

        shaderID = glCreateShader(GL_VERTEX_SHADER);

        String code = readFile();

        glShaderSource(shaderID,code);

        glCompileShader(shaderID);

        checkIfCompiled();

    }



}
