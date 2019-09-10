package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Gq2sxn560xM0EQWXin3mejSx5GgbjF7dGjUlXdX9")
                // if defined
                .clientKey("YA15YuVOCvDnWFBSLJFrx2eyAb71Gw7GHOqNGkaQ")
                .server("https://parseapi.back4app.com/")
                .build()
        );


    }
}
