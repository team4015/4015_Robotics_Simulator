package mainEngine.graphics;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.*;
import java.nio.*;
import java.util.*;

import static java.lang.Math.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImageResize.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Texture {

    private int width;
    private int height;
    private int comp;

    int textureID;

    final private String filePath;


    ByteBuffer imageData;

    public Texture(String filePath) {
        this.filePath = filePath;
        loadImage();
    }

    private void loadImage(){
        stbi_set_flip_vertically_on_load(false);

        try(MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            imageData = stbi_load(filePath,w,h,comp,3 );

            this.width = w.get(0);
            this.height = h.get(0);
            this.comp = comp.get(0);
        }
        setupOGLTexture();


    }

    private void setupOGLTexture(){
        textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D,textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,width,height,0,GL_RGB,GL_UNSIGNED_BYTE,imageData);
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(imageData);

        glBindTexture(GL_TEXTURE_2D,0);
    }

    public void bindTexture(){
        glBindTexture(GL_TEXTURE_2D,textureID);
    }
    public void unbindTexture(){glBindTexture(GL_TEXTURE_2D,0);}

    public void destroyTexture(){
        glDeleteTextures(textureID);
    }

    public static Texture loadTexture(String filePath){


        return new Texture(filePath);
    }







}
