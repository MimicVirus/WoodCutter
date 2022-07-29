package utils;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.Worlds;

import java.util.Arrays;
import java.util.List;

public class LogToConsoleUtil {
    private static final String player = Players.localPlayer().getName();
    private static final String world = String.valueOf(Worlds.getCurrentWorld());

    public static void onLogInfo(String message) {
        List<String> asList = Arrays.asList
                ("***** [INFO] *****",
                        String.format("[Bot]: %1s", player),
                        String.format("[Info]: %2s", message),
                        String.format("[World]: %3s", world),
                        ("***** [END] *****"));
        for (String infoString : asList) {
            MethodProvider.log(infoString);
        }
    }


    public static void onLogWarn(String message) {
        List<String> asList = Arrays.asList
                ("***** [WARN] *****",
                        String.format("[Bot]: %1s", player),
                        String.format("[Info]: %2s", message),
                        String.format("[World]: %3s", world),
                        ("***** [END] *****"));
        for (String warnString : asList) {
            MethodProvider.log(warnString);
        }
    }


    public static void onLogError(String message) {
        List<String> asList = Arrays.asList
                ("***** [ERROR] *****",
                        String.format("[Bot]: %1s", player),
                        String.format("[Info]: %2s", message),
                        String.format("[World]: %3s", world),
                        ("***** [END] *****"));
        for (String errorString : asList) {
            MethodProvider.log(errorString);
        }
    }
}