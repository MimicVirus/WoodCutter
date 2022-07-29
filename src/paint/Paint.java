package paint;

import config.Config;
import core.Main;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.world.Worlds;
import utils.TimeUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.stream.IntStream;

import static java.lang.Math.toIntExact;
import static org.dreambot.api.Client.getLocalPlayer;

public class Paint {
    // region EXP Popup Paint Variables
    private static boolean expPaint = false;
    private static long expStart = 0L;
    private static int expGained = 0;
    private static int expTotal = 0;
    private static double expY = 160;
    public static final boolean expShow = true;
    private static final double expX = 270;
    //endregion
    public static void onDisplayExpPopups(Graphics graphics) {
        if (expShow) {
            if (expTotal == 0) {
                expTotal = getTotalExp();
            }
            if (expPaint && Config.getConfig()
                    .isScriptRunning()) {
                if (System.currentTimeMillis() - expStart <= 3000 && expGained != 0) {
                    String gained = "+" + expGained + " EXP";
                    graphics.setColor(Color.black);
                    graphics.drawString(gained, (int) expX + 1, (int) expY + 1);
                    graphics.setColor(Color.green);
                    graphics.drawString(gained, (int) expX, (int) expY);
                    expY -= 0.5;
                } else {
                    expY = 160;
                    expPaint = false;
                }
            }

            if (getTotalExp() != expTotal) {
                expPaint = true;
                expStart = System.currentTimeMillis();
                expGained = getTotalExp() - expTotal;
                expTotal = getTotalExp();
                expY = 160;
            }
        }
    }

    private static int getTotalExp() {
        Skill[] skills = Skill.values();
        int exp = 0;
        for (Skill skill : skills) {
            exp += Skills.getExperience(skill);
        }

        return exp;
    }

    private static final Color color1 = new Color(153, 153, 153);
    private static final Color color2 = new Color(0, 0, 0);
    private static final BasicStroke stroke1 = new BasicStroke(1);
    private static final Font font1 = new Font("Arial", 1, 12);

    public static void onRepaint(Graphics g1) {
        Graphics2D g2D = (Graphics2D)g1;
        if (Main.timer != null) {
            String timeString = TimeUtil.getTimeUtil().getFormattedTimeLeft(Main.timer);

            g2D.setColor(color1);
            g2D.fillRoundRect(371, 278, 146, 60, 16, 16);
            g2D.setColor(color2);
            g2D.setStroke(stroke1);
            g2D.drawRoundRect(371, 278, 146, 60, 16, 16);
            g2D.setFont(font1);
            g2D.drawString("Time Elapsed: " + timeString, 380, 295);
            g2D.drawString("Trees Chopped : " + Main.chopped, 380, 308);
            g2D.drawString("Logs Burned: " + Main.burned, 380, 320);
            g2D.drawString("Task: " + Config.getConfig().getStatus(), 380, 332);
        }
    }
}
