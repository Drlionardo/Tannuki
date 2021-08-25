package com.drlionardo.registryhub.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Converter {
    public static String getDurationFromNow(LocalDateTime localDate) {
        var now = LocalDateTime.now();
        var yearDiff = ChronoUnit.YEARS.between(localDate, now);
        if(yearDiff > 0) {
            return (yearDiff >1) ? (yearDiff + " years ago") : (yearDiff + " year ago");
        }

        var monthsDiff = ChronoUnit.MONTHS.between(localDate, now);
        if(monthsDiff > 0) {
            return (monthsDiff > 1) ? (monthsDiff + " months ago") : (monthsDiff + " month ago");
        }

        var weeksDiff = ChronoUnit.WEEKS.between(localDate, now);
        if(weeksDiff > 0) {
            return (weeksDiff > 1) ? (weeksDiff + " weeks ago") : (weeksDiff + " week ago");
        }

        var daysDiff = ChronoUnit.DAYS.between(localDate, now);
        if(daysDiff > 0) {
            return (daysDiff > 1) ? (daysDiff + " days ago") : (daysDiff + " day ago");
        }

        var hoursDiff = ChronoUnit.HOURS.between(localDate, now);
        if(hoursDiff > 0) {
            return (hoursDiff > 1) ? (hoursDiff + " hours ago") : (hoursDiff + " hour ago");
        }

        var minutesDiff = ChronoUnit.MINUTES.between(localDate, now);
        if(minutesDiff > 0) {
            return (minutesDiff > 1) ? (minutesDiff + " minutes ago") : (minutesDiff + " minute ago");
        }

        var secondsDiff = ChronoUnit.SECONDS.between(localDate, now);
        if(secondsDiff > 0) {
            return (secondsDiff > 1) ? (secondsDiff + " seconds ago") : (secondsDiff + " second ago");
        }

        return "now";
    }
}
