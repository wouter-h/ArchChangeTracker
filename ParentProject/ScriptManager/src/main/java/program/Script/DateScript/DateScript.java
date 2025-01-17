package program.Script.DateScript;

import program.FileReader;
import program.Script.FileScript;
import program.Script.Writable;

import java.util.ArrayList;

public class DateScript implements FileScript, Writable {
    private String inputBashFilePath;
    private String contents;
    private ArrayList<String> dates;
    private String[] columnNames;
    private int index = 0;

    public DateScript(String inputBashFilePath, String[] columnNames){
        this.inputBashFilePath = inputBashFilePath;
        this.columnNames = columnNames;
        dates = new ArrayList<>();
    }

    public void read(){
        FileReader fr = new FileReader(inputBashFilePath);
        this.contents = fr.read();
    }

    public void execute(){
        System.out.println("analyzing...");
        String[] lines = contents.split("\n");
        for(String line : lines){
            String s = line.split(",")[1];
            dates.add(s);
        }
    }

    public String[] getColumnNames(){
        return this.columnNames;
    }

    public String[] getLineOfData(){
        String[] toRet = {dates.get(index)};
        ++index;
        return toRet;
    }
}
