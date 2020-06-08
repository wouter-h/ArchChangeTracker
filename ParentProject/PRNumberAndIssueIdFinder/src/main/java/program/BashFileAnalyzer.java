package program;

public interface BashFileAnalyzer {
    public void read();
    public void analyze();
    public String[] getColumnNames();
    public String[] getLineOfData();
}
