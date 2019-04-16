package mainEngine.callbacks.keyboard;

import net.java.games.input.Component;

public class KeyData {

    Component.Identifier.Key keyName;

    Component keyComponent;

    int pressed = 0;

    public KeyData(Component.Identifier.Key keyName) {
        this.keyName = keyName;
    }

    public void setKeyComponent(Component component){
        this.keyComponent = component;
    }

    public void updateState(){
        if(keyComponent.getPollData() == 1.0f){
            pressed = 1;
        }
    }

    public void keyPressed(){
        pressed = 1;
    }

    public boolean isPressed(){
        return (pressed == 1) ? true : false;
    }

    public Component.Identifier.Key getKeyName(){
        return keyName;
    }

    public void reset(){
        pressed = 0;
    }
}
