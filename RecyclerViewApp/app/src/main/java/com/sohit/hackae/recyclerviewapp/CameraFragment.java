package com.sohit.hackae.recyclerviewapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mindorks.paracamera.Camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CameraFragment extends Fragment {

    private Activity context;
//    private ImageView picFrame;
    private ProgressBar spinner;
    private Camera camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
//        picFrame = (ImageView) rootView.findViewById(R.id.picFrame);
        spinner = (ProgressBar) rootView.findViewById(R.id.magicSpinner);
        spinner.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("recyclerView_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_PNG)
                .setCompression(100)
                .setImageHeight(500)
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            if (bitmap != null) {
                new Predictor(Constants.clarify_api_key, context).execute(imageBitmapToFile(bitmap));
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File imageBitmapToFile(final Bitmap input) {
        try {
            File f = new File(context.getCacheDir(), "newFile?");
            f.createNewFile();

            //Convert bitmap to byte array
            Bitmap imgBitmap = input;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return f;
        } catch (Exception e) { /* Pls just work, thanks */ }

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }
}

