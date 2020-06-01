import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileReader {
    private String fileLoc;
    private BufferedReader br;

    public FileReader(String fileLoc){
        this.fileLoc = fileLoc;
        try {
            this.br = new BufferedReader(new java.io.FileReader(new File(fileLoc)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String read(){
        StringBuilder sb = new StringBuilder();
        int c;
        try {
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public ArrayList<String> readLines(){
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return lines;
    }
}
