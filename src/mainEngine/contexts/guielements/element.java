package mainEngine.contexts.guielements;

import mainEngine.callbacks.mouse.Mouse;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.ArrayList;

public abstract class element {

    /**
     * Every element must have a draw function,
     * It should be able to draw to a bufferedImage
     * Must have a graphics output
     */

    private ArrayList<element> windowElements;

    float xOffset;
    float yOffset;

    int width;
    int height;

    Mouse mouse;

    boolean hovering = false;

    boolean draggable = false;

    public element(float xOffset,float yOffset,int width,int height,boolean draggable){
        this.draggable = draggable;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        mouse = Mouse.getInstance();
        windowElements = new ArrayList<>();
    }

    public abstract void draw(Graphics2D g);

    /**
     * This function will return a boolean value depending on if an element was click or not
     * Uses the hover function to check if mouse is in region of element
     * @return boolean -> has click or not
     */

    public boolean hasMouseClick(){
        if(draggable) {
            if (hovering && mouse.getLeftImmediate()) {
                onMouseClick();
                return true;
            } else {
                return false;
            }
        }else{
            if(hovering && mouse.getLeftButton()){
                onMouseClick();
                return true;
            }else{
                return false;
            }
        }
    }


    /**
     * Method to be overriden by every element whether they implement mouse
     * control or not. Can be left empty.
     */
    public abstract void onMouseClick();

    /**
     * This function determines whether the mouse cursor is over the
     * current element or not
     * @return boolean -> hovering or not
     */
    public boolean hasMouseHover(){
        double mousePosX = mouse.getCurrentMouseX();
        double mousePosY = mouse.getCurrentMouseY();

        double boundingX = xOffset;
        double boundingY = yOffset;
        double boundingX1 = xOffset + width;
        double boundingY1 = yOffset + height;

        if((mousePosX > boundingX && mousePosX < boundingX1) && (mousePosY > boundingY && mousePosY < boundingY1)){
            onMouseHover();
            hovering = true;
            return true;
        }else{
            hovering = false;
            return  false;
        }
    }


    /**
     * This method is to be overriden by all gui elements
     * to add functionality for mouse hovering behavior
     * can be set to do nothing
     */
    public abstract void onMouseHover();

    public void translateElement(int x, int y){
        xOffset +=x;
        yOffset +=y;
        for(element element : windowElements){
            element.translateElement(x,y);
        }
    }

    final protected void drawText(Graphics2D g,String text,String fontName,int size,float x,float y, Color color){
        g.setPaint(color);
        g.setFont(new Font(fontName,Font.PLAIN,size));
        g.drawString(text,x,y);
    }





}
