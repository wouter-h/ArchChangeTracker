package DataStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageLookupTable {
    private int count = 0;
    private HashMap<Integer, String> storage = new HashMap<>();
    private HashMap<String, Integer> packageLookUp = new HashMap<>();

    public void storagePackage(String str){
        if(!storage.containsValue(str)){
            storage.put(count, str);
            packageLookUp.put(str, count);
            ++count;
        }
    }

    public void storagePackages(ArrayList<String> packages){
        for(String p : packages){
            if(!storage.containsValue(p)){
                storage.put(count, p);
                packageLookUp.put(p, count);
                ++count;
            }
        }
    }

    public String getString(int n){
        return storage.get(n);
    }

    public Integer getKey(String str){
        return packageLookUp.get(str);
    }

    public int getSize(){
        return count;
    }
}
