package core;

import config.Config;
import org.dreambot.api.Client;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import paint.DrawMouseUtil;
import task.impl.ChopNode;
import task.impl.FireNode;
import utils.LogToConsoleUtil;

import java.awt.*;
@ScriptManifest(category = Category.WOODCUTTING, name = "woodcutter", author = "MimicVirus", version = 1.0, description = "A simple script to farm low levels for woodcutting and fire making at the GE")
public class Main extends TaskScript implements ChatListener {
    private final DrawMouseUtil _drawMouseUtil = new DrawMouseUtil();
    private final Area _randomArea = new Area(3151, 3501, 3162, 3491);
    public static Timer timer;
    public static int chopped;
    public static int burned;

    @Override
    public void onStart() {
        super.onStart();
        Config.getConfig().setStatus("Walking to trees");
        boolean initialized = sleepUntil(() ->
                Client.isLoggedIn() && (Players.localPlayer().getX() > 0), 20000);
        if (initialized) {
            timer = new Timer();
            _drawMouseUtil.setTrailColor(Color.ORANGE);
            _drawMouseUtil.setCursorColor(Color.ORANGE);
            addNodes(new FireNode(), new ChopNode());
            Config.getConfig().setScriptRunning(true);
            LogToConsoleUtil.onLogInfo("Bot is online");
        }
    }

    @Override
    public void onMessage(Message message) {
        ChatListener.super.onMessage(message);
        if (message.getMessage().contains("You get some")) {
            chopped++;
        } else if (message.getMessage().contains("The fire catches")) {
            burned++;
        } else if (message.getMessage().contains("You can't light a fire here.")) {
            Walking.walk(_randomArea.getRandomTile());
            sleepUntil(() ->
                    !Players.localPlayer().isMoving(), 10000);
        }
    }

    @Override
    public void onExit() {
        super.onExit();
        Config.getConfig().setScriptRunning(false);
        LogToConsoleUtil.onLogInfo("Bot has gone offline");
    }

    @Override
    public void onPaint(Graphics graphics) {
        super.onPaint(graphics);
        _drawMouseUtil.drawTrail(graphics);
        _drawMouseUtil.drawRotatingCircleMouse(graphics);

        paint.Paint.onDisplayExpPopups(graphics);
        paint.Paint.onRepaint(graphics);
    }
}
