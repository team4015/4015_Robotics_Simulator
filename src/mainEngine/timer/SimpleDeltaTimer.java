package mainEngine.timer;

import mainEngine.callbacks.*;

public class SimpleDeltaTimer implements updateCallback{

    private double lastTime;
    private double currTime;

    private double dt;

    private double totalTime;

    boolean timerInUse;

    public SimpleDeltaTimer() {
        reset();
        CallbackHandler.registerUpdateCallback(this);
    }

    private void reset(){
        lastTime = 0.0D;
        currTime = 0.0D;
        totalTime = 0.0D;
        totalTime = 0.0D;
        timerInUse = false;
    }

    public void startTimerUse(){
        timerInUse = true;
    }

    public void stopTimer(){
        reset();
    }

    public void pauseTimer(){
        timerInUse = false;
    }

    public double getTimeElapsed(){
        return dt;
    }

    public double getTotalTime(){
        return totalTime;
    }

    @Override
    public void update() {
        if(timerInUse) {
            currTime = System.nanoTime() / 1_000_000_000.0;
            dt = currTime - lastTime;

            lastTime = currTime;
            totalTime += dt;
        }
    }
}
