package DataStorage;

/** The class that stores the package name and the class name itself of a class.
 *
 */
public class ClassInfo {
    private String packageName;
    private String name;

    public ClassInfo(String packageName, String name){
        this.packageName = packageName;
        this.name = name;
    }

    /** returns the package name
     *
     * @return package name
     */
    public String getPackageName() {
        return packageName;
    }

    /** Returns the class name.
     *
     * @return the class name
     */
    public String getName() {
        return name;
    }
}
