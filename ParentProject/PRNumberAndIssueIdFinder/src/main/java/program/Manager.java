package program;

import program.Script.*;
import program.Script.ArchChangeTrackerScript.ArchChangeTrackerDataWriter;
import program.Script.DateScript.DateScript;
import program.Script.GapScript.GapScript;
import program.Script.PrAndIssueScript.PrAndIssueScript;

public class Manager {

    private String fileLocOriginalCSVFile;
    private String fileLoc1;
    private String fileLoc2;
    private String fileLoc3;
    private String csvFileLoc;
    private String delimiter;
    private String issueToken;
    private String prToken;
    //private ArrayList<BashFileAnalyzer> analyzers = new ArrayList<>();
    private ScriptManager scriptManager = new ScriptManager();

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
            new Manager().printArgsInstructions();
            return;
        }
        new Manager().anAdditionMainFunction(args);
    }

    /*reflection issues in maven or something. That's why the name of this method is so strange*/
    public void anAdditionMainFunction(String[] args){
        System.out.println("In PRNumberAndIssueIdFinder!");
        if(args.length != 8){
            new Manager().printArgsInstructions();
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
        scriptManager.addScript(new GapScript(fileLoc2, columns));
    }

    private void addPrAndIssueFinder(){
        String[] columns = {"issueId", "prNumber"};
        scriptManager.addScript(new PrAndIssueScript(fileLoc1, columns, delimiter, issueToken, prToken));
    }

    private void addDateLinker(){
        String[] columns = {"date"};
        scriptManager.addScript(new DateScript(fileLoc3, columns));
    }

    private void addArchChangeTrackerDataWriter(Script archChangeTrackerData){
        scriptManager.addScript(archChangeTrackerData);
    }

    private void execute(){
        ArchChangeTrackerDataWriter archChangeTrackerDataWriter = new ArchChangeTrackerDataWriter(fileLocOriginalCSVFile);
        addGapAnalyzer();
        addPrAndIssueFinder();
        addDateLinker();
        addArchChangeTrackerDataWriter(archChangeTrackerDataWriter);
        int[] writeOrder = {2, 3, 0, 1};
        scriptManager.execute();
        WriteManager writeManager = new WriteManager(writeOrder, scriptManager.getScripts(), csvFileLoc, archChangeTrackerDataWriter.getSize());
        writeManager.write();
    }
}