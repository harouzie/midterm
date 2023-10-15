package tdtu.fit.hrz.midterm.entity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import tdtu.fit.hrz.midterm.LanguageManager;

public class MyStringFormatter {
    public final static SimpleDateFormat fullDateFormat
        = new SimpleDateFormat( "EEEE, dd/MM/yyyy", LanguageManager.locale);
    /**
     * "dd/MM/yyyy"
     */
    public static SimpleDateFormat dateFormatter
            = new SimpleDateFormat("dd/MM/yyyy", LanguageManager.locale);
    public static NumberFormat numberFormat
            = NumberFormat.getNumberInstance(LanguageManager.locale);

    public static SimpleDateFormat dayFormatter
        = new SimpleDateFormat("dd", LanguageManager.locale);
    public static SimpleDateFormat weekdayFormatter
        = new SimpleDateFormat("EEEE, MMMM, yyyy", LanguageManager.locale);


}
