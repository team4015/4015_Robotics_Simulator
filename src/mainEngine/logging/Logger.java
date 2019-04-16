package mainEngine.logging;

import static org.lwjgl.opengl.GL33.*;

public class Logger {
    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {
    }

    public void clearErrors() {
        while (glGetError() != GL_NO_ERROR) ;
    }

    public void getErrors(){
        int error;
        while((error = glGetError()) != GL_NO_ERROR){
            System.out.println("Error occured: "+error);
        }
    }
}
