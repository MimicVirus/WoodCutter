package task;

import config.Config;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.TaskNode;

public abstract class AbstractTask extends TaskNode {
    protected ScriptManager scriptManager = ScriptManager.getScriptManager();
    protected Config config = Config.getConfig();
    public abstract boolean accept();
    public abstract int execute();
}