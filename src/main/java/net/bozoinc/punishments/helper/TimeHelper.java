package net.bozoinc.punishments.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class TimeHelper {

    public static Long convertToLong(String string) {
        long time = 0;
        String[] split = string.split(" ");

        for (String splittedString : split) {
            char charAt = splittedString.charAt(splittedString.length() - 1);

            int timeInt;
            try {
                timeInt = Integer.parseInt(splittedString.replace(Character.toString(charAt), ""));
            } catch (NumberFormatException exception) {
                return (long) -1;
            }

            for (TimeMultiplier timeMultiplier : TimeMultiplier.values()) {
                if (timeMultiplier.getDiminutive() != charAt) continue;

                time += timeMultiplier.getMultiplier() * timeInt;
            }
        }

        return time;
    }

    public static String formatDifference(long time, boolean compare) {
        if (time == -1) return "n/a";

        long realTime = (compare ? time - System.currentTimeMillis() : time);

        if (realTime == 0L) {
            return "nunca";
        }

        long day = TimeUnit.MILLISECONDS.toDays(realTime);
        long hours = TimeUnit.MILLISECONDS.toHours(realTime) - day * 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(realTime) - TimeUnit.MILLISECONDS.toHours(realTime) * 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(realTime) - TimeUnit.MILLISECONDS.toMinutes(realTime) * 60L;

        StringBuilder stringBuilder = new StringBuilder();

        if (day > 0L) {
            stringBuilder.append(day).append(day == 1L ? "d" : "d");
        }
        if (hours > 0L) {
            stringBuilder.append(hours).append(hours == 1L ? "h" : "h");
        }
        if (minutes > 0L) {
            stringBuilder.append(minutes).append(minutes == 1L ? "m" : "m");
        }
        if (seconds > 0L) {
            stringBuilder.append(seconds).append(seconds == 1L ? "s" : "s");
        }

        String build = stringBuilder.toString();
        return (build.isEmpty() ? "agora" : build);
    }

    @Getter
    @AllArgsConstructor
    private enum TimeMultiplier {

        SECONDS(1000, 's'),
        MINUTES(60 * 1000, 'm'),
        HOURS(60 * 60 * 1000, 'h'),
        DAYS(24 * 60 * 60 * 1000, 'd'),
        WEEKS(7 * 24 * 60 * 60 * 1000, 'w'),
        YEARS((long) 365 * 24 * 60 * 60 * 1000, 'y');

        private long multiplier;
        private char diminutive;

    }

}
