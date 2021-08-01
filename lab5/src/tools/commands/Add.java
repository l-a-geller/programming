package tools.commands;

import data.HumanBeing;
import javafx.scene.control.PasswordField;
import tools.execution.Loader;

import java.util.Scanner;

public class Add extends AbstractCommand {
    public Add() {
        super("Add", "Добавить новый элемент в коллекцию");
        needsScanner = true;
    }

    @Override
    public void execute(Scanner sc) {
        Loader loader = new Loader();
        HumanBeing hum = loader.search(sc);
        loader.load(hum);
    }
}
