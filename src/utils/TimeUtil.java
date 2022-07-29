package utils;

import org.dreambot.api.utilities.Timer;
public class TimeUtil {
    private static final TimeUtil timeUtil = new TimeUtil();
    public String getFormattedTimeLeft(Timer timer) {
        return getTimerString(timer);
    }


    private String getTimerString(Timer timer) {
        long timeLeft = timer.remaining();
        long second = (timeLeft / 1000) % 60;
        long minute = (timeLeft / (1000 * 60)) % 60;
        long hour = (timeLeft / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static TimeUtil getTimeUtil() {
        return timeUtil;
    }
}
