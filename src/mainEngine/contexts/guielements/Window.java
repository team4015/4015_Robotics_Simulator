package mainEngine.contexts.guielements;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.callbacks.mouse.Mouse;
import mainEngine.datastore.DataStoreGlobal;
import mainEngine.graphicBuffers.ElementBufferObject;
import mainEngine.graphicBuffers.VertexArrayObject;
import mainEngine.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;




import javax.imageio.ImageIO;
import javax.swing.*;

import static org.lwjgl.opengl.GL33.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Window extends  element implements DrawCallback2D {

    private BufferedImage windowTexture;

    private Image windowImage;

    private Graphics2D g2d;

    private int textureID;

    private VertexArrayObject vao;

    private ElementBufferObject ibo;

    float [] vertices;
    float [] textureCoords;

    boolean once = true;

    public Window(float xOffset, float yOffset, int width, int height) {
        super(xOffset,yOffset,width,height,true);
        vao = new VertexArrayObject();
        ibo = new ElementBufferObject();
        windowTexture = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        initTextures();
        windowImage = GuiTextureStore.getInstance().getWindowImageBySize(width,height);
        g2d = windowTexture.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    }

    private void initTextures(){
        textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glBindTexture(GL_TEXTURE_2D,0);


        textureCoords = new float[8];
        textureCoords[0] = 0.0f;
        textureCoords[1] = 0.0f;
        textureCoords[2] = 0.0f;
        textureCoords[3] = 1.0f;
        textureCoords[4] = 1.0f;
        textureCoords[5] = 0.0f;
        textureCoords[6] = 1.0f;
        textureCoords[7] = 1.0f;

        vao.setData(1,textureCoords,true,GL_DYNAMIC_DRAW);
        ibo.setData(new int[]{0,1,2,2,1,3});
    }

    private void initVertices(){

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

        vao.setData(0,vertices,true,GL_DYNAMIC_DRAW);

    }


    @Override
    public void update2D() {

    }

    @Override
    public void onMouseClick() {
        double dy = mouse.getDy();
        double dx = mouse.getDx();
        translateElement((int) dx,(int) dy);
    }

    @Override
    public void onMouseHover() {
            drawText(g2d,"Hovering Over me!","Times New Roman",10,50,100,Color.BLACK);
    }

    @Override
    public void draw2D() {

        draw(g2d);
        hasMouseHover();
        hasMouseClick();
        initVertices();
        uploadTexture();
        drawTexture();
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(windowImage,0,0,null);
        drawText(g,"FRC GRID 2019","Times New Roman",20 / (int)(height*0.011),7,height*0.06f,Color.WHITE);
        String xPos = String.format("%.2f %.2f",mouse.getCurrentMouseX(),mouse.getCurrentMouseY());
        drawText(g,xPos,"Times New Roman",20,50,150,Color.BLACK);
    }



    private void drawTexture(){
        vao.bind();
        ibo.bind();
        glBindTexture(GL_TEXTURE_2D,textureID);
        Logger.getInstance().clearErrors();
        glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0L);
        Logger.getInstance().getErrors();
        VertexArrayObject.unbind();
        ElementBufferObject.unbind();
        glBindTexture(GL_TEXTURE_2D,0);
    }

    private void uploadTexture(){

        int [] pixels = new int [width*height];

        ByteBuffer texBuf = BufferUtils.createByteBuffer(width*height * 4);

        windowTexture.getRGB(0,0,width,height,pixels,0,width);

        for(int i =0 ; i<height; i++){
            for(int j=0 ; j<width; j++){
                int pixel = pixels[i * width + j];
                texBuf.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                texBuf.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                texBuf.put((byte) (pixel & 0xFF));               // Blue component
               // texBuf.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        texBuf.flip();

        glBindTexture(GL_TEXTURE_2D,textureID);


        if(!once) {
            glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, texBuf);
        }else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, texBuf);
            once = false;
        }

        glBindTexture(GL_TEXTURE_2D,0);

        
    }
}
