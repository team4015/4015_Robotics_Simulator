package mainEngine.contexts.guielements;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.callbacks.mouse.MouseClickCB;
import mainEngine.graphicBuffers.ElementBufferObject;
import mainEngine.graphicBuffers.VertexArrayObject;
import mainEngine.logging.Logger;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Button extends element implements DrawCallback2D{


    Image buttonImage;
    
    BufferedImage buttonTexture;

    int textureID;

    VertexArrayObject vao;
    ElementBufferObject ibo;

    String buttonText;

    MouseClickCB cbFunction;

    Graphics2D g2d;

    float [] textureCoords;
    float [] vertices;

    boolean once = true;


    public Button(float xOffset, float yOffset, int width, int height, String buttonText, MouseClickCB cbFunction) {
        super(xOffset, yOffset, width, height,false);
        this.buttonText = buttonText;
        buttonImage = GuiTextureStore.getInstance().getButtonBySize(width,height);

        buttonTexture = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        vao = new VertexArrayObject();
        ibo = new ElementBufferObject();
        g2d = buttonTexture.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        initTextures();
        this.cbFunction = cbFunction;
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

        vao.setData(1,textureCoords,true,GL_STATIC_DRAW);
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
    public void draw2D() {

        draw(g2d);
        hasMouseHover();
        hasMouseClick();
        initVertices();
        uploadTexture();
        drawTexture();
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

        buttonTexture.getRGB(0,0,width,height,pixels,0,width);

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

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(buttonImage,0,0,null);
        drawText(g2d,buttonText,"Times New Roman",20,10,30,Color.WHITE);

    }

    @Override
    public void onMouseClick() {
        cbFunction.onClick();
    }

    @Override
    public void onMouseHover() {
        g2d.setPaint(Color.RED);
        g2d.drawRect(0,0,width-1,height-1);
        //System.out.println("Clicked");
    }
}
