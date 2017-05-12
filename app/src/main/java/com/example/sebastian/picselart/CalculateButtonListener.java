package com.example.sebastian.picselart;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Sebastian on 11.05.2017.
 */
public class CalculateButtonListener implements View.OnClickListener{
    private final int EDGE_LENGTH = 20;
    private MainActivity activity;
    private Bitmap mosaicBitmap, originalBitmap;

    public CalculateButtonListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        originalBitmap = activity.getCurrentBitmap();
        mosaicBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.RGB_565);
        recalcPixel(originalBitmap);
        activity.setCurrentBitmap(mosaicBitmap);
    }
    // VORSICHT FALL WIRD NICHT ABGEFAGNEN FALLS UNGERADE ZAHL VON PIXELN
    private void recalcPixel(Bitmap bitmap){
        int x , y; x = y = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int xPixelCount = width % EDGE_LENGTH == 0 ? width/EDGE_LENGTH : width/EDGE_LENGTH+1;
        int yPixelCount = height % EDGE_LENGTH == 0 ? height/EDGE_LENGTH : height/EDGE_LENGTH+1;

        while(x < xPixelCount){
            int rectWidth = EDGE_LENGTH;
            int rectHeight = EDGE_LENGTH;
            if((x+1)*EDGE_LENGTH > width)
                 rectWidth = width % EDGE_LENGTH;
            while(y < yPixelCount){
                if((y+1)*EDGE_LENGTH > height)
                    rectHeight = height % EDGE_LENGTH;
                int left = x * EDGE_LENGTH, top = y * EDGE_LENGTH, right = left + rectWidth, bot = top + rectHeight;
                Rect pixel = new Rect(left, top, right, bot);
                int color = calcAverageColor(bitmap, pixel);
                setAverageColor(pixel, color);
                y++;
            }
            y = 0;
            x++;
        }
    }

    private int calcAverageColor(Bitmap bitmap, Rect pixel) {
        int r = 0, g = 0, b = 0;
        for (int x = 0; x < pixel.width(); x++) {
            for (int y = 0; y < pixel.height(); y++) {
                r += Color.red(bitmap.getPixel(pixel.left + x, pixel.top + y));
                g += Color.green(bitmap.getPixel(pixel.left + x, pixel.top + y));
                b += Color.blue(bitmap.getPixel(pixel.left + x, pixel.top + y));
            }
        }
        int countPixel = (pixel.bottom - pixel.top) * (pixel.right - pixel.left);
        return Color.rgb(r / countPixel, g / countPixel, b / countPixel);
    }

    private void setAverageColor(Rect pixel, int color){
        for (int x = 0; x < pixel.width(); x++) {
            for (int y = 0; y < pixel.height(); y++) {
                mosaicBitmap.setPixel(pixel.left + x, pixel.top + y, color);
            }
        }
    }

}
