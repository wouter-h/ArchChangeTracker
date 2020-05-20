package Reader.GraphmlFile;

import java.io.File;
import java.util.Comparator;

public class GraphmlFileInfo implements Comparator< GraphmlFileInfo > {
    private File f;
    private int id;
    private String commit;

    public GraphmlFileInfo(){}

    public GraphmlFileInfo(File f){
        this.f = f;
        String name = f.getName();
        String[] elements = name.split("-");
        this.id = Integer.parseInt(elements[1]);
        this.commit = elements[3].substring(0, elements[3].length()-8);
    }

    public int getId(){
        return this.id;
    }

    public File getFile(){
        return this.f;
    }

    public String getCommit(){
        return this.commit;
    }

    @Override
    public int compare(GraphmlFileInfo f1, GraphmlFileInfo f2){
        return Integer.compare(f1.getId(), f2.getId());
    }
}
