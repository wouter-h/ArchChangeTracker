package program;

import java.util.ArrayList;

public class mainProgram {
    /*
        private static ArrayList<String> getCommits(String path){
            ArrayList<String> commits = new ArrayList<>();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new program.FileReader(path));
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

        private static program.Info getPRNumberAndIssueId(String str){
            JSONObject json = new JSONObject(str);
            if((Integer) json.get("total_count") == 0) return new program.Info("", -1);
            JSONArray jsonArray = json.getJSONArray("items");
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
            Integer number = (Integer) jsonObject.get("number");
            String title = (String) jsonObject.get("title");
            String issueId = getIssueId(title, "TAJO-");
            System.out.println("number: " + number);
            System.out.println("title: " + title);
            System.out.println("issueId: " + issueId);
            return new program.Info(issueId, number);
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

    public void printArgsInstructions(){
        System.out.println("Incorrect input args.\n" +
                "Please provide a <originalCSVFileLoc> <fileLoc> <csvFileLoc> <delimeter> <issueToken> <prToken>\n" +
                "originalCSVFileLoc: path to the original csv file that was fed to the bash script analyzer\n" +
                "fileLoc: path to the file that will be anayzed\n" +
                "csvFileLoc: path to the csv file where the results will be written to.\n" +
                "delimeter: the separator used to make a distinction between commits in the file that will be analyzed.\n" +
                "issueToken: the token that will be used to recognise issues (includes lines, e.g. -, so TAJO- for issues of the form: TAJO-xxx)" +
                "prToken: the token that will be used to recognise pull request numbers (e.g. Closes: # for pull request numbers of the form: Closes #xxx"
        );
    }

    public static void main(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 6){
            new mainProgram().printArgsInstructions();
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
    }

    public void anAdditionMainFunction(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 6){
            new mainProgram().printArgsInstructions();
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
    }
}