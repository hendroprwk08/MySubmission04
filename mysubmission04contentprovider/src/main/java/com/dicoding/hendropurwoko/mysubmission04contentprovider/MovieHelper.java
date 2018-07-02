package com.dicoding.hendropurwoko.mysubmission04contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class MovieHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private ArrayList<MovieModel> movieModels;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(MovieModel movieModel) {
        String sql = "INSERT INTO " + MovieContract.TABLE_NAME + " (" +
                MovieContract.MovieColumns.TITLE + ", " +
                MovieContract.MovieColumns.RELEASE_DATE + ", " +
                MovieContract.MovieColumns.OVERVIEW + ", " +
                MovieContract.MovieColumns.POPULARITY + ", " +
                MovieContract.MovieColumns.POSTER + ") VALUES (?, ?, ?, ?, ?)";

        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, movieModel.getTitle());
        stmt.bindString(2, movieModel.getRelease_date());
        stmt.bindString(3, movieModel.getOverview());
        stmt.bindString(4, movieModel.getPopularity());
        stmt.bindString(5, movieModel.getPoster());
        stmt.execute();
        stmt.clearBindings();
    }

    public ArrayList<MovieModel> getAllData(){
        ArrayList<MovieModel> movieModels = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + MovieContract.TABLE_NAME + " ORDER BY " + MovieContract.MovieColumns._ID + " DESC", null);
        cursor.moveToFirst();

        MovieModel movieModel;

        if (cursor.getCount()>0) {
            do {
                movieModel = new MovieModel();
                movieModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.TITLE)));
                movieModel.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.RELEASE_DATE)));
                movieModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.OVERVIEW)));
                movieModel.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.POPULARITY)));
                movieModel.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.POSTER)));

                movieModels.add(movieModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieModels;
    }
}