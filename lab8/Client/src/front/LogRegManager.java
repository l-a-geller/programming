package front;

import data.Coordinates;
import data.Difficulty;
import data.Discipline;
import data.LabWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tools.commands.CommandInvoker;
import tools.io.QuitException;
import tools.io.StreamReadWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class LogRegManager {
    private FlowPane root = new FlowPane();
    private CommandInvoker commandInvoker;
    private StreamReadWriter iOclient;
    private TableManager tableManager;
    private String login;

    Button addButton = new Button("Add LabWork");
    Button clearButton = new Button("Clear Table");
    Button infoButton = new Button("Info");
    Button historyButton = new Button("History");
    Button removeButton = new Button("Remove LabWork");
    Button filterButton = new Button("Filter Table");
    Button filterClearButton = new Button("Clear Filters");
    Button editButton = new Button("Edit LabWork");

    Label title;
    Button buttonlog;
    Button buttonreg;
    TextField textField;
    PasswordField passwordField;
    Label hint;

    Label userName;
    Button quitButton;
    Button logOutButton;

    TextArea textArea = new TextArea();
    int hintPointer = 0;

    public LogRegManager(CommandInvoker commandInvoker, StreamReadWriter iOclient, Stage primaryStage){

        this.iOclient = iOclient;
        this.commandInvoker = commandInvoker;
        //Authorization screen
        ObservableList<String> langs = FXCollections.observableArrayList("русский", "Português", "shqiptar", "English (AU)");
        ComboBox<String> langsComboBox = new ComboBox<String>(langs);
        langsComboBox.setPromptText("Language");
        langsComboBox.setOnAction((event) -> {
            try {
                loadLangLog(langsComboBox.getValue());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        title = new Label("WELCOME TO JAVAFX CLIENT");
        title.setFont(Font.font("Roboto", 16));
        title.setPrefHeight(20); title.setPrefWidth(220);
        title.setAlignment(Pos.CENTER);
        buttonlog = new Button("Login");
        buttonreg = new Button("Register");
        buttonlog.setPrefHeight(20); buttonlog.setPrefWidth(105);
        buttonreg.setPrefHeight(20); buttonreg.setPrefWidth(105);
        textField = new TextField();
        textField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        hint = new Label("Enter Username and Password and click Login button to log in. If you are not Registered enter your registration Username and Password and click Register.");
        hint.setTextFill(Color.rgb(255,0,0));
        hint.setWrapText(true);
        hint.setTextAlignment(TextAlignment.CENTER);
        hint.setAlignment(Pos.CENTER);
        hint.setMaxWidth(225);

        VBox vbox = new VBox(10, title, textField, passwordField, new HBox(10, buttonlog, buttonreg), hint, langsComboBox); vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().add(vbox);

        buttonlog.setOnAction((ae) -> {
            commandInvoker.setData(textField.getText(), passwordField.getText());
            try {
                boolean logged = commandInvoker.run("LOGIN", iOclient, new Scanner(System.in));
                if (!logged){
                    hint.setText("Wrong username or password. Please check and try again");
                    hintPointer = 1;
                }else {
                    login = textField.getText();
                    goMainTable(primaryStage);
                }
            } catch (QuitException | IOException e) {
                e.printStackTrace();
            }
        });

        buttonreg.setOnAction((ae) -> {
            commandInvoker.setData(textField.getText(), passwordField.getText());
            try {
                if (commandInvoker.run("REGISTER", iOclient, new Scanner(System.in))){
                    hint.setText("User successfully registered");
                    hintPointer = 2;
                }else {
                    hint.setText("Sorry, username is taken, try another one");
                    hintPointer = 3;
                }
            } catch (QuitException | IOException e) {
                e.printStackTrace();
            }
        });
        root.setAlignment(Pos.CENTER);
    }
    public FlowPane getRoot(){
        return root;
    }
    private void goMainTable(Stage primaryStage) throws IOException, QuitException {

        FlowPane tableRoot = new FlowPane();
        commandInvoker.run("SHOW", iOclient, new Scanner(System.in));

        Stage tableStage = new Stage();

        tableManager = new TableManager(commandInvoker, iOclient, tableStage, tableRoot);
        CanvasHandler canvasHandler = new CanvasHandler();
        Canvas canvas = canvasHandler.getCanvas();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Updator.setConfiguration(tableManager, canvasHandler);
                try {
                    Updator.updateTable();
                } catch (IOException | QuitException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread updator = new Thread(r);
        updator.start();

        final LabWork[] clickedLab = {null};
        TableView<LabWork> table = tableManager.getTable();
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                clickedLab[0] = (LabWork) table.getSelectionModel().getSelectedItem();;
            }
        };
        //Registering the event filter
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        textArea.setPrefHeight(80);
        CommandButtonHandler commandButtonHandler = new CommandButtonHandler(commandInvoker, iOclient);

        TextField nameField = new TextField(); nameField.setPromptText("name");
        TextField coordinateXField = new TextField(); coordinateXField.setPromptText("X coordinate");
        TextField coordinateYField = new TextField(); coordinateYField.setPromptText("Y coordinate");
        TextField minimalField = new TextField(); minimalField.setPromptText("Minimal point");
        TextField maximalField = new TextField(); maximalField.setPromptText("Maximal point");
        TextField difficultyField = new TextField(); difficultyField.setPromptText("Difficulty");
        TextField disciplineNameField = new TextField(); disciplineNameField.setPromptText("Discipline Name");
        TextField disciplineLectureField = new TextField(); disciplineLectureField.setPromptText("Discipline Lecture Hours");
        TextField disciplinePracticeField = new TextField(); disciplinePracticeField.setPromptText("Discipline Practice Hours");
        TextField disciplineSelfStudyField = new TextField(); disciplineSelfStudyField.setPromptText("Discipline SelfStudy Hours");
        Button submitLab = new Button("Submit");

        VBox interactiveInput = new VBox(10, new Label("Labwork input"), nameField, coordinateXField, coordinateYField, minimalField, maximalField, difficultyField, disciplineNameField, disciplineLectureField, disciplinePracticeField, disciplineSelfStudyField, submitLab);
        interactiveInput.setAlignment(Pos.CENTER);

        userName = new Label("User: \n" + login); userName.setPadding(new Insets(10, 10, 10, 10));
        quitButton = new Button("Quit"); quitButton.setMaxHeight(10); quitButton.setOnAction((l) -> System.exit(0));

        logOutButton = new Button("Log Out"); logOutButton.setOnAction((l) -> {tableStage.close(); primaryStage.show();});

        ObservableList<String> langs = FXCollections.observableArrayList("русский", "Português", "shqiptar", "English (AU)");
        ComboBox<String> langsComboBox = new ComboBox<String>(langs); quitButton.setPrefHeight(15);//langsComboBox.setPadding(new Insets(10, 10, 10, 10));
        langsComboBox.setPromptText("Language");
        HBox userBox = new HBox(10, userName, quitButton, logOutButton, langsComboBox); userBox.setAlignment(Pos.CENTER);
        userBox.setMaxHeight(70);
        userBox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1))));


        VBox userNCanvas = new VBox(10, userBox, canvas);
        VBox vBox = new VBox(10, new HBox(10, table, userNCanvas), new HBox(10, addButton, clearButton, infoButton, historyButton, removeButton, filterButton, filterClearButton), textArea);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        langsComboBox.setOnAction((event) -> {
            try {
                loadLang(langsComboBox.getValue(), textArea);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        addButton.setOnAction((l) -> {

            Stage inputStage = new Stage();
            inputStage.setMaxHeight(500);
            inputStage.setMaxWidth(185);
            FlowPane inputRoot = new FlowPane();
            interactiveInput.setAlignment(Pos.CENTER);
            interactiveInput.setPadding(new Insets(10, 10, 10, 10));
            inputRoot.getChildren().add(interactiveInput);
            Scene scene = new Scene(inputRoot);
            inputStage.setScene(scene);
            inputStage.setTitle("TableView in JavaFX");
            inputStage.setMaximized(false);
            inputStage.show();

            submitLab.setOnAction((ll) -> {
                LabWork lab = null;
                String sqlString = "";
                ObservableList<LabWork> labWorks = tableManager.getLabWorks();
                String name = null;
                Integer xCoord  = null;
                Float yCoord  = null;
                Float minPoint = null;
                Long maxPoint = null;
                Difficulty difficulty = null;
                Integer difOrd = null;
                Discipline discipline = null;
                String disName = null;
                Integer disPrac = null;
                Integer disLec = null;
                Long disSelfS = null;
                try {
                    textArea.setText("Lab Name must be Unique");
                    name = nameField.getText();
                    if (name == null | name.equals("")){ textArea.setText("Name cannot be null"); }

                    try{
                        xCoord = Integer.parseInt(coordinateXField.getText());
                        yCoord = Float.parseFloat(coordinateYField.getText());
                        if (xCoord == null || yCoord == null || xCoord < -175 || xCoord > 175 || !(yCoord > -222) || !(yCoord < 222)){throw new NumberFormatException();}
                    }catch (NumberFormatException e){
                        textArea.setText("Coordinates cannot be null,\ncoordinate X must be an integer, in [" + -175 + ":" + 175 + "]\ncoordinate Y must be a float, in (" + -222 + ":" + 222 + ")");
                    }

                    try {
                        minPoint = Float.parseFloat(minimalField.getText());
                        maxPoint = Long.parseLong(maximalField.getText());
                        if (!(maxPoint > minPoint)){
                            minPoint = null;
                            maxPoint = null;
                        }
                        if (minPoint == null || maxPoint == null || !(maxPoint > minPoint)){ throw new NumberFormatException(); }
                    }catch (NumberFormatException e){
                        textArea.setText("Minimal and Maximum Points cannot be null, \nMinimal Point must be Float in [" + -Float.MAX_VALUE + ":" + Float.MAX_VALUE + "]\nMaximum Point must be a Long in [" + -Long.MAX_VALUE + ":" + Long.MAX_VALUE + "]\nMaximum Point must be greater than Minimal Point");
                    }

                    try{
                        if (difficultyField.getText().equals("")){
                            difficulty = null;
                            difOrd = null;
                        }else {
                            difficulty = Difficulty.valueOf(difficultyField.getText());
                            difOrd = difficulty.ordinal();
                        }
                    } catch (NullPointerException | IllegalArgumentException e) {}

                    try {
                        if (disciplineNameField.getText().equals("") & disciplinePracticeField.getText().equals("") & disciplineLectureField.getText().equals("") & disciplineSelfStudyField.getText().equals("")){
                            discipline = new Discipline(null, null, null, null);
                        }else{
                            disName = disciplineNameField.getText();
                            disPrac = Integer.parseInt(disciplinePracticeField.getText());
                            disLec = Integer.parseInt(disciplineLectureField.getText());
                            disSelfS = Long.parseLong(disciplineSelfStudyField.getText());
                            discipline = new Discipline(disName, disPrac, disLec, disSelfS);
                        }
                    }catch (NumberFormatException e){

                        throw new IllegalArgumentException();
                    }

                    sqlString = name + "," + xCoord + "," + yCoord + "," + minPoint + "," + maxPoint + "," + difOrd +"," + disName + "," + disPrac + "," + disLec + "," + disSelfS;
                    lab = new LabWork(name, new Coordinates((xCoord + ":" + yCoord)), minPoint, maxPoint, difficulty, discipline); //new Discipline(disName, disPrac, disLec, disSelfS));
                    lab.setAuthor(login);
                    CommandButtonHandler.handleAdd(sqlString, canvasHandler);
                    canvasHandler.drawLab(lab);
                    textArea.setText("Labwork successfully added");

                    inputStage.close();
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                    if (!difficultyField.getText().equals("") & !(difficultyField.getText().equals("IMPOSSIBLE") | difficultyField.getText().equals("VERY_EASY") | difficultyField.getText().equals("VERY_HARD"))) textArea.setText("Difficulty must be a constant in: VERY_EASY, " + "VERY_HARD, " + "IMPOSSIBLE");
                    else if (!disciplineNameField.getText().equals("") | !disciplinePracticeField.getText().equals("") | !disciplineLectureField.getText().equals("") | !disciplineSelfStudyField.getText().equals("")){
                        textArea.setText("Discipline name must be a not null String \n" +
                                "Discipline lecture hours must be an Integer \n" +
                                "Discipline practice hours must be an Integer \n" +
                                "Discipline selfStudy hours must be a Long");
                    }
                }
            });
        });
        infoButton.setOnAction((l) -> {CommandButtonHandler.handleInfo(textArea);});
        filterButton.setOnAction((l) -> {CommandButtonHandler.handleFilter(textArea);});
        filterClearButton.setOnAction((l) -> {CommandButtonHandler.handleClearFilter(textArea);});
        historyButton.setOnAction((l) -> {CommandButtonHandler.handleHistory(textArea);});
        editButton.setOnAction((l) -> {CommandButtonHandler.handleEdit(textArea, clickedLab[0]);});
        removeButton.setOnAction((l) -> CommandButtonHandler.handleRemove(textArea, tableManager.getTable(), clickedLab[0]));
        clearButton.setOnAction((l) -> {
            try {

                CommandButtonHandler.handleClear();
                ObservableList<LabWork> labWorks = tableManager.getLabWorks();
                Iterator iter = labWorks.iterator();
                while (iter.hasNext()){
                    LabWork lab = (LabWork) iter.next();
                    if (lab.getAuthor().equals(login)){iter.remove();}
                }

            } catch (IOException | QuitException e) {
                e.printStackTrace();
            }
        });

        tableRoot.getChildren().add(vBox);
        Scene scene = new Scene(tableRoot);
        tableStage.setScene(scene);
        tableStage.setTitle("TableView in JavaFX");
        tableStage.setMaximized(true);
        tableStage.show();
        primaryStage.close();
    }

    private Locale locale;
    private ResourceBundle bundle;
    public void loadLang(String lang, TextArea textArea) throws UnsupportedEncodingException {

        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("app", locale);
        textArea.setText("LOCALE "+(locale.toString()));
        if(!locale.toString().contains("po")){
            if (locale.toString().contains("au")) bundle = ResourceBundle.getBundle("app", new Locale("en")); //bundle = ResourceBundle.getBundle("app", new Locale("en"));
            else if (locale.toString().equals("русский")) bundle = ResourceBundle.getBundle("app", new Locale("ru"));
            else if (locale.toString().contains("sh"))  bundle = ResourceBundle.getBundle("app", new Locale("sq"));
            infoButton.setText(new String(bundle.getString("infoCommand").getBytes("ISO-8859-1"), "UTF-8"));
            addButton.setText(new String(bundle.getString("addCommand").getBytes("ISO-8859-1"), "UTF-8"));
            clearButton.setText(new String(bundle.getString("clearCommand").getBytes("ISO-8859-1"), "UTF-8"));
            filterButton.setText(new String(bundle.getString("filterCommand").getBytes("ISO-8859-1"), "UTF-8"));
            filterClearButton.setText(new String(bundle.getString("filterClearCommand").getBytes("ISO-8859-1"), "UTF-8"));
            removeButton.setText(new String(bundle.getString("removeCommand").getBytes("ISO-8859-1"), "UTF-8"));
            historyButton.setText(new String(bundle.getString("historyCommand").getBytes("ISO-8859-1"), "UTF-8"));
            userName.setText(new String(bundle.getString("userName").getBytes("ISO-8859-1"), "UTF-8") + " " + login);
            quitButton.setText(new String(bundle.getString("quitButton").getBytes("ISO-8859-1"), "UTF-8"));
            logOutButton.setText(new String(bundle.getString("logOutButton").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.idColumn.setText(new String(bundle.getString("idColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.nameColumn.setText(new String(bundle.getString("nameColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.xColumn.setText(new String(bundle.getString("xColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.yColumn.setText(new String(bundle.getString("yColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.dateColumn.setText(new String(bundle.getString("dateColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.minColumn.setText(new String(bundle.getString("minColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.maxColumn.setText(new String(bundle.getString("maxColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.difficultyColumn.setText(new String(bundle.getString("difficultyColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.disciplineNameColumn.setText(new String(bundle.getString("disNameColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.disciplinePracticeColumn.setText(new String(bundle.getString("disPracColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.disciplineLectureColumn.setText(new String(bundle.getString("disLecColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.disciplineSelfStudyColumn.setText(new String(bundle.getString("disSelfColumn").getBytes("ISO-8859-1"), "UTF-8"));
            tableManager.authorColumn.setText(new String(bundle.getString("authorColumn").getBytes("ISO-8859-1"), "UTF-8"));

        }else {
            bundle = ResourceBundle.getBundle("app", new Locale("pt"));
            infoButton.setText((bundle.getString("infoCommand")));
            addButton.setText(bundle.getString("addCommand"));
            clearButton.setText(bundle.getString("clearCommand"));
            filterButton.setText(bundle.getString("filterCommand"));
            filterClearButton.setText(bundle.getString("filterClearCommand"));
            removeButton.setText(bundle.getString("removeCommand"));
            historyButton.setText(bundle.getString("historyCommand"));
            userName.setText(bundle.getString("userName") + " " + login);
            quitButton.setText(bundle.getString("quitButton"));
            logOutButton.setText(bundle.getString("logOutButton"));
            tableManager.idColumn.setText((bundle.getString("idColumn")));
            tableManager.nameColumn.setText(bundle.getString("nameColumn"));
            tableManager.xColumn.setText(bundle.getString("xColumn"));
            tableManager.yColumn.setText(bundle.getString("yColumn"));
            tableManager.dateColumn.setText(bundle.getString("dateColumn"));
            tableManager.minColumn.setText(bundle.getString("minColumn"));
            tableManager.maxColumn.setText(bundle.getString("maxColumn"));
            tableManager.difficultyColumn.setText(bundle.getString("difficultyColumn"));
            tableManager.disciplineNameColumn.setText(bundle.getString("disNameColumn"));
            tableManager.disciplinePracticeColumn.setText(bundle.getString("disPracColumn"));
            tableManager.disciplineLectureColumn.setText(bundle.getString("disLecColumn"));
            tableManager.disciplineSelfStudyColumn.setText(bundle.getString("disSelfColumn"));
            tableManager.authorColumn.setText(bundle.getString("authorColumn"));

        }
    }

    private void loadLangLog(String lang) throws UnsupportedEncodingException {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("app", locale);
        textArea.setText("LOCALE "+(locale.toString()));
        if (locale.toString().contains("po")){
            bundle = ResourceBundle.getBundle("app", new Locale("pt"));
            title.setText(bundle.getString("title"));
            buttonlog.setText(bundle.getString("buttonlog"));
            buttonreg.setText(bundle.getString("buttonreg"));
            textField.setPromptText(bundle.getString("textField"));
            passwordField.setPromptText(bundle.getString("passwordField"));
            if (hintPointer == 0) hint.setText((bundle.getString("hint0")));
            if (hintPointer == 1) hint.setText((bundle.getString("hint1")));
            if (hintPointer == 2) hint.setText((bundle.getString("hint2")));
            if (hintPointer == 3) hint.setText((bundle.getString("hint3")));

        }else {
            if (locale.toString().equals("русский")){
            bundle = ResourceBundle.getBundle("app", new Locale("ru"));
            }else if (locale.toString().contains("au")){
                bundle = ResourceBundle.getBundle("app", new Locale("en"));
            }else if (locale.toString().contains("sh")){
                bundle = ResourceBundle.getBundle("app", new Locale("sq"));
            }
            title.setText(new String(bundle.getString("title").getBytes("ISO-8859-1"), "UTF-8"));
            buttonlog.setText(new String(bundle.getString("buttonlog").getBytes("ISO-8859-1"), "UTF-8"));
            buttonreg.setText(new String(bundle.getString("buttonreg").getBytes("ISO-8859-1"), "UTF-8"));
            textField.setPromptText(new String(bundle.getString("textField").getBytes("ISO-8859-1"), "UTF-8"));
            passwordField.setPromptText(new String(bundle.getString("passwordField").getBytes("ISO-8859-1"), "UTF-8"));

            if (hintPointer == 0) hint.setText(new String(bundle.getString("hint0").getBytes("ISO-8859-1"), "UTF-8"));
            if (hintPointer == 1) hint.setText(new String(bundle.getString("hint1").getBytes("ISO-8859-1"), "UTF-8"));
            if (hintPointer == 2) hint.setText(new String(bundle.getString("hint2").getBytes("ISO-8859-1"), "UTF-8"));
            if (hintPointer == 3) hint.setText(new String(bundle.getString("hint3").getBytes("ISO-8859-1"), "UTF-8"));
        }
    }
}
