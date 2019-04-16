package mainEngine.contexts;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.callbacks.mouse.MouseClickCB;
import mainEngine.contexts.guielements.Button;
import mainEngine.contexts.guielements.Window;
import mainEngine.datastore.DataStoreGlobal;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainMenu implements DrawCallback2D {


    private Map<String,Button> buttonMap;


    public MainMenu(){
        UIRender.getInstance().add2DCallback(Context.MAIN_MENU,this);
        initButtons();

    }



    private void initButtons(){
        buttonMap = new HashMap<>();
        buttonMap.put("loadMapButton",new Button(20,800,140,50,"Design PlayingField", new MouseClickCB(){
            @Override
            public void onClick(){
                UIRender.getInstance().setState(Context.DESIGN_MAP);
            }
        }));
        buttonMap.put("RobotLoaderButton",new Button(20,680,140,50,"Robot Loader", new MouseClickCB()));
        buttonMap.put("PlayMapButton",new Button(20,560,140,50,"Play PlayingField", new MouseClickCB()));
        buttonMap.put("ExitButton",new Button(20,440,140,50,"Exit", new MouseClickCB(){
            @Override
            public void onClick(){
                GLFW.glfwSetWindowShouldClose(DataStoreGlobal.getInstance().getPropertyLong("windowID"),true);
            }
        }));
    }







    @Override
    public void draw2D() {
        for(Button buttons : buttonMap.values()){
            buttons.draw2D();
        }
    }

    @Override
    public void update2D() {
        for(Button buttons : buttonMap.values()){
            buttons.update2D();
        }
    }
}
