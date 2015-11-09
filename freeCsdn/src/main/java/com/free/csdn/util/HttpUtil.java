package com.free.csdn.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class HttpUtil {
    static OkHttpClient client = new OkHttpClient();

    public static String httpGet(String url)  {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        String result = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Bitmap HttpGetBmp(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        Bitmap bitmap = null;
        try {
            response = client.newCall(request).execute();
            bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
