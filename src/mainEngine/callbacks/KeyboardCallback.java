package mainEngine.callbacks;

import java.util.ArrayList;

import net.java.games.input.*;

public interface KeyboardCallback {

    public ArrayList<Component.Identifier.Key> keysToBeCalledFor = new ArrayList<>();

    public void keyPressed(Component.Identifier.Key key);

}
