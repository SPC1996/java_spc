package java_spc.tutorials.datetime_api;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 观看java tutorials的datetime-api模块时所写的代码
 */
public class Start {
    public static void main(String[] args) {
        showDayOfWeek();
        showMonth();
        showLocalDate();
        showYearMonth();
        showMonthDay();
        showYear();
        showLocalTime();
        showLocalDateTime();
        showZoneIdAndZoneOffset();
        showZonedDateTime();
        showOffsetDateTime();
        showOffsetTime();
        showInstant();
        parsing();
        formatting();
        showTemporalAdjusters();
        showCustomAdjuster();
        showTemporalQueries();
        showCustomQuery();
        showDuration();
        showChronoUnit();
        showPeriod();
    }

    private static void showDayOfWeek() {
        System.out.println("show DayOfWeek------------------------------");

        DayOfWeek dow = DayOfWeek.MONDAY;
        System.out.println(dow.getDisplayName(TextStyle.FULL, Locale.US));
        System.out.println(dow.getDisplayName(TextStyle.NARROW, Locale.US));
        System.out.println(dow.getDisplayName(TextStyle.SHORT, Locale.US));
        System.out.println(dow.getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println(dow.getDisplayName(TextStyle.NARROW, Locale.CHINA));
        System.out.println(dow.getDisplayName(TextStyle.SHORT, Locale.CHINA));

        System.out.println("--------------------------------------------");
    }

    private static void showMonth() {
        System.out.println("show Month----------------------------------");

        Month month = Month.FEBRUARY;
        System.out.println(month.getDisplayName(TextStyle.FULL, Locale.US));
        System.out.println(month.getDisplayName(TextStyle.NARROW, Locale.US));
        System.out.println(month.getDisplayName(TextStyle.SHORT, Locale.US));
        System.out.println(month.getDisplayName(TextStyle.FULL, Locale.CHINA));
        System.out.println(month.getDisplayName(TextStyle.NARROW, Locale.CHINA));
        System.out.println(month.getDisplayName(TextStyle.SHORT, Locale.CHINA));

        System.out.println("--------------------------------------------");
    }

    private static void showLocalDate() {
        System.out.println("show LocalDate-------------------------------");

        LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
        LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        System.out.println(date);
        System.out.println(nextWed);
        System.out.println(date.getDayOfWeek());
        System.out.println(nextWed.getDayOfWeek());

        System.out.println("---------------------------------------------");
    }

    private static void showYearMonth() {
        System.out.println("show YearMonth-------------------------------");

        YearMonth date = YearMonth.now();
        System.out.printf("%s: %d\n", date, date.lengthOfMonth());
        YearMonth date2 = YearMonth.of(2018, Month.FEBRUARY);
        System.out.printf("%s: %d\n", date2, date2.lengthOfMonth());
        YearMonth date3 = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("%s: %d\n", date3, date3.lengthOfMonth());

        System.out.println("---------------------------------------------");
    }

    private static void showMonthDay() {
        System.out.println("show MonthDay--------------------------------");

        MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
        System.out.println(date.isValidYear(2017));

        System.out.println("---------------------------------------------");
    }

    private static void showYear() {
        System.out.println("show Year------------------------------------");

        System.out.println(Year.isLeap(2016));

        System.out.println("---------------------------------------------");
    }

    private static void showLocalTime() {
        System.out.println("show LocalTime-------------------------------");

        LocalTime thisSec = LocalTime.now();
        LocalTime endSec = thisSec.plusSeconds(2);
        LocalTime newSec;
        while (thisSec.isBefore(endSec)) {
            newSec = LocalTime.now();
            if (thisSec.getHour() != newSec.getHour() ||
                    thisSec.getMinute() != newSec.getMinute() ||
                    thisSec.getSecond() != newSec.getSecond()) {
                thisSec = newSec;
                System.out.printf("%d:%d:%d\n", thisSec.getHour(), thisSec.getMinute(), thisSec.getSecond());
            }
        }
        System.out.println("---------------------------------------------");
    }

    private static void showLocalDateTime() {
        System.out.println("show LocalTime-------------------------------");

        System.out.printf("now: %s\n", LocalDateTime.now());
        System.out.printf("Apr 15, 1996 @ 11:30am: %s\n",
                LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));
        System.out.printf("now (from Instant): %s\n",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        System.out.printf("6 months from now: %s\n",
                LocalDateTime.now().plusMonths(6));
        System.out.printf("6 months ago: %s\n",
                LocalDateTime.now().minusMonths(6));

        System.out.println("---------------------------------------------");
    }

    private static void showZoneIdAndZoneOffset() {
        System.out.println("show ZoneId and ZoneOffset-------------------");

        Set<String> allZones = ZoneId.getAvailableZoneIds();
        LocalDateTime dt = LocalDateTime.now();

        List<String> zoneList = new ArrayList<>(allZones);
        Collections.sort(zoneList);

        for (String s : zoneList) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
            String out = String.format("%25s %10s\n", zone, offset);
            if (secondsOfHour != 0) {
                System.out.print(out);
            }
        }

        System.out.println("---------------------------------------------");
    }

