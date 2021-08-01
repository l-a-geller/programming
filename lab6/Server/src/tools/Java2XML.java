package tools;

import data.LabworksStorage;
import data.LabWork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Java2XML {
    private File f = new File("output.xml");
    public void writeXML(){
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.write("<body>\n");
            for (LabWork labWork: LabworksStorage.getData()){
                fw.write("\t<el>\n");
                String coordinates = "";
                for (String f: labWork.getAllFieldNames()){
                    String res = "";
                    if (f.contains("name")){
                        res = labWork.getName();
                    }
                    if (f.contains("coordinates")){
                        res = labWork.getCoordinates().toString();
                    }
                    if (f.contains("minimalPoint")){
                        res = labWork.getMinPoint();
                    }
                    if (f.contains("maximumPoint")){
                        res = labWork.getMaxPoint();
                    }
                    if (f.contains("difficulty")){
                        res = labWork.getDifficulty();
                    }
                    if (f.contains("discipline")){
                        res = labWork.getDiscipline();
                    }
                    if (!res.equals("")){
                        fw.write("\t\t<" + f + ">" + res + "</" + f + ">\n");
                    }
                }
                fw.write("\t</el>\n");
            }
            fw.write("</body>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}