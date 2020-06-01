package Reader.GraphmlFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Util {

    public static void sort(ArrayList<GraphmlFileInfo> files){
        Collections.sort(files, new GraphmlFileInfo());
    }

    public static ArrayList<GraphmlFileInfo> fileToGraphmlFileInfo(ArrayList<File> files){
        ArrayList<GraphmlFileInfo> array = new ArrayList<GraphmlFileInfo>(files.size());
        for(File f : files){
            array.add(new GraphmlFileInfo(f));
        }
        return array;
    }
}
