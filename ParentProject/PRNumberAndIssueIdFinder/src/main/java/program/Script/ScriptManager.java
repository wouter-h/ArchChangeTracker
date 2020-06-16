package program.Script;

import java.util.ArrayList;

public class ScriptManager {
    ArrayList<Script> scripts = new ArrayList<>();

    public void addScript(Script script){
        scripts.add(script);
    }

    public void execute(){
        readInFiles();
        executeScripts();
    }

    private void readInFiles(){
        for(int i = 0; i < scripts.size(); ++i){
            Script s = scripts.get(i);
            if(s instanceof FileScript){
                ((FileScript) s).read();
            }
        }
    }

    private void executeScripts(){
        for(Script script: scripts){
            script.execute();
        }
    }

    public ArrayList<Script> getScripts(){
        return this.scripts;
    }
}
