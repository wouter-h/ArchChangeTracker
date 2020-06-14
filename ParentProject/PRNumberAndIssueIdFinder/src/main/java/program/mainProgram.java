package program;

import java.util.ArrayList;

public class mainProgram {

    private String fileLocOriginalCSVFile;
    private String fileLoc1;
    private String fileLoc2;
    private String fileLoc3;
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
                "fileLoc2: path to the second file that will be analyzed.\n" +
                "fileLoc3: path to the third file that will be analyzed"
        );
    }

    public static void main(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 8){
            new mainProgram().printArgsInstructions();
            return;
        }
        new mainProgram().anAdditionMainFunction(args);
    }

    public void anAdditionMainFunction(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 8){
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
        this.fileLoc3 = args[7];
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

    private void addDateLinker(){
        String[] columns = {"date"};
        analyzers.add(new DateAnalyzer(fileLoc3, columns));
    }

    private void execute(){
        addGapAnalyzer();
        addPrAndIssueFinder();
        addDateLinker();
        for(BashFileAnalyzer bfa : analyzers){
            bfa.read();
            bfa.analyze();
        }
        ArrayList<String> originalCsvLines = new FileReader(fileLocOriginalCSVFile).readLines();
        NewCsvWriter ncw = new NewCsvWriter(csvFileLoc);
        for(String column : analyzers.get(2).getColumnNames()){
            ncw.writeFieldComma(column);
        }
        ncw.writeLine(originalCsvLines.get(0));//column names
        for(int i = 0; i < analyzers.size() - 1; ++i){
            BashFileAnalyzer bfa = analyzers.get(i);
            for(String columnName : bfa.getColumnNames()) {
                ncw.writeCommaField(columnName);
            }
        }
        ncw.writeNewLineCharacter();
        //all other data
        System.out.println("originalCsvLines.size(): + " + (originalCsvLines.size() - 1));
        for(int i = 1; i < originalCsvLines.size() - 1; ++i) {
            for (String field : analyzers.get(2).getLineOfData()) {
                ncw.writeFieldComma(field);
            }
            ncw.writeLine(originalCsvLines.get(i));
            for (int j = 0; j < analyzers.size() - 1; ++j){
                BashFileAnalyzer bfa = analyzers.get(j);
                String[] lineOfData = bfa.getLineOfData();
                for (String field : lineOfData) {
                    ncw.writeCommaField(field);
                }
            }
            ncw.writeNewLineCharacter();
        }
        for (String field : analyzers.get(2).getLineOfData()) {
            ncw.writeFieldComma(field);
        }
        ncw.writeLine(originalCsvLines.get(originalCsvLines.size() - 1));
        for (int i = 0; i < analyzers.size() - 1; ++i){
            String[] lineOfData = analyzers.get(i).getLineOfData();
            for (String field : lineOfData) {
                ncw.writeCommaField(field);
            }
        }
        ncw.close();
    }
}