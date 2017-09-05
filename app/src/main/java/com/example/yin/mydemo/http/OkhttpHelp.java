package com.example.yin.mydemo.http;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/4.
 */

public class OkhttpHelp {
    
    public static void sendOkhttpPost(String url, RequestBody body, Callback callback){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkhttpGet(String url,Callback callback){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
