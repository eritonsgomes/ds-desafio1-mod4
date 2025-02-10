package com.devsuperior.dsmeta.utils;

import com.devsuperior.dsmeta.exceptions.DateTimePeriodException;
import org.apache.logging.log4j.util.Strings;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static LocalDate getInitialDate(String minDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate initialDate;

        if (Strings.isBlank(minDate)) {
            initialDate = today.minusYears(1);
        } else {
            initialDate = LocalDate.parse(minDate, dtf);
        }

        return initialDate;
    }

    public static LocalDate getInitialDateFromFinalDate(String endDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate finalDate = LocalDate.parse(endDate, dtf);

        return finalDate.minusYears(1);
    }

    public static LocalDate getFinalDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate initialDate;

        if (Strings.isBlank(date)) {
            initialDate = today;
        } else {
            initialDate = LocalDate.parse(date, dtf);
        }

        return initialDate;
    }

    public static LocalDate getFinalDateFromInitialDate(String startDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate initialDate = LocalDate.parse(startDate, dtf);
        LocalDate finalDate = initialDate.plusYears(1);

        return initialDate;
    }

    public static void validateYearDays(LocalDate initialDate, LocalDate finalDate) {
        LocalDateTime starDateTime = LocalDateTime.of(initialDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(finalDate, LocalTime.MIN);

        boolean isStarDateTimeInCalendarGregorian = isCalendarGregorian(starDateTime);
        boolean isEndDateTimeInCalendarGregorian = isCalendarGregorian(endDateTime);
        boolean isGregorianCalendars = isStarDateTimeInCalendarGregorian && isEndDateTimeInCalendarGregorian;
        boolean hasAtLeastOneGregorianCalendar = !isGregorianCalendars && (isStarDateTimeInCalendarGregorian
                || isEndDateTimeInCalendarGregorian);

        Duration duration = Duration.between(starDateTime, endDateTime);

        if (isGregorianCalendars && duration.toDays() > 366) {
            throw new DateTimePeriodException("O período informado deve ter no máximo 366 dias");
        } else if (hasAtLeastOneGregorianCalendar && duration.toDays() > 366) {
            throw new DateTimePeriodException("O período informado deve ter no máximo 366 dias");
        } else if (!hasAtLeastOneGregorianCalendar && duration.toDays() > 365) {
            throw new DateTimePeriodException("O período informado deve ter no máximo 365 dias");
        }
    }

    public static boolean isCalendarGregorian(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();

        LocalDateTime beginDate = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year + 1, Month.JANUARY, 1, 0, 0, 0);

        Duration duration = Duration.between(beginDate, endDate);

        if (duration.toDays() == 366) {
            return true;
        }

        return false;
    }

}
