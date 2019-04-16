package mainEngine;

import mainEngine.graphicBuffers.*;
import mainEngine.graphics.*;
import mainEngine.graphics.camera.Camera;
import mainEngine.graphics.projections.PerspectiveProjection;
import mainEngine.model.ObjModel;
import mainEngine.shaders.*;
import org.joml.Matrix4f;

/**
 * This class will be the main class used to render literally anything.
 *
 */
public class Renderer
{

    private static Renderer rendererInstance = new Renderer();

    public static Renderer getInstance(){
        return rendererInstance;
    }

    private Renderer(){

    }



    public void render3DModel(RenderProgram shaderProgram, ObjModel model){
        shaderProgram.useProgram();
        shaderProgram.setupProjectionMatrix(PerspectiveProjection.getCurrentProjection());
        shaderProgram.setupViewMatrix(Camera.getInstance().getViewMatrix());
        shaderProgram.setupModelMatrix(model.getModelMatrix());
        shaderProgram.setUniformFloat((float)Math.random(),"randomSeed");
        model.draw();
        shaderProgram.finishUse();
    }

}
