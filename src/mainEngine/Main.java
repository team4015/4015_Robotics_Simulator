package mainEngine;

import mainEngine.MainApplication;
import map.*;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;

public class Main {

    static boolean mapToolIsRunning = false;

    public static MainApplication mainEngine;

    private static final String fileForMap = "correctedFile.png";

    public static void main(String[] args) {

        //WE INITIALIZE PARAMS FOR THE FIELD HERE.
        System.out.println(System.getProperty("user.dir")+"/lib/");
        System.setProperty("java.library.path", System.getProperty("user.dir")+"/lib/");

        mapLoader map = new mapLoader(fileForMap);

        System.out.println("Successfully loaded map at "+fileForMap );
        System.out.printf("Loaded PlayingField res: %d x %d \n",map.getWidth(),map.getHeight());

        int area = map.getWidth() * map.getHeight();


        System.out.println("Successfully Analyzed "+area+" pixels.");

        map.setLength(16.517);
        map.setWidth(7.326);

        map.setPixelLength(map.getLength()/map.getWidth());
        map.setPixelWidth(map.getFieldWidth()/map.getLength());

        System.out.printf("Estimated pixel length: %f\nEstimated Pixel width: %f\n",map.getPixelLength(),map.getPixelWidth());

        mainEngine = new MainApplication();
        mainEngine.run();
    }



}
