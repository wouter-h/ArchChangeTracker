package Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryReader {
    private String dirPath;
    public DirectoryReader(String dirPath){this.dirPath = dirPath;}

    public ArrayList<File> read(){
        File f = new File(dirPath);
        ArrayList<File> filesToReturn = new ArrayList<>();
        File[] files = f.listFiles();
        if(files == null) return filesToReturn;
        for (File fileEntry : files) {
            if (fileEntry.isFile()) {
                filesToReturn.add(fileEntry);
            }
        }
        return filesToReturn;
    }
}
