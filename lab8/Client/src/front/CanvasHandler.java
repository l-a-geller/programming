package front;

import data.LabWork;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasHandler {
    private Canvas canvas;
    private ArrayList<Color> colors = new ArrayList<>();
    private HashMap<String, Color> authorToColor = new HashMap<>();
    GraphicsContext gc;
    private float x = 197.5f;
    private float y = 207.5f;
    public CanvasHandler(){

        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.BLACK);
        colors.add(Color.AQUA);
        colors.add(Color.AQUAMARINE);
        colors.add(Color.ORANGE);

        canvas = new Canvas(400, 420);

        gc = canvas.getGraphicsContext2D();
        drawgrid();
    }
    public Canvas getCanvas(){
        return canvas;
    }
    public void updateLab(LabWork labWork){
        if (authorToColor.containsKey(labWork.getAuthor())){
            gc.setFill(authorToColor.get(labWork.getAuthor()));
        }else {
            gc.setFill(getColor(labWork));
        }
        gc.strokeOval(labWork.getCoordinates().getX() + x, (-labWork.getCoordinate_y() + y), 5, 5);
        gc.fillOval(labWork.getCoordinates().getX() + x, (-labWork.getCoordinate_y() + y), 5, 5);

    }
    public void drawLab(LabWork labWork){
        Long time1 = System.currentTimeMillis();
        Color currentColor;
        if (authorToColor.containsKey(labWork.getAuthor())){
            currentColor = authorToColor.get(labWork.getAuthor());
            gc.setFill(currentColor);
        }else {
            currentColor = getColor(labWork);
            gc.setFill(currentColor);
        }

        while ((System.currentTimeMillis() - time1) < 2 ){
            gc.strokeOval(labWork.getCoordinates().getX() + x -12.5 , (-labWork.getCoordinate_y() + y - 12.5), 30, 30);
            gc.fillOval(labWork.getCoordinates().getX() + x -12.5, (-labWork.getCoordinate_y() + y - 12.5), 30, 30);
            gc.setFill(Color.WHITE);
            gc.strokeOval(labWork.getCoordinates().getX() + x -10 , (-labWork.getCoordinate_y() + y - 10), 25, 25);
            gc.fillOval(labWork.getCoordinates().getX() + x -10, (-labWork.getCoordinate_y() + y - 10), 25, 25);
            gc.setFill(currentColor);
        }
        time1 = System.currentTimeMillis();
        while ((System.currentTimeMillis() - time1) < 2 ){
            gc.strokeOval(labWork.getCoordinates().getX() + x -7.5 , (-labWork.getCoordinate_y() + y - 7.5), 20, 20);
            gc.fillOval(labWork.getCoordinates().getX() + x -7.5, (-labWork.getCoordinate_y() + y - 7.5), 20, 20);
            gc.setFill(Color.WHITE);
            gc.strokeOval(labWork.getCoordinates().getX() + x -5 , (-labWork.getCoordinate_y() + y - 5), 15, 15);
            gc.fillOval(labWork.getCoordinates().getX() + x -5, (-labWork.getCoordinate_y() + y - 5), 15, 15);
            gc.setFill(currentColor);
        }

        updateLab(labWork);
    }

    public Color getColor(LabWork labWork){
        Color c = colors.get((int)(Math.random() * (colors.size() - 1)));
        authorToColor.put(labWork.getAuthor(), c);
        colors.remove(c);
        return c;
    }
    public void clearCanvas(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public void drawgrid(){
        gc.moveTo(1, 1);
        gc.lineTo(1, canvas.getHeight());
        gc.stroke();

        gc.moveTo(1, canvas.getHeight());
        gc.lineTo(canvas.getWidth(), canvas.getHeight());
        gc.stroke();

        gc.moveTo(canvas.getWidth(), canvas.getHeight());
        gc.lineTo(canvas.getWidth(), 1);
        gc.stroke();

        gc.moveTo(canvas.getWidth(), 1);
        gc.lineTo(1, 1);
        gc.stroke();

        gc.moveTo(1, canvas.getHeight()/2);
        gc.lineTo(canvas.getWidth(), canvas.getHeight()/2);
        gc.stroke();

        gc.moveTo(canvas.getWidth()/2, 1);
        gc.lineTo(canvas.getWidth()/2, canvas.getHeight());
        gc.stroke();
    }
}
