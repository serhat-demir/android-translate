package com.serhat.translate.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.serhat.translate.model.Language;

import java.util.ArrayList;

public class LanguageDao {
    @SuppressLint("Range")
    public static ArrayList<String> getLanguages(DatabaseHelper databaseHelper) {
        ArrayList<String> languages = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor c = db.rawQuery("select * from languages", null);
        while (c.moveToNext()) {
            languages.add(c.getString(c.getColumnIndex("language")));
        }
        c.close();
        db.close();

        return languages;
    }

    public static void insertLanguage(DatabaseHelper databaseHelper, Language language) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("language", language.getLanguage());

        db.insert("languages", null, values);
        db.close();
    }
}
