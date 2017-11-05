package com.sohit.hackae.recyclerviewapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

/**
 * Created by sohit on 10/6/17.
 */

public class ImageRecyclingHandler extends AsyncTask<String, Boolean, String> {

    private Context context = null;
    private JSONObject resultJson = null;
    private String allResults = "";

    public ImageRecyclingHandler() {}

    public ImageRecyclingHandler(Context context, String allResults) {
        this.context = context;
        this.allResults = allResults;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpResponse response = httpclient.execute(new HttpGet(urls[0]));
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                result = convertStreamToString(instream);
                System.out.println(result);
                result = getRecyclerResult(result);
                instream.close();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return result;
    }

    public static String convertStreamToString(InputStream is) {
        /* Taken from Stackoverflow */

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private String getRecyclerResult(String input) {
        try {
            resultJson = new JSONObject(input);
            return resultJson.getString("recyclable");
        } catch (Exception e) { /* No */ }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (context != null) {
            Intent intent = new Intent(context, ResultPage.class);
            intent.putExtra("result", result.equals("true"));
            intent.putExtra("resultsString", allResults);
            context.startActivity(intent);
        } else {
            Toast.makeText(null, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
