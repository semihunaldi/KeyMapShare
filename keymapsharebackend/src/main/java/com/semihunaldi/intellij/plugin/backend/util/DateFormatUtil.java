package com.semihunaldi.intellij.plugin.backend.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TTSUNALDI on 4/26/2017.
 */
public class DateFormatUtil
{
    public static final String DATE_FORMAT_DAY_MONTH_YEAR_WITH_DOTS = "dd.MM.yyyy";
    public static final String DATE_FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_WITH_DOTS = "dd.MM.yyyy HH:mm";
    public static final String DATE_FORMAT_DAY_MONTH_YEAR_WITH_SLASH = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DAY_MONTH_YEAR = "ddMMyyyy";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_WITH_DASHES = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YEARMONTHDAYHOURMINUTESECOND = "YYYYMMddHHmmss";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_FIRST_DAY_MONTH_YEAR = "01MMyyyy";
    public static final String DATE_FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND="ddMMyyyHHmmsss";

    public static String getDateFormatDayMonthYearWithDots(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR_WITH_DOTS);
        return sdf.format(date);
    }

    public static String getDateFormatDayMonthYearHourMinuteWithDots(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_WITH_DOTS);
        return sdf.format(date);
    }

    public static String getDateFormatDayMonthYearWithSlash(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR_WITH_SLASH);
        return sdf.format(date);
    }

    public static String getDateFormatDayMonthYear(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR);
        return sdf.format(date);
    }

    public static String getDateFormatYearMonthDayWithDashes(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY_WITH_DASHES);
        return sdf.format(date);
    }

    public static String getDateFormatYearMonthDayHourMinuteSecond(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        return sdf.format(date);
    }

    public static Date addDaysToDate(Date date, int days)
    {
        return addTimeToDate(date, days, Calendar.DATE);
    }

    public static Date addTimeToDate(Date date, int count, int unit)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, count);
        return cal.getTime();
    }

    public static String getDateFormatFirstDayMonthYear(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FIRST_DAY_MONTH_YEAR);
        return sdf.format(date);
    }

    public static String getDateFormatLastDayMonthYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR);
        return sdf.format(calendar.getTime());
    }

    public static String date2Str(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDateFormatDayMonthYearHourMinuteSecond(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND);
        return sdf.format(date);
    }

    public static boolean todayAfterDaysEqualsToDate(Date date, int days)
    {
        if(date!=null)
        {
            DateTime todayDateTime = new DateTime(new Date());
            DateTime dateDaysLater = todayDateTime.plusDays(days);
            DateTime dateTime = new DateTime(date);
            if(dateTime.withTimeAtStartOfDay().equals(dateDaysLater.withTimeAtStartOfDay()))
            {
                return true;
            }
        }
        return false;
    }
}
