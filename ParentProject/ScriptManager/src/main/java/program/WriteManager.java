package program;

import program.Script.Script;
import program.Script.Writable;

import java.util.ArrayList;

public class WriteManager {
    private int[] orderToWriteIn;
    private ArrayList<Script> scripts;
    private NewCsvWriter csvWriter;
    private int size;

    public WriteManager(int[] orderToWriteIn, ArrayList<Script> scripts, String writeLocation, int size){
        this.orderToWriteIn = orderToWriteIn;
        this.scripts = scripts;
        this.csvWriter = new NewCsvWriter(writeLocation);
        this.size = size;
    }

    public void write(){
        writeColumnNames();
        csvWriter.writeNewLineCharacter();
        writeFieldData();
        csvWriter.close();
    }

    private void writeColumnNames(){
        int i;
        for(i = 0; i < orderToWriteIn.length - 1; ++i){
            int index = orderToWriteIn[i];
            Script script = scripts.get(index);
            if(script instanceof Writable){
                String[] columns = ((Writable) script).getColumnNames();
                writeFieldComma(columns);
            }
        }
        Script script = scripts.get(orderToWriteIn[i]);
        if(script instanceof Writable){
            String[] columns = ((Writable) script).getColumnNames();
            writeFieldCommaExceptForLast(columns);
        }
    }

    private void writeFieldComma(String[] columns){
        for(String columnName : columns){
            csvWriter.writeFieldComma(columnName);
        }
    }

    private void writeFieldCommaExceptForLast(String[] fields){
        int i;
        for(i = 0; i < fields.length - 1; ++i){
            csvWriter.writeFieldComma(fields[i]);
        }
        csvWriter.writeLine(fields[i]);
    }

    private void writeFieldData() {
        for (int j = 1; j < size - 1; ++j) {
            int i;
            for (i = 0; i < orderToWriteIn.length - 1; ++i) {
                int index = orderToWriteIn[i];
                Script script = scripts.get(index);
                if (script instanceof Writable) {
                    String[] fields = ((Writable) script).getLineOfData();
                    writeFieldComma(fields);
                }
            }
            Script script = scripts.get(orderToWriteIn[i]);
            if (script instanceof Writable) {
                String[] fields = ((Writable) script).getLineOfData();
                writeFieldCommaExceptForLast(fields);
            }
            csvWriter.writeNewLineCharacter();
        }
        int i;
        for (i = 0; i < orderToWriteIn.length - 1; ++i) {
            int index = orderToWriteIn[i];
            Script script = scripts.get(index);
            if (script instanceof Writable) {
                String[] fields = ((Writable) script).getLineOfData();
                writeFieldComma(fields);
            }
        }
        Script script = scripts.get(orderToWriteIn[i]);
        if (script instanceof Writable) {
            String[] fields = ((Writable) script).getLineOfData();
            writeFieldCommaExceptForLast(fields);
        }
    }
}
