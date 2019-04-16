package mainEngine.graphicBuffers;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class VertexArrayObject {

    private int id;
    VertexBufferObject vertexBuffer;
    VertexBufferObject normalBuffer;
    VertexBufferObject textureBuffer;

    public VertexArrayObject(){
        id = glGenVertexArrays();
        vertexBuffer = new VertexBufferObject();
        normalBuffer = new VertexBufferObject();
        textureBuffer = new VertexBufferObject();
    }

    public VertexArrayObject(float [] dataToSet,boolean is2dTarget){
        id = glGenVertexArrays();

    }


    public void bind(){
        glBindVertexArray(id);
    }

    public static void unbind(){
        glBindVertexArray(0);
    }


    public void setData(int pos,float[] data, boolean is2dTarget,int type){
        bind();
      if(pos == 0){
          vertexBuffer.setAttrib(data,0,is2dTarget, type);
      }else if(pos == 1){
          normalBuffer.setAttrib(data,1,is2dTarget,type );
      }else if(pos == 2){
          textureBuffer.setAttrib(data,2,is2dTarget, type);
      }

       unbind();
    }


}
