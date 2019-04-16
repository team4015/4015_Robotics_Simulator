package mainEngine.contexts.guielements;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.graphicBuffers.ElementBufferObject;
import mainEngine.graphicBuffers.VertexArrayObject;
import mainEngine.graphics.Texture;
import org.lwjgl.opengl.GL33;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class GLRectangle extends element implements DrawCallback2D {


    VertexArrayObject vao;
    ElementBufferObject ebo;
    float [] vertices;

    float[] textureCoords;

    Texture texture;

    public GLRectangle(float xOffset, float yOffset, int width, int height, Texture texture) {
        super(xOffset, yOffset, width, height, false);
        this.texture = texture;
        vao = new VertexArrayObject();
        ebo = new ElementBufferObject();

        generateVertices();
    }


    private void generateVertices(){
        vertices = new float [8];

        float x = super.xOffset;
        float y = super.yOffset;

        vertices[0] = x;
        vertices[1] = y +super.height;
        vertices[2] =x;
        vertices[3] = y;
        vertices[4] = x+super.width;
        vertices[5] = y+super.height;
        vertices[6] = x+super.width;
        vertices[7] = y;

        textureCoords = new float[8];
        textureCoords[0] = 0.0f;
        textureCoords[1] = 0.0f;
        textureCoords[2] = 0.0f;
        textureCoords[3] = 1.0f;
        textureCoords[4] = 1.0f;
        textureCoords[5] = 0.0f;
        textureCoords[6] = 1.0f;
        textureCoords[7] = 1.0f;

        vao.setData(1,textureCoords,true,GL_STATIC_DRAW);

        vao.setData(0,vertices,true,GL_STATIC_DRAW);
        ebo.setData(new int[]{0,1,2,2,1,3});
    }

    @Override
    public void draw2D() {
        vao.bind();
        ebo.bind();
        texture.bindTexture();
        GL33.glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0L);
        texture.unbindTexture();
        VertexArrayObject.unbind();
        ElementBufferObject.unbind();
    }


    @Override
    public void update2D() {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public void onMouseClick() {

    }

    @Override
    public void onMouseHover() {

    }
}
