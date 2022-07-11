package com.serhat.translate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.serhat.translate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = MainActivity.this;

        binding.toolbar.setTitle(getString(R.string.app_name));
        binding.toolbar.setTitle(getString(R.string.google_translate_api));
        setSupportActionBar(binding.toolbar);

        binding.btnTranslate.setOnClickListener(view -> {
            
        });
    }
}