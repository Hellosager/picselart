package com.example.sebastian.picselart;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Sebastian on 04.05.2017.
 */
public class ImageClickListener implements View.OnClickListener{

    public static final int SELECT_PICTURE_ACTIVITY_REQUEST_CODE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private MainActivity activity;

    public ImageClickListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        verifyStoragePermissions();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(activity, intent, SELECT_PICTURE_ACTIVITY_REQUEST_CODE, null);
    }

    private void verifyStoragePermissions(){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
