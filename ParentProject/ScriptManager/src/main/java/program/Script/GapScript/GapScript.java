package program.Script.GapScript;

import program.FileReader;
import program.Script.FileScript;
import program.Script.Writable;

import java.util.ArrayList;

public class GapScript implements FileScript, Writable {
    private String inputBashFilePath;
    private String contents;
    private ArrayList<Integer> gaps;
    private String[] columnNames;
    private int index = 0;

    public GapScript(String inputBashFilePath, String[] columnNames){
        System.out.println("I AM HERE NOW\n\n\n\n\n\n");
        this.inputBashFilePath = inputBashFilePath;
        this.columnNames = columnNames;
        gaps = new ArrayList<>();
    }

    public void read(){
        FileReader fr = new FileReader(inputBashFilePath);
        this.contents = fr.read();
    }

    public void execute(){
        System.out.println("analyzing...");
        String[] lines = contents.split("\n");
        System.out.println("\n\n\n\n\n\n\n\nCONTENTS\n\n\n\n\n\n" + contents);
        for(String line : lines){
            int i = Integer.parseInt(line.split(",")[2]);
            gaps.add(i);
        }
    }

    public String[] getColumnNames(){
        return this.columnNames;
    }

    public String[] getLineOfData(){
        String[] toRet = {gaps.get(index).toString()};
        ++index;
        return toRet;
    }
}
