package com.sohit.hackae.recyclerviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class MainActivity extends AppCompatActivity {

    private Button cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        cameraBtn = findViewById(R.id.cameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        final TextFieldBoxes textFieldContainer = (TextFieldBoxes) findViewById(R.id.text_field_boxes);
        final ExtendedEditText textField = (ExtendedEditText) findViewById(R.id.extended_edit_text);
        final ProgressBar magicSpinner = (ProgressBar) findViewById(R.id.magicSpinner);
        final ImageView searchBtn = (ImageView) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textField.getText().toString().length() > 0) {
                    magicSpinner.setVisibility(View.VISIBLE);
                    searchBtn.setVisibility(View.GONE);
                    textFieldContainer.setEnabled(false);

                    new TextRecyclingHandler(getApplicationContext()).execute("http://wecyclr.net/text_to_material?text=" + textField.getText().toString().replace(" ", "%20"));

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
