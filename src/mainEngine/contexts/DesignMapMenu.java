package mainEngine.contexts;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.callbacks.mouse.MouseClickCB;
import mainEngine.contexts.guielements.Button;

import org.lwjgl.PointerBuffer;
import org.lwjgl.util.nfd.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DesignMapMenu implements DrawCallback2D {

    private Map<String, Button> buttonMap;


    public DesignMapMenu(){
        buttonMap = new HashMap<>();
        buttonMap.put("newMapKey",new Button(20,800,140,50,"Create New PlayingField", new MouseClickCB(){
            @Override
            public void onClick(){
                PointerBuffer outPath = memAllocPointer(1);
                System.out.println(System.getProperty("user.dir"));
               int result = NativeFileDialog.NFD_SaveDialog("map",System.getProperty("user.dir")+"/maps/",outPath);
                if(result!=NativeFileDialog.NFD_CANCEL) {
                    createMap(outPath.getStringUTF8(0));
                }
                outPath.free();
            }
        }));
        UIRender.getInstance().add2DCallback(Context.DESIGN_MAP,this);
    }


    private void createMap(String path){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write("*PlayingField Format v0.1*\n");
            bw.write("***LORD BLESS TEAM 4015***\n");
            bw.close();
        }catch (IOException ex){

        }
    }

    @Override
    public void draw2D() {
        for(Button button:buttonMap.values()){
            button.draw2D();
        }
    }

    @Override
    public void update2D() {
        for(Button button:buttonMap.values()){
            button.update2D();
        }
    }
}
