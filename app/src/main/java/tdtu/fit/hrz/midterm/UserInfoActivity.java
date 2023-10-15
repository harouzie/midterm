package tdtu.fit.hrz.midterm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;


public class UserInfoActivity extends AppCompatActivityModified {

    Button languageChange, nameChange, saveButton;
    ImageView userAvatar;
    TextView userInfoTitle, languageChangeTitle, userDisplayName;
    EditText editName;
    Context context;
    Resources resources;
    Context ctx;
    ActivityResultLauncher<String> imagePickerLauncher;
    String lastUserName, newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ctx = this;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        lastUserName = preferences.getString("lastUserName", "Default Username");
        languageChange = findViewById(R.id.languageButton);
        userInfoTitle = findViewById(R.id.userInfoTitleTextView);
        languageChangeTitle = findViewById(R.id.languageSwitchDescription);
        userAvatar = findViewById(R.id.userAvatar);
        nameChange = findViewById(R.id.changeNameButton);
        saveButton = findViewById(R.id.saveNameButton);
        userDisplayName = findViewById(R.id.userDisplayName);
        editName = findViewById(R.id.editName);
        LanguageManager lang = new LanguageManager(this);

        userDisplayName.setText(lastUserName);

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

        nameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                editName.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editName, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (editName.getText().toString().length() == 0) {
                    Toast.makeText(ctx, "Do not leave your name empty!", Toast.LENGTH_SHORT)
                                                                                        .show();
                    editName.requestFocus();
                }
                newName = editName.getText().toString();
                userDisplayName.setText(newName);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("lastUserName", newName);
                editor.apply();
                saveButton.setVisibility(View.INVISIBLE);
                editName.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
            }
        });

    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivityForResult(intent);
        String selectedDate = getIntent().getStringExtra("selectedDate");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("shouldRestartMainActivity", true);
        editor.putString("lastSelectedDate", selectedDate);
        editor.putBoolean("shouldReloadSelectedDate", true);
        editor.putString("lastUserName", newName);
        editor.apply();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String lastUserNameSave = userDisplayName.getText().toString();
        outState.putString("userName", lastUserNameSave);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lastUserName", lastUserNameSave);
        editor.apply();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String userName = savedInstanceState.getString("userName");
        userDisplayName.setText(userName);
    }

}