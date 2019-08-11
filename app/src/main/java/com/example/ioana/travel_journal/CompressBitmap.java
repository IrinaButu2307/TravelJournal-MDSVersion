package com.example.ioana.travel_journal;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CompressBitmap extends AsyncTask<Bitmap,Void,byte[]> {

    @Override
    protected byte[] doInBackground(Bitmap... bitmaps) {
        Bitmap image = bitmaps[0];
        byte[] array = null;
        if(image != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,50,outputStream);

            array = outputStream.toByteArray();
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return array;
    }
}
