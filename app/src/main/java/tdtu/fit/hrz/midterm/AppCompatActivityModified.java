package tdtu.fit.hrz.midterm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppCompatActivityModified extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageManager langMng = new LanguageManager(this);
        langMng.updateResource(langMng.getLang());
    }
}
