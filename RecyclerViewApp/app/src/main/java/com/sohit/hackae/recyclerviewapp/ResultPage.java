package com.sohit.hackae.recyclerviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultPage extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_result_page);

        TextView textView_type = findViewById(R.id.thingType);
        linearLayout = findViewById(R.id.linearLayout);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();

        boolean isCompostable = extras.getBoolean("isCompostable");
        boolean isRecyclable = extras.getBoolean("isRecyclable");

        if (isCompostable) {
            imageView.setImageResource(R.drawable.compost);
            textView_type.setText("Compostable");
        } else if (isRecyclable) {
            imageView.setImageResource(R.drawable.recycle);
            textView_type.setText("Recyclable");
        } else {
            imageView.setImageResource(R.drawable.cancel);
            linearLayout.setBackgroundResource(R.color.red);
            textView_type.setText("Garbage");
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
