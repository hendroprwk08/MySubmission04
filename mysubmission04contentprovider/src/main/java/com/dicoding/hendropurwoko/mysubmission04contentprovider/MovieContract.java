package com.dicoding.hendropurwoko.mysubmission04contentprovider;

import android.provider.BaseColumns;

public class MovieContract {
    static String TABLE_NAME = "table_movie";

    static final class MovieColumns implements BaseColumns {
        static String TITLE = "title";
        static String OVERVIEW  = "overview";
        static String RELEASE_DATE  = "release_date";
        static String POPULARITY  = "popularity";
        static String POSTER = "poster";
    }
}
