package mainEngine.datastore;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is meant to be a data store for global properties useful to many
 * components of the program without having to get the mainApplication to initialize everything!
 */
public class DataStoreGlobal {


    /**
     * Single global instance of the data store.
     */
    private static DataStoreGlobal instance = new DataStoreGlobal();

    private Map<String,String> dataStore;

    private DataStoreGlobal(){
        dataStore = new HashMap<>();
    }

    public static DataStoreGlobal getInstance(){
        return instance;
    }


    /***
     * Methods to set a property. Will automatically create them if the property
     * doesn't exist
     * @param name property name
     * @param value property value -> Methods are type overloaded so be cautious when using
     */
    public void setProperty(String name, Double value){
        dataStore.put(name,value.toString());
    }

    public void setProperty(String name, Integer value){
        dataStore.put(name,value.toString());
    }

    public void setProperty(String name, Float value){
        dataStore.put(name,value.toString());
    }

    public void setProperty(String name, String value){
        dataStore.put(name,value);
    }

    public void setProperty(String name, Boolean value){
        dataStore.put(name,value.toString());
    }

    public void setProperty(String name, Long value){
        dataStore.put(name,value.toString());
    }

    /**
     * Methods to retrieve a value from the store.
     * Methods are type dependant and any property can be retrieved as any type as long
     * as they are compatible
     * @param name -> property name
     * @return the value in the type specified. Will crash if any errors occur.
     */

    public Double getPropertyDouble(String name){
        return Double.parseDouble(dataStore.get(name));
    }

    public Integer getPropertyInteger(String name){
        return Integer.parseInt(dataStore.get(name));
    }

    public Float getPropertyFloat(String name){
        return Float.parseFloat(dataStore.get(name));
    }

    public String getPropertyString(String name){
        return dataStore.get(name);
    }

    public Boolean getPropertyBoolean(String name){
        return Boolean.parseBoolean(dataStore.get(name));
    }

    public Long getPropertyLong(String name){
        return Long.parseLong(dataStore.get(name));
    }
}
