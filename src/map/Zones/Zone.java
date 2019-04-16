package map.Zones;

import map.Color;
import map.Zones.ZoneTypes;

public class Zone {

    private ZoneTypes type;
    private Color color;

    public Zone(ZoneTypes type, Color color) {
        this.type = type;
        this.color = color;
    }

    public ZoneTypes getType() {
        return type;
    }

    public boolean isThisZoneType(Color color){
        if(this.color.equals(color)){
            return true;
        }else{
            return false;
        }
    }

    public Color getColor(){return color;}


}
