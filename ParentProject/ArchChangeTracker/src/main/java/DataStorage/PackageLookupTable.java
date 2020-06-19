package DataStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageLookupTable {
    private int count = 0;
    private HashMap<Integer, String> storage = new HashMap<>(250);
    private HashMap<String, Integer> packageLookUp = new HashMap<>(250);

    /** Stores a package inside 2 hashmaps. One with the <id, string> the other one with <string, id>.
     *
     * @param str the package to be stored
     */
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

    /** Retrieves the string belonging to an id. If the id does not exist, return null.
     *
     * @param n the id to find the string of
     * @return the string belonging to the id
     */
    public String getString(int n){
        return storage.get(n);
    }

    /** Retrieves the id belonging to a string. If the string does not exist, return null.
     *
     * @param str the string to find the id of
     * @return the id belonging to the string
     */
    public Integer getKey(String str){
        return packageLookUp.get(str);
    }

    /** Returns the size of the hashmap
     *
     * @return the size
     */
    public int getSize(){
        return count;
    }
}
