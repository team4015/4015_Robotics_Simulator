package mainEngine.callbacks;

import mainEngine.callbacks.keyboard.KeyData;
import org.lwjgl.glfw.*;
import net.java.games.input.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.java.games.input.Component.Identifier.Key;


public class KeyboardHandler implements updateCallback,GLFWKeyCallbackI {

    ArrayList<KeyboardCallback> callbacks;

    private static KeyboardHandler keyboardHandler = new KeyboardHandler();

    Controller [] inputs = ControllerEnvironment.getDefaultEnvironment().getControllers();

    Controller keyboardController;

    Component [] keyboardComponents;

    Map<Component.Identifier.Key, KeyData> keysMap;

    private GLFWKeyCallback keyCallback;

    private long windowID;

    private KeyboardHandler(){

        keysMap = new HashMap<>();
        callbacks = new ArrayList<>();

        detectKeyboard();
    }

    private void detectKeyboard(){
        for(Controller inputs : inputs){
            if(inputs.getType() == Controller.Type.KEYBOARD && inputs != null){
                keyboardController = inputs;
                keyboardComponents = keyboardController.getComponents();
                System.out.println("Found keyboard :"+inputs.getName());
                break;
            }
        }

    }

    public void registerCallback(KeyboardCallback cb){
        callbacks.add(cb);
    }

    public static KeyboardHandler getInstance(){
        return keyboardHandler;
    }

    public void initKeyboard(long windowID){
        this.windowID = windowID;

        keysMap.put(Key.A,new KeyData(Key.A));
        keysMap.put(Key.B,new KeyData(Key.B));
        keysMap.put(Key.C,new KeyData(Key.C));
        keysMap.put(Key.D,new KeyData(Key.D));
        keysMap.put(Key.E,new KeyData(Key.E));
        keysMap.put(Key.F,new KeyData(Key.F));
        keysMap.put(Key.G,new KeyData(Key.G));
        keysMap.put(Key.H,new KeyData(Key.H));
        keysMap.put(Key.I,new KeyData(Key.I));
        keysMap.put(Key.J,new KeyData(Key.J));
        keysMap.put(Key.K,new KeyData(Key.K));
        keysMap.put(Key.L,new KeyData(Key.L));
        keysMap.put(Key.M,new KeyData(Key.M));
        keysMap.put(Key.N,new KeyData(Key.N));
        keysMap.put(Key.O,new KeyData(Key.O));
        keysMap.put(Key.P,new KeyData(Key.P));
        keysMap.put(Key.Q,new KeyData(Key.Q));
        keysMap.put(Key.R,new KeyData(Key.R));
        keysMap.put(Key.S,new KeyData(Key.S));
        keysMap.put(Key.T,new KeyData(Key.T));
        keysMap.put(Key.U,new KeyData(Key.U));
        keysMap.put(Key.V,new KeyData(Key.V));
        keysMap.put(Key.W,new KeyData(Key.W));
        keysMap.put(Key.X,new KeyData(Key.X));
        keysMap.put(Key.Y,new KeyData(Key.Y));
        keysMap.put(Key.Z,new KeyData(Key.Z));

        registerComponents();

        CallbackHandler.registerUpdateCallback(this);
    }

    private void registerComponents(){
        for(Component component : keyboardComponents){
            for(KeyData keyData : keysMap.values()){
                if(keyData.getKeyName() == component.getIdentifier()){
                    keyData.setKeyComponent(component);
                }
            }
        }
    }




    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {

    }


    private void scanKeyboard(){
        for(KeyData data: keysMap.values()){
            if(GLFW.glfwGetWindowAttrib(windowID,GLFW.GLFW_FOCUSED) == 1) {
                data.updateState();
            }
        }
    }

    @Override
    public void update() {

        keyboardController.poll();
        scanKeyboard();


        for(KeyboardCallback callback: callbacks){
            for(Component.Identifier.Key key : callback.keysToBeCalledFor){
                if(keysMap.get(key).isPressed()){
                    callback.keyPressed(keysMap.get(key).getKeyName());

                }

            }
        }

        for(KeyData keyData : keysMap.values()){
            keyData.reset();
        }


    }
}
