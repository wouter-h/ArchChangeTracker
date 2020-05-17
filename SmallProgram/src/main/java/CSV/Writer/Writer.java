package CSV.Writer;

import DataStorage.ChangedPackageI;
import DataStorage.DifferenceInfoHash;
import DataStorage.PackageLookupTable;

import java.io.*;
import java.util.ArrayList;

public class Writer {

    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;

    public Writer(String fileLoc){
        try {
            fw = new FileWriter(fileLoc, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void columnNames(String str){
        writeToFile(str + "\n");
    }

    /*public void addEntry(DifferenceInfoHash dih, PackageLookupTable plt){
        String entry = convertCommitToCSV(dih, plt);
        writeToFile(entry);
    }*/

    public void addEntryWithNewline(DifferenceInfoHash dih, PackageLookupTable plt1, PackageLookupTable plt2){
        String entry = convertCommitToCSV(dih, plt1, plt2);
        StringBuilder sb = new StringBuilder(entry);
        sb.append("\n");
        //System.out.println(sb.toString());
        writeToFile(sb.toString());
    }

    private void writeToFile(String str){
        out.write(str);
    }

    private String convertCommitToCSV(DifferenceInfoHash dih, PackageLookupTable plt1, PackageLookupTable plt2){
        String commit = dih.getGraphName();
        String comparedToCommit = dih.getComparedGraphName();
        ArrayList<ChangedPackageI> addedPackageDependencies = dih.getAddedPackageDependencies();
        ArrayList<ChangedPackageI> removedPackageDepencies = dih.getRemovedPackageDependencies();
        ArrayList<Integer> addedPackages = dih.getAddedPackages();
        ArrayList<Integer> removedPackages = dih.getRemovedPackages();

        StringBuilder sb = new StringBuilder();

        sb.append(commit);

        sb.append(",");

        sb.append(comparedToCommit);

        sb.append(",");
        sb.append("\"");
        sb.append("[");
        sb.append(arrayToCSV(plt1, addedPackages));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append("\"");
        sb.append("[");
        sb.append(arrayToCSV(plt2, removedPackages));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append("\"");
        sb.append("[");
        sb.append(changedPackagesToCSV1(plt1, plt1, addedPackageDependencies, "added"));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append("\"");
        sb.append("[");
        sb.append(changedPackagesToCSV1(plt2, plt1, removedPackageDepencies, "removed"));
        sb.append("]");
        sb.append("\"");

        return sb.toString();
    }

    private String changedPackagesToCSV1(PackageLookupTable plt, PackageLookupTable pltName, ArrayList<ChangedPackageI> cpi, String mode){
        if(cpi.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cpi.size() - 1 ; ++i){
            sb.append(changedPackageInfoToCSV1(plt, pltName, cpi.get(i), mode));
            sb.append(",");
        }
        sb.append(changedPackageInfoToCSV1(plt, pltName, cpi.get(cpi.size() - 1), mode));
        return sb.toString();
    }

    private String changedPackageInfoToCSV1(PackageLookupTable plt, PackageLookupTable pltName, ChangedPackageI cpi, String mode){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("name:");
        sb.append(getName(pltName, cpi.getPackageName()));
        sb.append(",");
        sb.append(mode).append(":");
        sb.append("[");
        if(mode.equals("added")) {
            sb.append(arrayToCSV(plt, cpi.getDependencies()));
        } else if(mode.equals("removed")) {
            sb.append(arrayToCSV(plt, cpi.getDependencies()));
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    private String arrayToCSV(PackageLookupTable plt, ArrayList<Integer> array){
        if(array.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.size() - 1; ++i){
            String name = getName(plt, array.get(i));
            sb.append(name);
            sb.append(",");
        }
        sb.append(getName(plt, array.get(array.size() - 1)));
        return sb.toString();
    }

    private String getName(PackageLookupTable plt, Integer n){
        return plt.getString(n);
    }

    public void close(){
        try {
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
