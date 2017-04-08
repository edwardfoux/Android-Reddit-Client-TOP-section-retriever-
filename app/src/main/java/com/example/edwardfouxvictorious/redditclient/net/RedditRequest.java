package com.example.edwardfouxvictorious.redditclient.net;

import com.example.edwardfouxvictorious.redditclient.activities.MainActivity;
import com.example.edwardfouxvictorious.redditclient.pojo.RedditResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface RedditRequest {
    @GET(MainActivity.URL_PATH)
    Call<RedditResponse> getRedditPosts(@QueryMap Map<String, String> count);
}
