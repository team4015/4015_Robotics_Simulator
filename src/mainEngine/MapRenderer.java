package mainEngine;

import mainEngine.graphics.Texture;
import mainEngine.model.ObjModel;
import mainEngine.shaders.FragShader;
import mainEngine.shaders.RenderProgram;
import mainEngine.shaders.VertShader;
import mainEngine.callbacks.*;


/**
 * This class will be responsible for creating and parsing the grid
 * to display through the opengl game engine
 */
public class MapRenderer implements updateCallback, drawCallback3D {

    String directory;

    RenderProgram stadiumProgram;
    ObjModel stadiumModel;


    public MapRenderer(){
        directory = System.getProperty("user.dir");
        stadiumModel = new ObjModel(directory+"/models/slight.obj");
        //stadiumProgram = new RenderProgram(new VertShader(directory+"/shaders/Vert3d.glsl"),new FragShader(directory+"/shaders/Frag.glsl"));

        CallbackHandler.registerDrawCallback(this);
        CallbackHandler.registerUpdateCallback(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        drawCallback();
    }

    public void drawCallback(){

      // Renderer.getInstance().render3DModel(stadiumProgram,stadiumModel);
    }


}
