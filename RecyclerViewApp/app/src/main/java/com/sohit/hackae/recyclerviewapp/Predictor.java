package com.sohit.hackae.recyclerviewapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.File;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class Predictor extends AsyncTask<File, Void, ClarifaiOutput<Concept>> {
    private ClarifaiClient client;
    private String apiKey;
    private Context context;

    public Predictor(String apiKey, Context context) {
        client = new ClarifaiBuilder(apiKey)
                .buildSync();
        this.apiKey = apiKey;
        this.context = context;
    }

    @Override
    protected ClarifaiOutput<Concept> doInBackground(File... files) {
        return predictWithImage(files[0]);
    }

    @Override
    protected void onPostExecute(ClarifaiOutput<Concept> input) {

        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println("******************************");
        String resultsString="";
        for (Concept c : input.data()) {
            System.out.println(c.name());
            resultsString += c.name() + ", ";
        }

        boolean isRecyclable = true;
        if (!checkIfRecycling(input) && getMainMaterial(input) == null) {
            isRecyclable = false;
        }

        // We have clarifai object, now pass data to other API
        Intent intent = new Intent(context, ResultPage.class);
        intent.putExtra("result", isRecyclable);
        intent.putExtra("resultsString",resultsString);
        context.startActivity(intent);
    }

    public ClarifaiOutput<Concept> predictWithImage(File file) {
        final List<ClarifaiOutput<Concept>> res =
                client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get your custom models
                        .predict()
                        .withInputs(ClarifaiInput.forImage(file))
                        .executeSync()
                        .get();
        if (res.size() != 1) {
            System.out.println("received more than 1 List response item!");
            return null;
        }
        return res.get(0);
    }

    private String getMainMaterial(ClarifaiOutput<Concept> res) {
        String[] checks = {"plastic", "cardboard", "paper"};
        //List<String> materials = new ArrayList<String>();
        String name;
        for (Concept c : res.data()) {
            for (String check : checks) {
                if (c != null) {
                    name = c.name();
                    if (name != null) {
                        if (name.equals(check)) {
                            return check;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean checkIfRecycling(ClarifaiOutput<Concept> res) {
        String name;
        for (Concept c : res.data()) {
            if (c != null) {
                name = c.name();
                if (name != null) {
                    if (name.equals("recycling")) {
                        return true;
                    }
                    if (name.equals("garbage")) {
                        return false;
                    }
                    if (name.equals("trash")) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
