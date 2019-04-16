package mainEngine.shaders;


import static org.lwjgl.opengl.GL33.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FragShader extends Shader{




    public FragShader(String pathToCode) {
        super(pathToCode);
        compileShaderCode();
    }

    @Override
    void compileShaderCode() {

        shaderID = glCreateShader(GL_FRAGMENT_SHADER);

        String code = readFile();

        glShaderSource(shaderID,code);

        glCompileShader(shaderID);

        checkIfCompiled();
    }
}
