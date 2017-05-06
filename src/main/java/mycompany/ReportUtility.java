package mycompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static mycompany.Constants.*;

/**
 * Created by Naresh Kumar Rachepalli on 06-05-2017.
 */
public class ReportUtility {

    public static String convertDate(String date) throws ParseException {
        Date tempDate = new SimpleDateFormat(XML_DATE_FORMAT, Locale.ENGLISH).parse(date);
        return new SimpleDateFormat(DATE_FORMAT).format(tempDate);
    }

    public static String getValidSettlementDate(String settlementDate, String currencyType) throws ParseException {
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(settlementDate);

        String day = new SimpleDateFormat(DAY).format(date);
        String updatedSettlementDate = settlementDate;
        if (currencyType.equalsIgnoreCase(AED) || currencyType.equalsIgnoreCase(SAR)) {
            if (day.equalsIgnoreCase(FRIDAY)) {
                updatedSettlementDate = getValidDay(date, TWO);
            } else if (day.equalsIgnoreCase(SATURDAY)) {
                updatedSettlementDate = getValidDay(date, ONE);
            }
        } else {
            if (day.equalsIgnoreCase(SATURDAY)) {
                updatedSettlementDate = getValidDay(date, TWO);
            } else if (day.equalsIgnoreCase(SUNDAY)) {
                updatedSettlementDate = getValidDay(date, ONE);
            }
        }
        return updatedSettlementDate;
    }

    private static String getValidDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
    }
}


