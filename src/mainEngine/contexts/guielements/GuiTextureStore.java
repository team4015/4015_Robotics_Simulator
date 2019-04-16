package mainEngine.contexts.guielements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GuiTextureStore {
    private static GuiTextureStore ourInstance = new GuiTextureStore();

    public static GuiTextureStore getInstance() {
        return ourInstance;
    }

    private final String RESOURCES_PATH = "resources/windowAssets/";

   private BufferedImage windowImage;
   private BufferedImage roboticsLogo;
   private BufferedImage buttonImage;

    private GuiTextureStore() {

        //Load in essential textures to be reused
        try {
            windowImage = ImageIO.read(new File(RESOURCES_PATH+"window.png"));
            roboticsLogo = ImageIO.read(new File(RESOURCES_PATH+"robotics.png"));
            buttonImage = ImageIO.read(new File(RESOURCES_PATH+"button.png"));
        }catch (IOException ex){

        }

    }


    public Image getWindowImageBySize(int w, int h){
        return windowImage.getScaledInstance(w,h, Image.SCALE_SMOOTH);
    }

    public Image getButtonBySize(int w,int h){
        return buttonImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
    }

    public Image getRoboticsImageBySize(int w,int h){
        return roboticsLogo.getScaledInstance(w,h,Image.SCALE_SMOOTH);
    }


}
