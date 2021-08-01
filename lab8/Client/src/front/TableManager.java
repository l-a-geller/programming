package front;

import data.Coordinates;
import data.Difficulty;
import data.Discipline;
import data.LabWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import tools.commands.CommandInvoker;
import tools.io.QuitException;
import tools.io.StreamReadWriter;

import java.io.IOException;
import java.time.LocalDate;

public class TableManager {
    private TableView<LabWork> table;
    private CommandInvoker commandInvoker;
    private Stage tableStage;
    private FlowPane tableRoot;
    private ObservableList<LabWork> labWorks;
    private StreamReadWriter ioClient;
    public TableManager(CommandInvoker commandInvoker, StreamReadWriter ioClient, Stage tableStage, FlowPane tableRoot) throws IOException, QuitException {
        this.ioClient = ioClient;
        this.tableStage = tableStage;
        this.tableRoot = tableRoot;
        this.commandInvoker = commandInvoker;
        table = new TableView<LabWork>(loadLabs());

        table.setPrefWidth(840);
        table.setPrefHeight(480);
        createColumns(table);
    }
    public ObservableList<LabWork> loadLabs() throws IOException, QuitException {
        //System.out.println(commandInvoker.getAnsverArray());
        Integer id = null;
        String name = null;
        String coordinates = null;
        Float minimalPoint = null;
        Long maximumPoint = null;
        Difficulty difficulty = null;
        String disciplineName = null;
        Integer lecture = null;
        Integer practice = null;
        Long selfStudy = null;
        String author = null;
        ObservableList<LabWork> labs = FXCollections.observableArrayList();
        commandInvoker.run("SHOW", ioClient, null);
        for (String part: commandInvoker.getAnsverArray()){
            if (part.contains("id")){
                id = Integer.parseInt(part.split("id")[1].trim());
            }
            if (part.contains("name")){
                name = part.split("name")[1].trim();
            }
            if (part.contains("coordinates")){
                coordinates = part.split("coordinates")[1].trim();
            }
            if (part.contains("minimalPoint")){
                minimalPoint = Float.parseFloat(part.split("minimalPoint")[1].trim());
            }
            if (part.contains("maximumPoint")){
                maximumPoint = Long.parseLong(part.split("maximumPoint")[1].trim());
            }
            if (part.contains("difficulty")){
                difficulty = Difficulty.valueOf(part.split("difficulty")[1].trim());
            }
            if (part.contains("discipline")){
                disciplineName = part.split("discipline")[1].trim();
            }
            if (part.contains("lecture_hours")){
                lecture = Integer.parseInt(part.split("lecture_hours")[1].trim());
            }
            if (part.contains("practice_hours")){
                practice = Integer.parseInt(part.split("practice_hours")[1].trim());
            }
            if (part.contains("selfstudy_hours")){
                selfStudy = Long.parseLong(part.split("selfstudy_hours")[1].trim());
            }
            if (part.contains("author")){
                author =part.split("author")[1].trim();
                LabWork l = new LabWork(name, new Coordinates(coordinates), minimalPoint, maximumPoint, difficulty, new Discipline(disciplineName, lecture, practice, selfStudy));
                l.setAuthor(author);
                l.setId(id);
                labs.add(l);
            }
        }
        labWorks = labs;
        return labWorks;
    }
    public ObservableList<LabWork> getLabWorks(){
        return labWorks;
    }
    public TableView<LabWork> getTable(){
        return table;
    }
    public TableColumn<LabWork, Integer> idColumn;
    public TableColumn<LabWork, String> nameColumn;
    public TableColumn<LabWork, Integer> xColumn;
    public TableColumn<LabWork, Float> yColumn;
    public TableColumn<LabWork, LocalDate> dateColumn;
    public TableColumn<LabWork, Float> minColumn;
    public TableColumn<LabWork, Long> maxColumn;
    public TableColumn<LabWork, Difficulty> difficultyColumn;
    public TableColumn<LabWork, String> disciplineNameColumn;
    public TableColumn<LabWork, Integer> disciplineLectureColumn;
    public TableColumn<LabWork, Integer> disciplinePracticeColumn;
    public TableColumn<LabWork, Long> disciplineSelfStudyColumn;
    public TableColumn<LabWork, String> authorColumn;

    private void createColumns(TableView<LabWork> table){
        //creates id column
        idColumn = new TableColumn<LabWork, Integer>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Integer>("id"));
        table.getColumns().add(idColumn);

        //creates name column
        nameColumn = new TableColumn<LabWork, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<LabWork, String>("name"));
        table.getColumns().add(nameColumn);

        //creates x coordinate column
        xColumn = new TableColumn<LabWork, Integer>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Integer>("coordinate_x"));
        table.getColumns().add(xColumn);

        //creates y coordinate column
        yColumn = new TableColumn<LabWork, Float>("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Float>("coordinate_y"));
        table.getColumns().add(yColumn);

        //creates creation date column
        dateColumn = new TableColumn<LabWork, LocalDate>("Creation date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<LabWork, LocalDate>("date"));
        table.getColumns().add(dateColumn);

        //creates minimal point column
        minColumn = new TableColumn<LabWork, Float>("Minimal Point");
        minColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Float>("min"));
        table.getColumns().add(minColumn);

        //creates maximal point column
        maxColumn = new TableColumn<LabWork, Long>("Maximal Point");
        maxColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Long>("max"));
        table.getColumns().add(maxColumn);

        //creates difficulty column
        difficultyColumn = new TableColumn<LabWork, Difficulty>("Difficulty");
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Difficulty>("diff"));
        table.getColumns().add(difficultyColumn);

        //creates discipline name column
        disciplineNameColumn = new TableColumn<LabWork, String>("Discipline name");
        disciplineNameColumn.setCellValueFactory(new PropertyValueFactory<LabWork, String>("disname"));
        table.getColumns().add(disciplineNameColumn);

        //creates discipline lecture hours column
        disciplineLectureColumn = new TableColumn<LabWork, Integer>("lecture_hours");
        disciplineLectureColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Integer>("dislec"));
        table.getColumns().add(disciplineLectureColumn);

        //creates discipline practice hours column
        disciplinePracticeColumn = new TableColumn<LabWork, Integer>("practice_hours");
        disciplinePracticeColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Integer>("disprac"));
        table.getColumns().add(disciplinePracticeColumn);

        //creates discipline self study hours column
        disciplineSelfStudyColumn = new TableColumn<LabWork, Long>("selfstudy_hours");
        disciplineSelfStudyColumn.setCellValueFactory(new PropertyValueFactory<LabWork, Long>("disself"));
        table.getColumns().add(disciplineSelfStudyColumn);

        //creates author column
        authorColumn = new TableColumn<LabWork, String>("author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<LabWork, String>("author"));
        table.getColumns().add(authorColumn);
    }
}
