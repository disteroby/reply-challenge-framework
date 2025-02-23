package it.tt.utils;

import java.util.Date;

public final class DateTimeUtils {
    private DateTimeUtils() {}

    public static String getTimeDifference(Date dateStart, Date dateEnd) {
        // Calculate the difference in milliseconds
        long diffInMillis = dateEnd.getTime() - dateStart.getTime();

        // Calculate minutes, seconds, and milliseconds
        long minutes = diffInMillis / 60_000;
        long seconds = (diffInMillis % 60_000) / 1_000;
        long milliseconds = diffInMillis % 1_000;

        // Return the formatted result as a string
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
