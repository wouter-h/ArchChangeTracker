import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class main {
/*
    private static ArrayList<String> getCommits(String path){
        ArrayList<String> commits = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int i = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                if(i != 0){
                    String[] parts = line.split(",");
                    commits.add(parts[0]);
                }
                ++i;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return commits;
    }

    private static Info getPRNumberAndIssueId(String str){
        JSONObject json = new JSONObject(str);
        if((Integer) json.get("total_count") == 0) return new Info("", -1);
        JSONArray jsonArray = json.getJSONArray("items");
        JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
        Integer number = (Integer) jsonObject.get("number");
        String title = (String) jsonObject.get("title");
        String issueId = getIssueId(title, "TAJO-");
        System.out.println("number: " + number);
        System.out.println("title: " + title);
        System.out.println("issueId: " + issueId);
        return new Info(issueId, number);
    }

    private static String getIssueId(String title, String token){
        int start = title.indexOf(token);
        if(start == -1){return "NOT FOUND";}
        int i = start + token.length();
        StringBuilder issueId = new StringBuilder(token);
        boolean keepReading = true;
        while(i < title.length() && keepReading){
            char c = title.charAt(i);
            if(Character.isDigit(c)){
                issueId.append(c);
            } else {
                keepReading = false;
            }
            ++i;
        }
        return issueId.toString();
    }
*/
    public static void main(String[] args){
        if(args.length != 6){
            System.out.println("Insufficient arguments.\n" +
                    "Please provide a <originalCSVFileLoc> <fileLoc> <csvFileLoc> <delimeter> <issueToken> <prToken>\n" +
                    "originalCSVFileLoc: pathto the original csv file that was fed to the bash script analyzer\n" +
                    "fileLoc: path to the file that will be anayzed\n" +
                    "csvFileLoc: path to the csv file where the results will be written to.\n" +
                    "delimeter: the separator used to make a distinction between commits in the file that will be analyzed.\n" +
                    "issueToken: the token that will be used to recognise issues (includes lines, e.g. -, so TAJO- for issues of the form: TAJO-xxx)" +
                    "prToken: the token that will be used to recognise pull request numbers (e.g. Closes: # for pull request numbers of the form: Closes #xxx"
                    );
            return;
        }
        String fileLocOriginalCSVFile = args[0];
        String fileLoc = args[1];
        String csvFileLoc = args[2];
        String delimiter = args[3];
        String issueToken = args[4];
        String prToken = args[5];
        FileReader fr = new FileReader(fileLoc);
        String fileAsAString = fr.read();
        FileReader originalCSVFileReader = new FileReader(fileLocOriginalCSVFile);
        ArrayList<String> originalCSVFile = originalCSVFileReader.readLines();
        ArrayList<Info> commitInfos = FileAnalyzer.getCommitInfo(fileAsAString, delimiter, issueToken, prToken);
        CSVWriter writer = new CSVWriter(csvFileLoc);
        writer.columnNames(originalCSVFile.get(0) + ",issueId,prNumber");
        writer.writeCommitInfos(originalCSVFile, commitInfos);
        writer.close();
        /*String username = "xxxxxxx";
        String password = "xxxxxxx";
        URL url = null;
        ArrayList<String> commits = getCommits("/home/muffin/Documents/Universiteit/master internship/commits a2a/Commits.csv");
        ArrayList<Info> infos = new ArrayList<>();
        int i = 0;
        for(String commit : commits){
            System.out.println(commit);
            try {
                url = new URL("https://api.github.com/search/issues?q=sha:" + commit);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                //'%s:%s' % (username, passwd)).replace('\n', '')
                String encoding = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));
                //req.add_header("Authorization", "Basic %s" % base64string)
                con.setRequestProperty("Authorization", "Basic " + encoding);
                //int responseCode = con.getResponseCode();
                //System.out.println(responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
                Info info = getPRNumberAndIssueId(content.toString());
                info.setCommit(commit);
                infos.add(info);
                in.close();
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ++i;
            if(i == 11){return;}
        }
*/
    }
}
