package mainEngine.callbacks;

import java.util.ArrayList;

public class CallbackHandler {


    private static ArrayList<drawCallback3D> allDrawingCallbacks = new ArrayList<>();
    private static ArrayList<updateCallback> allUpdateCallbacks = new ArrayList<>();
    private static ArrayList<AfterDraw> allAfterDrawCallbacks = new ArrayList<>();

    public static void registerDrawCallback(drawCallback3D cb){
        allDrawingCallbacks.add(cb);
    }

    public static void registerUpdateCallback(updateCallback cb){
        allUpdateCallbacks.add(cb);
    }

    public static void registerAfterDrawCallback(AfterDraw cb) {allAfterDrawCallbacks.add(cb);}

    public static void timeForDrawCB(){
        for(drawCallback3D cb: allDrawingCallbacks){
            cb.draw();
        }
    }

    public static void timeForUpdateCB(){
        for(updateCallback cb: allUpdateCallbacks){
            cb.update();
        }
    }

    public static void timeForAfterdrawCallbacks(){
        for(AfterDraw cb:allAfterDrawCallbacks){
            cb.afterDraw();
        }
    }


}
