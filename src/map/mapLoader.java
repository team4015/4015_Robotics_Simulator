package map;



import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import map.Zones.ZoneTypes;
import map.Zones.ZoneSelector;

public class mapLoader {

    private BufferedImage mapImage;

    private String path;

    double width= 0.0;
    double length = 0.0;

    double pixelWidth = 0.0;
    double pixelLength = 0.0;

    public double getFieldWidth(){return width;}

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth(double pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    public double getPixelLength() {
        return pixelLength;
    }

    public void setPixelLength(double pixelLength) {
        this.pixelLength = pixelLength;
    }

    public mapLoader(String path) {
        this.path = path;
        loadImage();
        correctImage();
        outputSpecificZones();
    }




    private void loadImage(){
        try {
            BufferedImage mapImage = ImageIO.read(new File(path));
            this.mapImage = new BufferedImage(mapImage.getWidth(),mapImage.getHeight(),BufferedImage.TYPE_INT_RGB);

            Graphics2D g = this.mapImage.createGraphics();
            g.drawImage(mapImage,0,0,mapImage.getWidth(),mapImage.getHeight(),null);
            g.dispose();
        }catch(IOException ex){}
    }

    private void outputSpecificZones(){
        try{
            BufferedImage zone1 = new BufferedImage(mapImage.getWidth(),mapImage.getHeight(),BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<getWidth();i++){
                for(int j=0;j<getHeight();j++){
                    replaceWithColor(zone1,new Color(1,1,1),i,j);
                }
            }

            for(int i=0;i<getWidth();i++){
                for(int j=0; j< getHeight(); j++){
                    Color currColor = getPixel(i,j);
                    if(ZoneSelector.getZoneType(currColor) == ZoneTypes.BLUE_ALLIANCE_MOVE){
                        replaceWithColor(zone1,ZoneSelector.getZone(ZoneTypes.BLUE_ALLIANCE_MOVE).getColor(),i,j);
                    }
                }
            }

            ImageIO.write(zone1,"PNG",new File("Zone_AllianceBlue.png"));
        }catch (IOException ex){}
    }


    private void correctImage() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                Color currColor = getPixel(i, j);
                if (ZoneSelector.getZoneType(currColor) == null) {
                    System.out.println("Correcting image and outputting new one. Run again");
                    replaceWithBlack(i,j);
                }
            }
        }

        File f = new File("correctedFile.png");
        try {
            ImageIO.write(mapImage, "PNG", f);
        }catch(IOException ex){

        }
    }



    private void replaceWithBlack(int x, int y){
        int col = (1 << 24) | (0 << 16) | (0 << 16) | 0;
        System.out.println("Caner!");
        mapImage.setRGB(x,y,col);
    }

    private void replaceWithColor(BufferedImage image,Color color, int x, int y){
        int col = (1<< 24) | (color.getR() << 16) | (color.getG() << 16) | color.getB();
        image.setRGB(x,y,col);
    }

    public BufferedImage getMapImage(){
        return mapImage;
    }

    public Color getPixel(int x,int y){
        int clr=  mapImage.getRGB(x,y);
        int  red   = (clr & 0x00ff0000) >> 16;
        int  green = (clr & 0x0000ff00) >> 8;
        int  blue  =  clr & 0x000000ff;
        return new Color(red,green,blue);
    }

    public int getHeight(){
        return mapImage.getHeight();
    }

    public int getWidth(){
        return mapImage.getWidth();
    }
}
