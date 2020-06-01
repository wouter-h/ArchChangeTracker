package DataStorage;

import java.util.ArrayList;

/** Interface for changed packages
 *
 */
public interface ChangedPackageI {
    Integer getPackageName();
    ArrayList<Integer> getDependencies();
}
