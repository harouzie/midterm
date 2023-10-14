package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivityModified {

    Button languageChange;

    TextView userInfoTitle, languageChangeTitle;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        languageChange = findViewById(R.id.languageButton);
        userInfoTitle = findViewById(R.id.userInfoTitleTextView);
        languageChangeTitle = findViewById(R.id.languageSwitchDescription);
        LanguageManager lang = new LanguageManager(this);

        languageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (languageChange.getText().toString().equals("EN")) {
                    lang.updateResource("vi");
                    languageChange.setText(R.string.languageText);
                    userInfoTitle.setText(R.string.userInfoTitle);
                    languageChangeTitle.setText(R.string.languageSwitch);
                } else {
                    lang.updateResource("en");
                    languageChange.setText(R.string.languageText);
                    userInfoTitle.setText(R.string.userInfoTitle);
                    languageChangeTitle.setText(R.string.languageSwitch);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivityForResult(intent);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("shouldRestartMainActivity", true);
        editor.apply();
        super.onBackPressed();
    }
}