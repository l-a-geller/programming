package tools.commands;

import java.util.Scanner;

public abstract class AbstractCommand{

    private String name;
    private String description;
    protected boolean needsScanner = false;
    protected Scanner sc;

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public boolean doesNeedScanner() {
        return needsScanner;
    }

    protected AbstractCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public void execute() throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
    }
    public void execute(String data) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }
    public void execute(Scanner scanner) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }
}