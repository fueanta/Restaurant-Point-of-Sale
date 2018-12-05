package resource.utility;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UtilityProvider {

    public static java.util.Date getProgramDate(java.sql.Date date) {
        return (new java.util.Date(date.getTime()));
    }

    public static java.sql.Date getDatabaseDate(java.util.Date date) {
        return (new java.sql.Date(date.getTime()));
    }

    public static LocalDate getLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    public static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static java.util.Date getProgramDate(java.time.LocalDate localDate) {
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public static String getStringDate(Date date) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return simpleFormat.format(date);
    }

    public static String getStringDate(java.time.LocalDate localDate) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleFormat.format(UtilityProvider.getProgramDate(localDate));
    }

    public static int getInt(String field, String data) {
        int value = 0;
        try {
            value = Integer.parseInt(data);
        }
        catch (Exception ex) {
            //ErrorBox_Controller.showError("Invalid data!", field + " should have a number.");
            value = -1;
        }
        return value;
    }

    public static float getFloat(String field, String data) {
        float value = 0;
        try {
            value = Float.parseFloat(data);
        }
        catch (Exception ex) {
            //ErrorBox_Controller.showError("Invalid data!", field + " should have a number.");
            value = -1.0f;
        }
        return value;
    }

}
