package front;

import data.LabWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.Collation;
import javafx.scene.control.TableView;
import tools.io.QuitException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class Updator {
    private static TableManager tableManager;
    private static CanvasHandler canvasHandler;
    private static String filterField = null;
    private static String filterType = null;
    private static HashMap<String, Boolean> filters = new HashMap<String, Boolean>();
    static {
        filters.put("ID", false);
        filters.put("NAME", false);
        filters.put("CREATION_DATE", false);
        filters.put("X", false);
        filters.put("Y", false);
        filters.put("MINIMAL_POINT", false);
        filters.put("MAXIMAL_POINT", false);
        filters.put("DIFFICULTY", false);
        filters.put("DISCIPLINE_NAME", false);
        filters.put("DISCIPLINE_LEC", false);
        filters.put("DISCIPLINE_PRA", false);
        filters.put("DISCIPLINE_SEL", false);
        filters.put("AUTHOR", false);
    }
    private static int idFilter;
    private static String nameFilter;
    private static String creDateFilter;
    private static int xFilter;
    private static float yFilter;
    private static float minFilter;
    private static Long maxFilter;
    private static int difFilter;
    private static String disNameFilter;
    private static Integer disLecFilter;
    private static Integer disPracFilter;
    private static Long disSelfFilter;
    private static String authorFilter;
    private static int filterOperation;
    private static boolean first = true;

    public static void setConfiguration(TableManager tableManager, CanvasHandler canvasHandler){
        Updator.tableManager = tableManager;
        Updator.canvasHandler = canvasHandler;
    }
    public static void addIdFilter(int id, int operandType){ // значение, тип операции
        filters.put("ID", true);
        idFilter = id;
        filterOperation = operandType;
    }
    public static void addNameFilter(String name, int operandType){ // значение, тип операции
        filters.put("NAME", true);
        nameFilter = name;
        filterOperation = operandType;
    }
    public static void addCreDateFilter(String date, int operandType){ // значение, тип операции
        filters.put("CREATION_DATE", true);
        creDateFilter = date;
        filterOperation = operandType;
    }
    public static void addXFilter(int x, int operandType){ // значение, тип операции
        filters.put("X", true);
        xFilter = x;
        filterOperation = operandType;
    }
    public static void addYFilter(float y, int operandType){ // значение, тип операции
        filters.put("Y", true);
        yFilter = y;
        filterOperation = operandType;
    }
    public static void addMinFilter(float min, int operandType){ // значение, тип операции
        filters.put("MINIMAL_POINT", true);
        minFilter = min;
        filterOperation = operandType;
    }
    public static void addMaxFilter(Long max, int operandType){ // значение, тип операции
        filters.put("MAXIMAL_POINT", true);
        maxFilter = max;
        filterOperation = operandType;
    }
    public static void addDifFilter(int difOrd, int operandType){ // значение, тип операции
        filters.put("DIFFICULTY", true);
        difFilter = difOrd;
        filterOperation = operandType;
    }
    public static void addDisNameFilter(String name, int operandType){ // значение, тип операции
        filters.put("DISCIPLINE_NAME", true);
        disNameFilter = name;
        filterOperation = operandType;
    }
    public static void addDisLecFilter(Integer lec, int operandType){ // значение, тип операции
        filters.put("DISCIPLINE_LEC", true);
        disLecFilter = lec;
        filterOperation = operandType;
    }
    public static void addDisPracFilter(Integer pra, int operandType){ // значение, тип операции
        filters.put("DISCIPLINE_PRA", true);
        disPracFilter = pra;
        filterOperation = operandType;
    }
    public static void addDisSelfFilter(Long sel, int operandType){ // значение, тип операции
        filters.put("DISCIPLINE_SEL", true);
        maxFilter = sel;
        filterOperation = operandType;
    }
    public static void addAuthorFilter(String au, int operandType){ // значение, тип операции
        filters.put("AUTHOR", true);
        authorFilter = au;
        filterOperation = operandType;
    }

    public static void clearFilters(){
        filters.put("ID", false);
        filters.put("NAME", false);
        filters.put("CREATION_DATE", false);
        filters.put("X", false);
        filters.put("Y", false);
        filters.put("MINIMAL_POINT", false);
        filters.put("MAXIMAL_POINT", false);
        filters.put("DIFFICULTY", false);
        filters.put("DISCIPLINE_NAME", false);
        filters.put("DISCIPLINE_LEC", false);
        filters.put("DISCIPLINE_PRA", false);
        filters.put("DISCIPLINE_SEL", false);
        filters.put("AUTHOR", false);
    }
    public static void updateTable() throws IOException, QuitException {
        while (true){
            try {
                Thread.sleep(2000);

                TableView<LabWork> table = tableManager.getTable();
                int focus = table.getSelectionModel().getFocusedIndex();
                ObservableList<LabWork> labs = tableManager.loadLabs();

                if (filters.get("ID")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getId() == idFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getId() > idFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getId() < idFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("NAME")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getName().equals(nameFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getName().compareTo(nameFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getName().compareTo(nameFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("CREATION_DATE")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDateLoc().equals(creDateFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDateLoc().compareTo(creDateFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDateLoc().compareTo(creDateFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("X")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getCoordinate_x() == xFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getCoordinate_x() > xFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getCoordinate_x() < xFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("Y")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getCoordinate_y() == yFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getCoordinate_y() > yFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getCoordinate_y() < yFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("MINIMAL_POINT")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getMin() == minFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getMin() > minFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getMin() < minFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("MAXIMAL_POINT")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getMax().equals(maxFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getMax() > (maxFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getMax() < (maxFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("DIFFICULTY")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDiff().ordinal() == difFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDiff().ordinal() > difFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDiff().ordinal() < difFilter).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("DISCIPLINE_NAME")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDisname().equals(disNameFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDisname().compareTo(disNameFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDisname().compareTo(disNameFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("DISCIPLINE_LEC")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDislec().equals(disLecFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDislec().compareTo(disLecFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDislec().compareTo(disLecFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("DISCIPLINE_PRA")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDisprac().equals(disPracFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDisprac().compareTo(disPracFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDisprac().compareTo(disPracFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("DISCIPLINE_SEL")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getDisself().equals(disSelfFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getDisself().compareTo(disSelfFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getDisself().compareTo(disSelfFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }
                if (filters.get("AUTHOR")) {
                    if (filterOperation == 0) labs = labs.stream().filter(labWork -> labWork.getAuthor().equals(authorFilter)).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 1) labs = labs.stream().filter(labWork -> labWork.getAuthor().compareTo(authorFilter) > 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                    if (filterOperation == 2) labs = labs.stream().filter(labWork -> labWork.getAuthor().compareTo(authorFilter) < 0).collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
                }

                canvasHandler.clearCanvas();
                canvasHandler.drawgrid();
                if (first){
                    labs.stream().forEach(lab -> { canvasHandler.drawLab(lab); first = false;});
                }else labs.stream().forEach(lab -> { canvasHandler.updateLab(lab);});

                table.setItems(labs);
                table.getSelectionModel().focus(focus);
            }catch (Exception e){
                //
            }
        }
    }
}
