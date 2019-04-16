package mainEngine.contexts;

import mainEngine.callbacks.DrawCallback2D;
import mainEngine.contexts.guielements.GLRectangle;
import mainEngine.datastore.DataStoreGlobal;
import mainEngine.graphics.Texture;
import mainEngine.graphics.projections.OrthographicProjection;
import mainEngine.shaders.FragShader;
import mainEngine.shaders.RenderProgram;
import mainEngine.shaders.Shader;
import mainEngine.shaders.VertShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UIRender {

    private static UIRender ourInstance = new UIRender();

    public static UIRender getInstance() {
        if(ourInstance == null){
            ourInstance = new UIRender();
        }
        return ourInstance;
    }

    RenderProgram uiRenderer= new RenderProgram(new VertShader(System.getProperty("user.dir")+"/shaders/Vert.glsl"),new FragShader(System.getProperty("user.dir")+"/shaders/Frag2d.glsl"));

    private Map<Context,DrawCallback2D> callbacks2D;

    private Texture backgroundTexture;

    private DataStoreGlobal dataStoreGlobal = DataStoreGlobal.getInstance();

    private GLRectangle glRectangle;

    public void add2DCallback(Context contexToRegister,DrawCallback2D cb){
        if(callbacks2D.keySet().contains(contexToRegister)){
            throw new IllegalArgumentException("STATE ALREADY REGISTERED!");
        }
        callbacks2D.put(contexToRegister,cb);
    }

    private MainMenu mainMenu;

    private DesignMapMenu designMapMenu;

    public Context currentContext;

    public void setState(Context newContext){
        currentContext = newContext;
    }

    private UIRender(){
        callbacks2D = new HashMap<>();
        backgroundTexture = new Texture(System.getProperty("user.dir")+"/resources/windowAssets/menu.png");
        currentContext = Context.MAIN_MENU;
    }

    public void initializeContexts(){
        mainMenu = new MainMenu();
        designMapMenu = new DesignMapMenu();
        //dataStoreGlobal.getPropertyInteger("windowWidthActual"),dataStoreGlobal.getPropertyInteger("windowHeightActual")
        glRectangle = new GLRectangle(0,0,dataStoreGlobal.getPropertyInteger("windowWidthActual"),dataStoreGlobal.getPropertyInteger("windowHeightActual") ,backgroundTexture);
    }


    public void timeFor2DCallbacks(){

        uiRenderer.useProgram();
        uiRenderer.setupProjectionMatrix(OrthographicProjection.getCurrentProjection());


        callbacks2D.get(currentContext).draw2D();
        glRectangle.draw2D();
        uiRenderer.finishUse();
    }

    public void timeForUpdate2D(){

    }



}
