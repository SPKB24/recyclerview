package com.sohit.hackae.recyclerviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.irozon.sneaker.Sneaker;

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

//        Sneaker.with(this)
//                .setTitle("Test Sneaker notification", R.color.white)
//                .setMessage("Sneaker message here!", R.color.white)
//                .setIcon(R.drawable.ic_success)
//                .setHeight(100)
//                .autoHide(false)
//                .sneak(R.color.black);
    }
}
