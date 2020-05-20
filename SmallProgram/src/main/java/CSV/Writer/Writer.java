package CSV.Writer;

import DataStorage.*;

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

    public void addEntryWithNewline(DifferenceInfo dih, PackageLookupTable plt1, PackageLookupTable plt2, ClassLookupTable clt2, ClassLookupTable clt1, CommitDependencies cd){
        String entry = convertCommitToCSV(dih, plt1, plt2, clt2, clt1, cd);
        StringBuilder sb = new StringBuilder(entry);
        sb.append("\n");
        writeToFile(sb.toString());
    }

    private void writeToFile(String str){
        out.write(str);
    }

    private String convertCommitToCSV(DifferenceInfo dih, PackageLookupTable plt1, PackageLookupTable plt2, ClassLookupTable clt2, ClassLookupTable clt1, CommitDependencies cd){
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
        sb.append(changedPackagesToCSV(plt1, plt1, addedPackageDependencies, "added"));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append("\"");
        sb.append("[");
        sb.append(changedPackagesToCSV(plt2, plt1, removedPackageDepencies, "removed"));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append("\"");
        sb.append("[");
        sb.append(classesToCSV(clt2, clt1, dih.getMovedClasses()));
        sb.append("]");
        sb.append("\"");

        sb.append(",");

        sb.append(dih.getAddedPackageCount());

        sb.append(",");

        sb.append(dih.getRemovedPackageCount());

        sb.append(",");

        sb.append(dih.getAddedDependenciesCount());

        sb.append(",");

        sb.append(dih.getRemovedDependenciesCount());

        sb.append(",");

        sb.append(dih.getMovedClassesCount());

        sb.append(",");

        sb.append(cd.getNrPackages());

        sb.append(",");

        sb.append(cd.getNumberOfDependencies());

        return sb.toString();
    }

    private String changedPackagesToCSV(PackageLookupTable plt, PackageLookupTable pltName, ArrayList<ChangedPackageI> cpi, String mode){
        if(cpi.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cpi.size() - 1 ; ++i){
            sb.append(changedPackageInfoToCSV(plt, pltName, cpi.get(i), mode));
            sb.append(",");
        }
        sb.append(changedPackageInfoToCSV(plt, pltName, cpi.get(cpi.size() - 1), mode));
        return sb.toString();
    }

    private String changedPackageInfoToCSV(PackageLookupTable plt, PackageLookupTable pltName, ChangedPackageI cpi, String mode){
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

    private String classesToCSV(ClassLookupTable clt2, ClassLookupTable clt1, ArrayList<ClassInfo> cia){
        if(cia.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cia.size() - 1; ++i){
            sb.append(cia.get(i).getPackageName());
            sb.append(cia.get(i).getName());
            sb.append(",");
        }
        sb.append(cia.get(cia.size() - 1).getPackageName());
        sb.append(cia.get(cia.size() - 1).getName());
        return sb.toString();
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
