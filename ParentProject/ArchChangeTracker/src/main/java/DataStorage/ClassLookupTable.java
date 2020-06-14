package DataStorage;

import java.util.ArrayList;
import java.util.HashMap;

/** A class that can be used to quickly see if a class exists
 *
 */
public class ClassLookupTable {
    private int count = 0;
    private HashMap<String, ArrayList<ClassInfo>> storage = new HashMap<>();

    /** Stores a ClassInfo object
     *
     * @param ci the ClassInfo object to be stored
     */
    public void store(ClassInfo ci){
        if(storage.get(ci.getName()) == null){
            ArrayList<ClassInfo> cia = new ArrayList<>();
            cia.add(ci);
            storage.put(ci.getName(), cia);
            ++count;
        } else {
            ArrayList<ClassInfo> cia = storage.get(ci.getName());
            cia.add(ci);
            storage.put(ci.getName(), cia); //overrides previous older arraylist
        }
    }

    /** Returns the arraylist of ClassInfo objects that have the name matching the input parameter str.
     *
     * @param str the name of the class that needs to be returned
     * @return an arraylist of classes that match that string, null if it does not exist
     */
    public ArrayList<ClassInfo> get(String str){
        return storage.get(str);
    }

    public int getSize(){
        return count;
    }
}
