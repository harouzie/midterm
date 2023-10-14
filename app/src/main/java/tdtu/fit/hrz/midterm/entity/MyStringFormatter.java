package tdtu.fit.hrz.midterm.entity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import tdtu.fit.hrz.midterm.LanguageManager;

public class MyStringFormatter {
    public final static SimpleDateFormat fullDateFormat
        = new SimpleDateFormat( "EEEE, dd/MM/yyyy");
    public final static NumberFormat numberFormat
            = NumberFormat.getNumberInstance(LanguageManager.locale);

    public final static SimpleDateFormat dayFormatter
        = new SimpleDateFormat("dd");
    public final static SimpleDateFormat weekdayFormatter
        = new SimpleDateFormat("EEEE, MMMM, yyy");

}
