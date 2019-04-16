package mainEngine.graphicBuffers;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;


public class ElementBufferObject {

    private int id;
    private int [] data;

    public ElementBufferObject(){
        id = glGenBuffers();
    }

    public ElementBufferObject(int [] data){
        id = glGenBuffers();

        setData(data);
    }

    public void setData(int [] data){
        this.data = data;

        IntBuffer elements = BufferUtils.createIntBuffer(data.length+1);
        elements.put(data);
        elements.flip();

        bind();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER,elements,GL_STATIC_DRAW);

        unbind();


    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,id);
    }

    public static void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }
}
