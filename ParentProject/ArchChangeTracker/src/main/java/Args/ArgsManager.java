package Args;

/** The class that manages the arguments given as input parameters to the program.
 *
 */
public class ArgsManager {
    private String inputFileLoc;
    private String outputFileLoc;
    private String name;

    /** Does some basic checks to see whether the args aren't completely wrong.
     *
     * @param args The args that will be checked.
     * @return True if it passes the checks, false otherwise.
     */
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

    /** Checks whether the args length is correct (length of 4).
     *
     * @param args the arguments that will be checked.
     * @return True if the length is 4, otherwise false.
     */
    private boolean checkArgsLength(String[] args){
        return args.length == 6;
    }

    /** Checks whether the first token equals "-if", the third token equals "-of", the second token should not be empty
     * nor contain only white spaces and the same also applies to the fourth token.
     * If it passes these checks the input file location and output file location will be set.
     *
     * @param args the arguments that will be checked.
     * @return True if the input passes these checks, false otherwise.
     */
    private boolean hasValidInputArgs(String[] args){
        if(args[0].equals("-if") &&
                args[2].equals("-of") &&
                args[4].equals("-name") &&
                !args[1].isEmpty() && !args[1].isBlank() &&
                !args[3].isEmpty() && !args[3].isBlank() &&
                !args[5].isEmpty() && !args[5].isBlank()
        ){
            this.inputFileLoc = args[1];
            this.outputFileLoc = args[3];
            this.name = args[5];
            return true;
        }
        return false;
    }

    /** Prints the error message if the arguments are incorrect.
     *
     */
    public void printArgsInstructions(){
        System.out.println("Args are invalid");
    }

    /** Returns the input file location.
     *
     * @return the input file location
     */
    public String getInputFileLoc() {
        return inputFileLoc;
    }

    /** Returns the output file location.
     *
     * @return the output file location
     */
    public String getOutputFileLoc() {
        return outputFileLoc;
    }

    public String getName() { return name;}
}
