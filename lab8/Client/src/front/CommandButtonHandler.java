package front;

import data.Difficulty;
import data.LabWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.commands.CommandInvoker;
import tools.commands.commands.History;
import tools.io.QuitException;
import tools.io.StreamReadWriter;
import java.io.IOException;
import java.util.ArrayList;

class CommandButtonHandler {
    private static CommandInvoker commandInvoker;
    private static StreamReadWriter ioClient;
    CommandButtonHandler(CommandInvoker commandInvoker, StreamReadWriter ioClient){
        CommandButtonHandler.commandInvoker = commandInvoker;
        CommandButtonHandler.ioClient = ioClient;
    }
    public static void handleAdd(String sqlString, CanvasHandler canvasHandler)  {
        try {
            commandInvoker.setLabString(sqlString);
            commandInvoker.run("ADD", ioClient, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void handleClear() throws IOException, QuitException {
        commandInvoker.run("CLEAR", ioClient,null);
    }
    public static void handleClearFilter(TextArea textArea) {
        Updator.clearFilters();
        textArea.setText("Filters cleared");
    }
    public static void handleInfo(TextArea textArea) {
        try {
            String res = "";
            commandInvoker.run("INFO", ioClient, null);
            ArrayList<String> answers = commandInvoker.getAnsverArray();
            textArea.clear();
            answers.stream().forEach(s -> textArea.appendText(s + "\n"));
        } catch (QuitException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void handleHistory(TextArea textArea) {
        commandInvoker.loadToSavedCommands(new History());
        String s = commandInvoker.printSavedCommands();
        textArea.clear();
        textArea.appendText(s);
    }
    public static void handleFilter(TextArea textArea) {

        commandInvoker.loadToSavedCommands(new History());
        textArea.clear();
        textArea.appendText("Filtered");
        Button submitLab = new Button("Submit");
        ObservableList<String> fields = FXCollections.observableArrayList("Id", "Name", "x", "y", "Creation Date", "Minimal Point", "Maximal Point", "Difficulty", "Discipline Name", "Lecture hours", "Practice hours", "Selfstudy hours", "Author");
        ComboBox<String> fieldsComboBox = new ComboBox<String>(fields);

        ObservableList<String> types = FXCollections.observableArrayList(">", "<", "=");
        ComboBox<String> typesComboBox = new ComboBox<String>(types);

        TextField filterValue = new TextField();

        VBox filterInput = new VBox(10, new HBox(new Label("Labwork field"), fieldsComboBox), new HBox(new Label("Filter Type"), typesComboBox), new HBox(new Label("Filter Value"),  filterValue), submitLab);
        filterInput.setAlignment(Pos.CENTER);
        filterInput.setPadding(new Insets(10, 10, 10, 10));

        Stage filterStage = new Stage();
        filterStage.setMaxHeight(800);
        filterStage.setMaxWidth(500);
        FlowPane filterRoot = new FlowPane();
        filterRoot.getChildren().add(filterInput);

        Scene scene = new Scene(filterRoot);
        filterStage.setScene(scene);
        filterStage.setTitle("Table Filter");
        filterStage.setMaximized(false);
        filterStage.show();

        submitLab.setOnAction((l) -> {
            try{
                Integer filterType = null;
                if (typesComboBox.getValue().equals("=")){filterType = 0;}
                if (typesComboBox.getValue().equals(">")){filterType = 1;}
                if (typesComboBox.getValue().equals("<")){filterType = 2;}

                if (fieldsComboBox.getValue().toUpperCase().equals("ID")){
                    Updator.addIdFilter(Integer.parseInt(filterValue.getText()), filterType); // значение, тип операции
                    textArea.clear(); textArea.appendText(filterValue.getText() +" "+ filterType);
                }else if (fieldsComboBox.getValue().toUpperCase().equals("NAME")){
                    Updator.addNameFilter(filterValue.getText(), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("CREATION DATE")){
                    Updator.addCreDateFilter(filterValue.getText(), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("X")){
                    Updator.addXFilter(Integer.parseInt(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("Y")){
                    Updator.addYFilter(Float.parseFloat(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("MINIMAL POINT")){
                    Updator.addMinFilter(Float.parseFloat(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("MAXIMAL POINT")){
                    Updator.addMaxFilter(Long.parseLong(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("DIFFICULTY")){
                    Updator.addDifFilter(Difficulty.valueOf(filterValue.getText()).ordinal(), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("DISCIPLINE NAME")){
                    Updator.addDisNameFilter(filterValue.getText(), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("LECTURE HOURS")){
                    Updator.addDisLecFilter(Integer.parseInt(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("PRACTICE HOURS")){
                    Updator.addDisPracFilter(Integer.parseInt(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("SELFSTUDY HOURS")){
                    Updator.addDisSelfFilter(Long.parseLong(filterValue.getText()), filterType); // значение, тип операции
                }else if (fieldsComboBox.getValue().toUpperCase().equals("AUTHOR")){
                    Updator.addAuthorFilter(filterValue.getText(), filterType); // значение, тип операции
                }else {
                    textArea.appendText("Filter by: " + fieldsComboBox.getValue() + "\nOperation type: " + typesComboBox.getValue() + "\nValue " + filterValue.getText());
                    //textArea.setText("Please enter data to all fields");
                }
            }catch (NumberFormatException e){
                textArea.setText("Incorrect Filter Value Type");
            }
            filterStage.close();
        });

    }
    public static void handleRemove(TextArea textArea, TableView tableView, LabWork lab2del ) {
        try {
            commandInvoker.setComData(String.valueOf(lab2del.getId()));
            commandInvoker.run("REMOVE_BY_ID", ioClient, null);
        } catch (QuitException | IOException e) {
            e.printStackTrace();
        }
        textArea.setText("Labwork " + lab2del.getName() + " is removed");
        textArea.setText(commandInvoker.getAnsverArray().toString());
    }
    public static void handleEdit(TextArea textArea, LabWork lab2edit){
        ObservableList<String> fields = FXCollections.observableArrayList("Id", "Name", "x", "y", "Creation Date", "Minimal Point", "Maximal Point", "Difficulty", "Discipline Name", "Lecture hours", "Practice hours", "Selfstudy hours", "Author");
        ComboBox<String> editFieldsComboBox = new ComboBox<String>(fields);

        Button submitEdit = new Button("Submit");

        TextField editValue = new TextField();

        VBox filterInput;
        try {
            filterInput = new VBox(10, new HBox(new Label("Chosen Lab id: " + lab2edit.getId())), new HBox(new Label("Labwork field"), editFieldsComboBox),  new HBox(new Label("Filter Value"),  editValue), submitEdit);
            filterInput.setAlignment(Pos.CENTER);
            filterInput.setPadding(new Insets(10, 10, 10, 10));
            Stage filterStage = new Stage();
            filterStage.setMaxHeight(800);
            filterStage.setMaxWidth(500);
            FlowPane filterRoot = new FlowPane();
            filterRoot.getChildren().add(filterInput);
            Scene scene = new Scene(filterRoot);
            filterStage.setScene(scene);
            filterStage.setTitle("Table Filter");
            filterStage.setMaximized(false);
            filterStage.show();

            submitEdit.setOnAction((l) -> {
                try{

                    if (editFieldsComboBox.getValue().toUpperCase().equals("ID")){
                        textArea.setText("Cannot edit ID field");

                    }else if (editFieldsComboBox.getValue().toUpperCase().equals("NAME")){
                        commandInvoker.setComData((String.valueOf(lab2edit.getId())) + " " + "NAME " + editValue.getText());
                        commandInvoker.run("UPDATE", ioClient, null);
                        textArea.setText(commandInvoker.getAnsverArray().toString());
                    }
                }catch (NumberFormatException e){
                    textArea.setText("Incorrect Filter Value Type");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (QuitException e) {
                    e.printStackTrace();
                }
                filterStage.close();
            });

        }catch (NullPointerException e){
            textArea.setText("Please choose a lab (click)");
        }
    }
}
