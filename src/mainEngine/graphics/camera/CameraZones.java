package mainEngine.graphics.camera;

import org.joml.Vector3f;

public class CameraZones {

    private Vector3f zoneLocation;

    private Vector3f cameraDirection;

    public CameraZones(Vector3f zoneLocation,Vector3f cameraDirection) {
        this.zoneLocation = zoneLocation;
        this.cameraDirection = cameraDirection;
    }


    public Vector3f getZoneLocation() {
        return zoneLocation;
    }

    public Vector3f getCameraDirection() {
        return cameraDirection;
    }
}
