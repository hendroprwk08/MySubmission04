package com.dicoding.hendropurwoko.mysubmission04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    MovieHelper movieHelper;
    ArrayList<MovieModel> movieModels;
    MovieModel movieModel;
    MovieAdapter movieAdapter;
    ProgressDialog progressDialog;
    RecyclerView rvMovie;

    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreference = new AppPreference(MainActivity.this);
        movieHelper = new MovieHelper(MainActivity.this);

        rvMovie = (RecyclerView) findViewById(R.id.recyclerview);

        getSupportActionBar().setSubtitle("Hanya memuat data saja");

        Log.d("Info: ", String.valueOf(appPreference.getFirstRun()));

        if(appPreference.getFirstRun()) {
            new LoadData().execute();
        }

        displayRecyclerView();
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(getString(R.string.please_wait));//ambil resource string
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<MovieModel> movies = loadJSON();

            //simpan
            movieHelper.open();
            movieHelper.beginTransaction();

            try {
                for (MovieModel model : movies) {
                    movieHelper.insertTransaction(model);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            movieHelper.setTransactionSuccess();
            movieHelper.endTransaction();
            movieHelper.close();

            appPreference.setFirstRun(false);
            return null;
        }
    }

    private ArrayList<MovieModel> loadJSON() {
        String API = "86b7abdb2cb37ac9c3c148021f6724e5";
        String URL = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ API + "&language=en-US";

        final ArrayList<MovieModel> movieModels = new ArrayList();
        SyncHttpClient client = new SyncHttpClient();

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray jsonArray = jsonObj.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        movieModel = new MovieModel();

                        movieModel.setTitle(data.getString("title").toString().trim());
                        movieModel.setOverview(data.getString("overview").toString().trim());
                        movieModel.setRelease_date(data.getString("release_date").toString().trim());
                        movieModel.setPopularity(data.getString("popularity").toString().trim());
                        movieModel.setPoster("http://image.tmdb.org/t/p/w185"+data.getString("poster_path").toString().trim());

                        movieModels.add(movieModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("ERROR: ", statusCode +": "+ error.toString());
            }
        });

        return movieModels;
    }

    private void displayRecyclerView() {
        movieAdapter = new MovieAdapter(getApplicationContext());
        rvMovie.setLayoutManager(new LinearLayoutManager(this));

        movieHelper.open();
        ArrayList<MovieModel> movies = movieHelper.getAllData();
        movieHelper.close();
        movieAdapter.addItem(movies);
        rvMovie.setAdapter(movieAdapter);
    }
}
