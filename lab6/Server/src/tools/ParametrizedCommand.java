package tools;

import java.util.Scanner;

public class ParametrizedCommand extends AbstractCommand {
    protected ParametrizedCommand(String name, String description){
        super(name, description);
    }
    public void execute(Scanner sc) throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
    }
}