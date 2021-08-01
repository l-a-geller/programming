package tools;

import java.io.Serializable;

public abstract class Command extends AbstractCommand implements Serializable {

    public boolean needsScanner = false;
    public boolean needsExecutor = false;
    public String data;
    public boolean hasData;
    public String res;

    protected Command(String name, String description){
        super(name, description);
        res = "Answer from server " + name.toUpperCase() + " >>> ";
    }

    public void execute() throws ArrayIndexOutOfBoundsException{
        throw new ArrayIndexOutOfBoundsException();
        //return "";
    }
    public void execute(String data) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }

    public String getAnswer() {
        String res = "";
        return res;
    }
}
