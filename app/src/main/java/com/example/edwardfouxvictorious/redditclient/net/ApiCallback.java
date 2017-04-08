package com.example.edwardfouxvictorious.redditclient.net;

import android.util.Log;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class ApiCallback<T> implements Callback<T> {
    protected ApiCallback() {
        super();
    }

    public abstract void onSuccess(T response);

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        onSuccess(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("TAG", t.toString());
    }
}
