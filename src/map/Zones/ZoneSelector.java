package map.Zones;

import map.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ZoneSelector {

    static Zone redAllianceMove = new Zone(ZoneTypes.RED_ALLIANCE_MOVE,new Color(255,0,0));
    static Zone blueAllianceMove = new Zone(ZoneTypes.BLUE_ALLIANCE_MOVE,new Color(0,38,255));
    static Zone moveAll = new Zone(ZoneTypes.ALL_MOVABLE, new Color(0,0,0));
    static  Zone notMovable = new Zone(ZoneTypes.NOT_MOVABLE,new Color(245,234,79));
    static Zone startPlatformBlue = new Zone(ZoneTypes.START_END_ZONE_BLUE,new Color(72,0,255));
    static Zone startPlatformRed = new Zone(ZoneTypes.START_END_ZONE_RED,new Color(255,151,107));
    static Zone blueGameLine = new Zone(ZoneTypes.BLUE_GAME_LINES, new Color(0,148,255));
    static Zone redGameLine = new Zone(ZoneTypes.RED_GAME_LINES,new Color(178,0,255));


    static ArrayList<Zone> zoneTypeList = new ArrayList<>(Arrays.asList(
            redAllianceMove,
            blueAllianceMove,
            moveAll,
            notMovable,
            startPlatformBlue,
            startPlatformRed,
            blueGameLine,
            redGameLine
    ));

    public static ZoneTypes getZoneType(Color color){
        for(Zone currZone : zoneTypeList){
            if(currZone.isThisZoneType(color)){
                return currZone.getType();
            }
        }
        return null;
    }

    public static Zone getZone(ZoneTypes type){
        for(Zone currZone: zoneTypeList){
            if(currZone.getType() == type){
                return currZone;
            }
        }
        return null;
    }

}
