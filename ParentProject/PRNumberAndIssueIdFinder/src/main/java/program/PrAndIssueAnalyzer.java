package program;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PrAndIssueAnalyzer implements BashFileAnalyzer {
    private String inputBashFilePath;
    private String contents;
    private ArrayList<Info> infos;
    private String[] columnNames;
    private int i = 0;
    private String delimiter;
    private String issueToken;
    private String prToken;

    public PrAndIssueAnalyzer(String inputBashFilePath, String[] columnNames, String delimiter, String issueToken, String prToken){
        this.inputBashFilePath = inputBashFilePath;
        this.columnNames = columnNames;
        this.delimiter = delimiter;
        this.issueToken = issueToken;
        this.prToken = prToken;
        infos = new ArrayList<>();
    }

    public void read(){
        FileReader fr = new FileReader(inputBashFilePath);
        this.contents = fr.read();
    }

    public void analyze(){
        String[] parts = contents.split(Pattern.quote(delimiter));
        for(String part : parts){
            infos.add(new Info(
                    getCommit(part),
                    getIssueKey(part, issueToken),
                    getPrNumbers(part, prToken)
            ));
        }
    }

    public String[] getColumnNames(){
        return this.columnNames;
    }

    public String[] getLineOfData(){
        String[] toRet = {
                infos.get(i).getIssueKey(),
                '"' + arrayListToString(infos.get(i).getPrNumbers()) + '"'
        };
        ++i;
        return toRet;
    }

    private static String getCommit(String str){
        return str.substring(0, 41);
    }

    private static String getIssueKey(String str, String token){
        int index = str.indexOf(token);
        if(index == -1){
            return "";
        }
        index += token.length();
        StringBuilder sb = new StringBuilder();
        sb.append(token);
        char c ;
        while(index < str.length()){
            c = str.charAt(index);
            if(Character.isDigit(c)){
                sb.append(c);
            } else {
                break;
            }
            ++index;
        }
        if(sb.toString().equals(token)){
            return "";
        }
        return sb.toString();
    }

    private static ArrayList<Integer> getPrNumbers(String str, String token){
        int index = str.indexOf(token);
        if(index == -1){
            return new ArrayList<>(1);
        }
        index += token.length();
        StringBuilder sb = new StringBuilder();
        char c ;
        ArrayList<Integer> array = new ArrayList<>();
        while(index < str.length()){
            c = str.charAt(index);
            if(Character.isDigit(c)){
                sb.append(c);
                ++index;
            } else {
                if(sb.toString().length() != 0) {
                    array.add(Integer.parseInt(sb.toString()));
                }
                sb = new StringBuilder();
                ++index;
                index = str.indexOf(token, index);
                if(index != -1){
                    index += token.length();
                } else {
                    break;
                }
            }
        }
        return array;
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
