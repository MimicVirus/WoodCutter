package task.impl;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.map.Area;
import task.AbstractTask;

public class FireNode extends AbstractTask {

    @Override
    public boolean accept() {
        return Inventory.contains("Tinderbox") && config.getIsBurning();
    }

    @Override
    public int execute() {
        if (!Inventory.contains("Logs")) {
            config.setIsBurning(false);
        }

        if (!config.isScriptRunning()) {
            scriptManager.stop();
        }

        config.setStatus("Burning Logs");
        if (Inventory.contains("logs") && getLocalPlayer().isStandingStill()) {
            Inventory.get("Tinderbox").useOn("logs");
            sleepUntil(() -> getLocalPlayer().isMoving(), 20000);
        }

        return Calculations.random(500, 2500);
    }
}