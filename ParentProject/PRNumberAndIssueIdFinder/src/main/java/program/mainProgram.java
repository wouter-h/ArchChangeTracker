package program;

import java.util.ArrayList;

public class mainProgram {

    private String fileLocOriginalCSVFile;
    private String fileLoc1;
    private String fileLoc2;
    private String csvFileLoc;
    private String delimiter;
    private String issueToken;
    private String prToken;
    private ArrayList<BashFileAnalyzer> analyzers = new ArrayList<>();

    public void printArgsInstructions(){
        System.out.println("Incorrect input args.\n" +
                "Please provide a <originalCSVFileLoc> <fileLoc1> <csvFileLoc> <delimeter> <issueToken> <prToken> <fileLoc2>\n" +
                "originalCSVFileLoc: path to the original csv file that was fed to the bash script analyzer\n" +
                "fileLoc1: path to the first file that will be analyzed\n" +
                "csvFileLoc: path to the csv file where the results will be written to.\n" +
                "delimiter: the separator used to make a distinction between commits in the first file that will be analyzed.\n" +
                "issueToken: the token that will be used to recognise issues (includes lines, e.g. -, so TAJO- for issues of the form: TAJO-xxx) in the first file.\n" +
                "prToken: the token that will be used to recognise pull request numbers (e.g. Closes: # for pull request numbers of the form: Closes #xxx in the first file.\n" +
                "fileLoc2: path to the second file that will be analyzed."
        );
    }

    public static void main(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 7){
            new mainProgram().printArgsInstructions();
            return;
        }
        new mainProgram().anAdditionMainFunction(args);
        /*
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
        */
    }

    public void anAdditionMainFunction(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 7){
            new mainProgram().printArgsInstructions();
            return;
        }
        this.fileLocOriginalCSVFile = args[0];
        this.fileLoc1 = args[1];
        this.csvFileLoc = args[2];
        this.delimiter = args[3];
        this.issueToken = args[4];
        this.prToken = args[5];
        this.fileLoc2 = args[6];
        execute();
    }

    private void addGapAnalyzer(){
        String[] columns = {"gap"};
        analyzers.add(new GapAnalyzer(fileLoc2, columns));
    }

    private void addPrAndIssueFinder(){
        String[] columns = {"issueId", "prNumber"};
        analyzers.add(new PrAndIssueAnalyzer(fileLoc1, columns, delimiter, issueToken, prToken));
    }

    private void execute(){
        addGapAnalyzer();
        addPrAndIssueFinder();
        for(BashFileAnalyzer bfa : analyzers){
            bfa.read();
            bfa.analyze();
        }
        ArrayList<String> originalCsvLines = new FileReader(fileLocOriginalCSVFile).readLines();
        NewCsvWriter ncw = new NewCsvWriter(csvFileLoc);
        ncw.writeLine(originalCsvLines.get(0));//column names
        for(BashFileAnalyzer bfa : analyzers){
            for(String columnName : bfa.getColumnNames()) {
                ncw.writeField(columnName);
            }
        }
        //all other data
        ncw.writeNewLineCharacter();
        System.out.println("originalCsvLines.size(): + " + (originalCsvLines.size() - 1));
        for(int i = 1; i < originalCsvLines.size() - 1; ++i) {
            ncw.writeLine(originalCsvLines.get(i));
            for (BashFileAnalyzer bfa : analyzers) {
                String[] lineOfData = bfa.getLineOfData();
                for (String field : lineOfData) {
                    ncw.writeField(field);
                }
            }
            ncw.writeNewLineCharacter();
        }
        ncw.writeLine(originalCsvLines.get(originalCsvLines.size() - 1));
        System.out.println("\n\nI GOT HERE\n");
        for (BashFileAnalyzer bfa : analyzers) {
            String[] lineOfData = bfa.getLineOfData();
            for (String field : lineOfData) {
                ncw.writeField(field);
            }
        }
        ncw.close();
    }
}