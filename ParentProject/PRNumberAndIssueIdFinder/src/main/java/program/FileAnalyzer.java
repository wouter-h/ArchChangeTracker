package program;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileAnalyzer {
    public static ArrayList<Info> getCommitInfo(String str, String delimiter, String issueToken, String prToken){
        String[] parts = str.split(Pattern.quote(delimiter));
        ArrayList<Info> commitInfos = new ArrayList<>();
        for(String part : parts){
            commitInfos.add(new Info(
                    getCommit(part),
                    getIssueKey(part, issueToken),
                    getPrNumbers(part, prToken)
            ));
        }
        return commitInfos;
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
}