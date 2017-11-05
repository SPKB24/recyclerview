package com.sohit.hackae.recyclerviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultPage extends AppCompatActivity {

    private LinearLayout validRecycleLayout;
    private LinearLayout invalidRecycleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        validRecycleLayout = findViewById(R.id.validRecycle);
        invalidRecycleLayout = findViewById(R.id.invalidRecycle);

        TextView validResults = (TextView) findViewById(R.id.validResults);
        TextView invalidResults = (TextView) findViewById(R.id.invalidResults);

        Bundle extras = getIntent().getExtras();
        boolean result = extras.getBoolean("result");
        String resultsString = extras.getString("resultsString");

        validResults.setText(resultsString);
        invalidResults.setText(resultsString);

        if (!result) {
            validRecycleLayout.setVisibility(View.GONE);
            invalidRecycleLayout.setVisibility(View.VISIBLE);
        }


    }
}
