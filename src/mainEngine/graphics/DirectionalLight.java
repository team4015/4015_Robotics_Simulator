package mainEngine.graphics;

import org.joml.Vector3f;

public class DirectionalLight {

    private Vector3f positionInWorld;

    private int lightIndex;

    public DirectionalLight(Vector3f positionInWorld,int lightIndex)
    {
        this.positionInWorld = positionInWorld;
        this.lightIndex = lightIndex;
    }


    public Vector3f getPositionInWorld() {
        return positionInWorld;
    }

    public int getLightIndex(){return lightIndex;}

    public void setPositionInWorld(Vector3f positionInWorld) {
        this.positionInWorld = positionInWorld;
    }
}