    private static void showZonedDateTime() {
        System.out.println("show ZonedDateTime---------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime leaving = LocalDateTime.of(2017, Month.JULY, 20, 19, 30);
        ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

        try {
            String out1 = departure.format(formatter);
            System.out.printf("leaving: %s (%s)\n", out1, leavingZone);
        } catch (DateTimeException e) {
            System.out.printf("%s can't be formatted!\n", departure);
            throw e;
        }

        ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone)
                .plusMinutes(650);

        try {
            String out2 = arrival.format(formatter);
            System.out.printf("arriving: %s (%s)\n", out2, arrivingZone);
        } catch (DateTimeException e) {
            System.out.printf("%s can't be formatted!\n", arrival);
            throw e;
        }

        if (leavingZone.getRules().isDaylightSavings(departure.toInstant())) {
            System.out.printf("(%s daylight saving time will be in effect.)\n", leavingZone);
        } else {
            System.out.printf("(%s standard time will be in effect.)\n", leavingZone);
        }

        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant())) {
            System.out.printf("(%s daylight saving time will be in effect.)\n", arrivingZone);
        } else {
            System.out.printf("(%s standard time will be in effect.)\n", arrivingZone);
        }

        System.out.println("---------------------------------------------");
    }

    private static void showOffsetDateTime() {
        System.out.println("show OffsetDateTime--------------------------");

        LocalDateTime localDateTime = LocalDateTime.of(2018, Month.JULY, 3, 8, 8);
        ZoneOffset offset = ZoneOffset.of("-08:00");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);
        OffsetDateTime lastThursday = offsetDateTime.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
        System.out.printf("The last Thursday in July 2018 is the %sth.\n",
                lastThursday.getDayOfMonth());

        System.out.println("---------------------------------------------");
    }

    private static void showOffsetTime() {
        System.out.println("show OffsetTime------------------------------");
        LocalTime localTime = LocalTime.now();
        ZoneOffset offset = ZoneOffset.of("+00:05");
        OffsetTime offsetTime = OffsetTime.of(localTime, offset);
        System.out.println(offsetTime);
        System.out.println("---------------------------------------------");
    }

    private static void showInstant() {
        System.out.println("show Instant---------------------------------");

        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(now.getLong(ChronoField.INSTANT_SECONDS));
        Instant oneHourLater = Instant.now().plusSeconds(3600);
        System.out.println(oneHourLater);
        System.out.println(oneHourLater.getLong(ChronoField.INSTANT_SECONDS));
        long secondsFromEpoch = Instant.ofEpochSecond(0L).until(Instant.now(), ChronoUnit.SECONDS);
        System.out.println(secondsFromEpoch);
        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        System.out.printf("%s %d %d at %d:%d\n", ldt.getMonth(), ldt.getDayOfMonth(),
                ldt.getYear(), ldt.getHour(), ldt.getMinute());

        System.out.println("---------------------------------------------");
    }

    private static void parsing() {
        System.out.println("parsing--------------------------------------");

        String in = "01 03 2018";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
            LocalDate date = LocalDate.parse(in, formatter);
            System.out.printf("%s\n", date);
        } catch (DateTimeParseException e) {
            System.out.printf("%s is not parsable!\n", in);
            throw e;
        }

        System.out.println("---------------------------------------------");
    }

    private static void formatting() {
        System.out.println("formatting-----------------------------------");

        LocalDateTime now = LocalDateTime.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy hh;mm a");
            String out = now.format(formatter);
            System.out.println(out);
        } catch (DateTimeException e) {
            System.out.printf("%s can't b formatted!\n", now);
            throw e;
        }

        System.out.println("---------------------------------------------");
    }

    private static void showTemporalAdjusters() {
        System.out.println("show TemporalAdjusters-----------------------");

        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();
        System.out.printf("%s is on a %s%n", date, dow);
        System.out.printf("first day of Month: %s%n",
                date.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.printf("first Monday of Month: %s%n",
                date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
        System.out.printf("last day of Month: %s%n",
                date.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.printf("last Monday of Month: %s%n",
                date.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));
        System.out.printf("first day of next Month: %s%n",
                date.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.printf("first day of Year: %s%n",
                date.with(TemporalAdjusters.firstDayOfYear()));
        System.out.printf("first day of next Year: %s%n",
                date.with(TemporalAdjusters.firstDayOfNextYear()));

        System.out.println("---------------------------------------------");
    }

    private static class PaydayAdjuster implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            LocalDate date = LocalDate.from(temporal);
            int day;
            if (date.getDayOfMonth() < 15) {
                day = 15;
            } else {
                day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            }
            date = date.withDayOfMonth(day);
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            }
            return temporal.with(date);
        }
    }

    private static void showCustomAdjuster() {
        System.out.println("show custom adjuster-------------------------");

        LocalDate givenDate = LocalDate.now().minusDays(16);
        LocalDate nextPayday = givenDate.with(new PaydayAdjuster());
        System.out.printf("given the date: %s%n", givenDate);
        System.out.printf("the next payday: %s%n", nextPayday);

        System.out.println("---------------------------------------------");
    }

    private static void showTemporalQueries() {
        System.out.println("show TemporalQueries-------------------------");

        TemporalQuery<TemporalUnit> query = TemporalQueries.precision();
        System.out.printf("LocalDate precision is %s%n",
                LocalDate.now().query(query));
        System.out.printf("LocalDateTime precision is %s%n",
                LocalDateTime.now().query(query));
        System.out.printf("Year precision is %s%n",
                Year.now().query(query));
        System.out.printf("YearMonth precision is %s%n",
                YearMonth.now().query(query));
        System.out.printf("Instant precision is %s%n",
                Instant.now().query(query));

        System.out.println("---------------------------------------------");
    }

    private static class CheckDate implements TemporalQuery<Boolean> {
        @Override
        public Boolean queryFrom(TemporalAccessor temporal) {
            int month = temporal.get(ChronoField.MONTH_OF_YEAR);
            int day = temporal.get(ChronoField.DAY_OF_MONTH);

            if (month == Month.APRIL.getValue() && day >= 3 && day <= 8) {
                return Boolean.TRUE;
            }

            if (month == Month.AUGUST.getValue() && day >= 8 && day <= 14) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }

    private static void showCustomQuery() {
        System.out.println("show custom query-----------------------------");

        LocalDate birth = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate now = LocalDate.now();
        System.out.printf("%s is birthday? : %s%n", now, now.query(new CheckDate()));
        System.out.printf("%s is birthday? : %s%n", birth, birth.query(new CheckDate()));

        System.out.println("----------------------------------------------");
    }

    private static void showDuration() {
        System.out.println("show Duration---------------------------------");

        Instant start = Instant.now();
        Duration gap = Duration.ofSeconds(10);
        Instant later = start.plus(gap);
        Instant end = Instant.now();
        long ns = Duration.between(start, end).toNanos();
        System.out.printf("start is %d%n", start.getEpochSecond());
        System.out.printf("10 seconds is %d%n", later.getEpochSecond());
        System.out.printf("end is %d%n", end.getEpochSecond());
        System.out.printf("duration between start and end is %d%n", ns);

        System.out.println("----------------------------------------------");
    }

    private static void showChronoUnit() {
        System.out.println("show ChronoUnit-------------------------------");

        Instant previous = Instant.now().minusSeconds(10);
        Instant current = Instant.now();
        long gap = ChronoUnit.MILLIS.between(previous, current);
        System.out.println(gap);

        System.out.println("----------------------------------------------");
    }

    private static void showPeriod() {
        System.out.println("show Period-----------------------------------");

        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1960, Month.JANUARY, 1);
        Period period = Period.between(birthday, today);
        long period2 = ChronoUnit.DAYS.between(birthday, today);
        System.out.println("You are " + period.getYears() + " years, "
                + period.getMonths() + " months, and "
                + period.getDays() + "days old.(" + period2 + " days total)");

        System.out.println("----------------------------------------------");


    }
}
