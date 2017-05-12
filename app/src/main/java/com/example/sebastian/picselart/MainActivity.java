package com.example.sebastian.picselart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private final String CURRENT_PIC = "bitmap";
    private ImageView pv;
    private Button calculate;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pv = (ImageView) findViewById(R.id.picture);
        pv.setOnClickListener(new ImageClickListener(this));

        calculate = (Button) findViewById(R.id.button);
        calculate.setOnClickListener(new CalculateButtonListener(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bitmap == null)
            pv.setBackgroundResource(R.drawable.imageview_bg);
        else{
            pv.setBackgroundResource(0);
            pv.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode){
            case ImageClickListener.SELECT_PICTURE_ACTIVITY_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if(cursor.moveToFirst()){
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        bitmap = BitmapFactory.decodeFile(filePath);
//                        bitmap = Bitmap.createScaledBitmap(bitmap, pv.getWidth(), pv.getHeight(), true);
                    }
                    cursor.close();
                }
                break;
        }

    }

    public Bitmap getCurrentBitmap(){
        return bitmap;
    }

    public void setCurrentBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        pv.setImageBitmap(bitmap);
    }
}
