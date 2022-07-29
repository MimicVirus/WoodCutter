package task.impl;

import core.Main;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;
import task.AbstractTask;

public class ChopNode extends AbstractTask {
    private final Area TREES = new Area(3142, 3515, 3153, 3493);

    @Override
    public boolean accept() {
        return Inventory.contains("Tinderbox") && !config.getIsBurning();
    }

    @Override
    public int execute() {
        if (TREES.contains(getLocalPlayer())) {
            if (Inventory.isFull()) {
                config.setIsBurning(true);
            }
            config.setStatus("Chopping Trees");

            if (!config.isScriptRunning()) {
                scriptManager.stop();
            }

            sleep(Calculations.random(340, 560));
            GameObject tree = GameObjects.closest(t ->
                    t.getName().contains("Tree") && t.hasAction("Chop down") && TREES.contains(t));
            if (tree != null && tree.interact("Chop down")) {
                sleepUntil(() ->
                        !getLocalPlayer().isMoving(), 3000);
                if (getLocalPlayer().isAnimating()) {
                    sleepUntil(() ->
                            !getLocalPlayer().isAnimating(), 12000);
                }
            }
        } else if (Walking.shouldWalk() && !getLocalPlayer().isMoving()) {
            if (!Walking.isRunEnabled() && Walking.getRunEnergy() > Calculations.random(12, 25)) {
                Walking.toggleRun();
            }
            do {
                Walking.walk(TREES.getRandomTile());
                walkDelay();
            } while (!TREES.contains(Players.localPlayer()));
        }

        return Calculations.random(500, 2500);
    }

    private void walkDelay() {
        Main.sleepUntil(() ->
                Walking.getDestinationDistance() < Calculations.random(3, 8), Calculations.random(3000, 6000));
    }
}