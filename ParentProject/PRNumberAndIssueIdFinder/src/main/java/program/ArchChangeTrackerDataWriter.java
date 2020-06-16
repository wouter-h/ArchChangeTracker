package program;

import java.util.ArrayList;

public class ArchChangeTrackerDataWriter implements FileScript, Writable {
    private String inputFileLocation;
    private ArrayList<String> contents;
    private String[] columnNames;
    private int index = 1;

    public ArchChangeTrackerDataWriter(String inputFileLocation){
        this.inputFileLocation = inputFileLocation;
    }

    public void read(){
        contents = new FileReader(inputFileLocation).readLines();
    }

    public void execute(){
        String columnLine = contents.get(0);
        this.columnNames = columnLine.split(",");
    }

    public String[] getColumnNames(){
        return columnNames;
    }

    public String[] getLineOfData(){
        String[] line = {contents.get(index)};
        ++index;
        return line;
    }

    public int getSize(){
        return contents.size();
    }
}
