import java.util.HashMap;
import java.util.Map;

/**
 * Created by ramandika on 28/11/15.
 */
public class Command {
    String command;
    Map<String,String> keyvalue;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, String> getKeyvalue() {
        return keyvalue;
    }

    public Command(){
        keyvalue=new HashMap();
    }
}