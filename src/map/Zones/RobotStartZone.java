package map.Zones;

import org.joml.Vector3f;

import java.util.Vector;

public class RobotStartZone {

   private Vector3f positionInWorld;

    public RobotStartZone(Vector3f positionInWorld) {
        this.positionInWorld = positionInWorld;
    }

    public Vector3f getPositionInWorld() {
        return positionInWorld;
    }
}
