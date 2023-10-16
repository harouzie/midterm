package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context ct;
    private SharedPreferences sharedPreferences;
    public static Locale locale;
    public LanguageManager(Context ctx) {
        ct = ctx;
        sharedPreferences = ct.getSharedPreferences("LANG", Context.MODE_PRIVATE);
    }

    public void updateResource(String code) {
        locale = new Locale(code);
        Locale.setDefault(locale);
        Resources res = ct.getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
        setLang(code);
    }

    public String getLang() {
        return sharedPreferences.getString("lang","en");
    }

    public void setLang(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code);
//        editor.commit();
        editor.apply();
    }

}
