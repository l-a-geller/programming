package tools.csvConverting;

import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
import data.HumanBeing;
import data.HumanBeingStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class HumanBeingsWriter {
    private File f = new File("humanBeings.csv");

    public void writeCSV() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(f);
            pw.write("ID, NAME, X, Y, LOCAL_DATE_TIME, REAL_HERO, HAS_TOOTHPICK, IMPACT_SPEED, WEAPON_TYPE, MOOD, CAR_COOLNESS\n");
            for (HumanBeing humanBeing : HumanBeingStorage.getData()) {
                pw.write('"' + humanBeing.getId() + '"' + ",");
                pw.write('"' + humanBeing.getName() + '"' + ",");
                pw.write('"' + humanBeing.getCoordinates().getX() + '"' + ",");
                pw.write('"' + humanBeing.getCoordinates().getY() + '"' + ",");
                pw.write('"' + humanBeing.getCreationDate().toString() + '"' + ",");
                pw.write('"' + humanBeing.getRealHero().toString() + '"' + ",");
                pw.write('"' + humanBeing.doesHaveToothpick().toString() + '"' + ",");
                pw.write('"' + humanBeing.getImpactSpeed() + '"' + ",");
                try{
                    pw.write('"' + humanBeing.getWeaponType().toString() + '"' + ",");
                }catch (NullPointerException e){
                    pw.write('"' + "null" + '"' + ",");
                }
                pw.write('"' + humanBeing.getMood().toString() + '"' + ",");
                pw.write('"' + humanBeing.getCar().isCool().toString() + '"' + "\n");


            }
            System.out.println("Коллекция успешно сохранена в файл");

        } catch (IOException e) {
            System.out.println("Неопознанная проблема с файлом");
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }
}