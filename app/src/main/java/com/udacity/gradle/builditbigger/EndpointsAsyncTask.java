package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hanan.and.udacity.jokesdisplay.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Nono on 4/24/2018.
 */

public //AsyncTask Class to retrieve the jokes from the endpoint api
class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private MyApi myApiService = null;
    private Context context;
    private EndPointIdlingResource mIdlingResource;
    private JokesListener mJokesListener;

    public interface JokesListener{
        public void displayJoke(String joke);
    }

    public EndpointsAsyncTask(EndPointIdlingResource idlingResource, JokesListener jokesListener) {
        mIdlingResource = idlingResource;
        mJokesListener = jokesListener;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mJokesListener.displayJoke(joke);
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
            mIdlingResource.setJoke(joke);
        }
    }
}
