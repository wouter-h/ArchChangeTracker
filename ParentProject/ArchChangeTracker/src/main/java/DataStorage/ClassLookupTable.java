package DataStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassLookupTable {
    private int count = 0;
    private HashMap<String, ArrayList<ClassInfo>> storage = new HashMap<>();

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

    public ArrayList<ClassInfo> get(String str){
        return storage.get(str);
    }

    public int getSize(){
        return count;
    }
}
