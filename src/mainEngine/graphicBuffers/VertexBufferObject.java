package mainEngine.graphicBuffers;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

public class VertexBufferObject {

    private int id;

    private float [] data;

    public VertexBufferObject(float [] data, boolean is2dTarget){

        id = glGenBuffers();


    }


    public VertexBufferObject(){
        id = glGenBuffers();
    }

    public void setAttrib(float [] data,int pos,boolean is2dTarget,int type){
        this.data = data;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length+1);
        buffer.put(data);
        buffer.flip();

        bind();
        glBufferData(GL_ARRAY_BUFFER,buffer,type);
        glEnableVertexAttribArray(pos);
        if(is2dTarget) {
            glVertexAttribPointer(pos, 2, GL_FLOAT, false, 0, 0);
        }else{
            glVertexAttribPointer(pos,3,GL_FLOAT,false,0,0);
        }
        //glDisableVertexAttribArray(0);

        unbind();
    }

    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER,id);
    }

    public static void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public void delete(){
        glDeleteBuffers(id);
    }

}
