package mainEngine.model;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {

    Vector3f position;

    Vector2f textureCoord;

    Vector3f normalVector;

    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normalVector) {
        this.position = position;
        this.textureCoord = textureCoord;
        this.normalVector = normalVector;
    }


}
