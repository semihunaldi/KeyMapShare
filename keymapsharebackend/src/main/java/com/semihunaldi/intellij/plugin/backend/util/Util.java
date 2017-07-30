package com.semihunaldi.intellij.plugin.backend.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * Created by semih on 26.04.2017.
 */
public class Util
{
    public static Date getStartOfDay(Date date)
    {
        DateTime start = new DateTime(date).withTime(0, 0, 1, 999);
        return start.toDate();
    }

    public static Date getEndOfDay(Date date)
    {
        DateTime start = new DateTime(date).withTime(23, 59, 59, 999);
        return start.toDate();
    }

    public static Session getSession(EntityManager entityManager)
    {
        return entityManager.unwrap(Session.class);
    }


    private static Date parseDate(String date, DateTimeFormatter dateTimeFormatter)
    {
        try
        {
            DateTime dateTime = DateTime.parse(date, dateTimeFormatter);
            if (dateTime != null)
            {
                return dateTime.toDate();
            }
            return null;
        }
        catch (Exception e1)
        {
            return null;
        }
    }

    public static boolean isURLValid(String urlString)
    {
        if(StringUtils.isNotBlank(urlString))
        {
            try
            {
                new URL(urlString);
                return true;
            }
            catch (MalformedURLException e)
            {
                return false;
            }
        }
        return false;
    }

    public static String generateToken()
    {
        return UUID.randomUUID().toString().toLowerCase().replace("-","");
    }

    public static String getUUIDWithoutDashes(String uuid)
    {
        return uuid.replace("-","");
    }
}
