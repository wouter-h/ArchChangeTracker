import java.util.ArrayList;

public class Info {
    private String commit;
    private String issueKey;
    private ArrayList<Integer> prNumbers;

    public Info(String issueKey, ArrayList<Integer> prNumbers){
        this.issueKey = issueKey;
        this.prNumbers = prNumbers;
    }

    public Info(String commit, String issueKey, ArrayList<Integer> prNumbers){
        this.commit = commit;
        this.issueKey = issueKey;
        this.prNumbers = prNumbers;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getCommit() {
        return commit;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public ArrayList<Integer> getPrNumbers() {
        return prNumbers;
    }
}