package program;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NewCsvWriter {
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;

    public NewCsvWriter(String fileLoc){
        try {
            fw = new FileWriter(fileLoc, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeField(String field){
        writeToFile("," + field);
    }

    public void writeLine(String line){
        writeToFile(line);
    }

    public void writeNewLineCharacter(){
        writeToFile("\n");
    }

    private void writeToFile(String str){
        out.write(str);
    }

    public void close(){
        try {
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}