package com.serhat.translate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.serhat.translate.api.ApiClient;
import com.serhat.translate.api.ApiInterface;
import com.serhat.translate.database.DatabaseHelper;
import com.serhat.translate.database.LanguageDao;
import com.serhat.translate.databinding.ActivityMainBinding;
import com.serhat.translate.model.Language;
import com.serhat.translate.model.LanguagesResponse;
import com.serhat.translate.model.TranslateResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ActivityMainBinding binding;

    private ApiInterface service;
    private DatabaseHelper databaseHelper;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEdit;

    private ArrayList<String> languages;
    private ArrayAdapter<String> languagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = MainActivity.this;
        service = ApiClient.getClient().create(ApiInterface.class);
        databaseHelper = new DatabaseHelper(context);
        sp = getSharedPreferences("Translate", MODE_PRIVATE);
        spEdit = sp.edit();

        checkLanguageList();

        //toolbar
        binding.toolbar.setTitle(getString(R.string.app_name));
        binding.toolbar.setSubtitle(getString(R.string.google_translate_api));
        setSupportActionBar(binding.toolbar);

        //spinners
        languages = LanguageDao.getLanguages(databaseHelper);
        languagesAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, languages);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spSourceLang.setAdapter(languagesAdapter);
        binding.spSourceLang.setPadding(0, binding.spSourceLang.getPaddingTop(), 0, binding.spSourceLang.getPaddingBottom());

        binding.spTargetLang.setAdapter(languagesAdapter);
        binding.spTargetLang.setPadding(0, binding.spTargetLang.getPaddingTop(), 0, binding.spTargetLang.getPaddingBottom());

        //translate
        binding.btnTranslate.setOnClickListener(view -> {
            String query = binding.edtSource.getText().toString().trim();
            String sourceLang = binding.spSourceLang.getSelectedItem().toString();
            String targetLang = binding.spTargetLang.getSelectedItem().toString();

            if (query.isEmpty()) {
                Snackbar.make(view, getString(R.string.the_text_cannot_be_empty), Snackbar.LENGTH_SHORT).show();
                return;
            }

            service.translate(ApiClient.X_RapidAPI_Key, ApiClient.X_RapidAPI_HOST, query, targetLang, sourceLang).enqueue(new Callback<TranslateResponse>() {
                @Override
                public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                    if (response.body() != null) {
                        String translatedText = response.body().getData().getTranslations().get(0).getTranslatedText();
                        binding.edtTarget.setText(translatedText);

                        Log.e("", "Translated");
                    } else {
                        Log.e("", "Response is empty");
                    }
                }

                @Override
                public void onFailure(Call<TranslateResponse> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });

        });

        //copy to clipboard
        binding.txtCopyToClipboard.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", binding.edtTarget.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(context, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
        });
    }

    private void checkLanguageList() {
        boolean status = sp.getBoolean("status", false);

        if (!status) {
            loadLanguagesToLocalDb();
            spEdit.putBoolean("status", true);
            spEdit.commit();
        }
    }

    private void loadLanguagesToLocalDb() {
        service.getLanguages(ApiClient.X_RapidAPI_Key, ApiClient.X_RapidAPI_HOST).enqueue(new Callback<LanguagesResponse>() {
            @Override
            public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
                if (response.body() != null) {
                    List<Language> languages = response.body().getData().getLanguages();

                    for (Language language : languages) {
                        LanguageDao.insertLanguage(databaseHelper, language);
                    }

                    Log.e("", "Language list loaded");
                } else {
                    Log.e("", "Response is empty");
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}