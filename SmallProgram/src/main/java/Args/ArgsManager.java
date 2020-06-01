package Args;

public class ArgsManager {
    private String inputFileLoc;
    private String outputFileLoc;

    public boolean isValidArgs(String[] args){
        if(!checkArgsLength(args)){
            printArgsInstructions();
            return false;
        }
        if(!hasValidInputArgs(args)){
            printArgsInstructions();
            return false;
        }
        return true;
    }

    private boolean checkArgsLength(String[] args){
        return args.length == 4;
    }

    private boolean hasValidInputArgs(String[] args){
        if(args[0].equals("-if") &&
                args[2].equals("-of") &&
                !args[1].isEmpty() && !args[1].isBlank() &&
                !args[3].isEmpty() && !args[3].isBlank()
        ){
            this.inputFileLoc = args[1];
            this.outputFileLoc = args[3];
            return true;
        }
        return false;
    }

    private void printArgsInstructions(){
        System.out.println("Stub error message");
    }

    public String getInputFileLoc() {
        return inputFileLoc;
    }

    public String getOutputFileLoc() {
        return outputFileLoc;
    }
}
