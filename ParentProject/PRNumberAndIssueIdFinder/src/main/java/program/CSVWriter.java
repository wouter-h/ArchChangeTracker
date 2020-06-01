package program;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVWriter {
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;

    public CSVWriter(String fileLoc){
        try {
            fw = new FileWriter(fileLoc, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void columnNames(String str){
        writeToFile(str + "\n");
    }

    private void writeToFile(String str){
        out.write(str);
    }

    public void writeCommitInfos(ArrayList<String> originalCSVFile, ArrayList<Info> infos){
        //starting at 1 since the first line in the original csv files contained the column info
        for(int i = 1; i < originalCSVFile.size(); ++i){
            writeToFile(originalCSVFile.get(i) + "," + writeCommitInfo(infos.get(i - 1)) + "\n");
        }
    }

    private String writeCommitInfo(Info info){
        StringBuilder sb = new StringBuilder();
        //sb.append(info.getCommit());
        //sb.append(",");
        sb.append(info.getIssueKey());
        sb.append(",");
        sb.append('"');
        sb.append(arrayListToString(info.getPrNumbers()));
        sb.append('"');
        return sb.toString();
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

    private String arrayListToString(ArrayList<Integer> array){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i = 0; i < array.size() - 1; ++i){
            sb.append(array.get(i));
            sb.append(',');
        }
        if(array.size() > 0){
            sb.append(array.get(array.size() - 1));
        }
        sb.append(']');
        return sb.toString();
    }
}