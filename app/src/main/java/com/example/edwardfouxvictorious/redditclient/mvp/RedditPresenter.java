package com.example.edwardfouxvictorious.redditclient.mvp;

import android.os.Bundle;

import com.example.edwardfouxvictorious.redditclient.activities.MainActivity;
import com.example.edwardfouxvictorious.redditclient.net.ApiCallback;
import com.example.edwardfouxvictorious.redditclient.net.RedditRequest;
import com.example.edwardfouxvictorious.redditclient.pojo.RedditResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static com.example.edwardfouxvictorious.redditclient.activities.MainActivity.BASE_URL;

public class RedditPresenter {

    private static final String COUNT = "count";
    private static final String AFTER = "after";
    private static final Integer ITEMS_PER_PAGE = 10;

    private int count = 0;
    private String after = "";
    private ListView view;
    private boolean isLoading;


    public RedditPresenter(ListView view) {
        this.view = view;
    }

    public void onCreateCalled(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            RedditResponse redditResponse = savedInstanceState.getParcelable(MainActivity.DATA);
            view.onDataAvailable(redditResponse.getData().getChildren());
        } else {
            getData();
        }
    }

    /**
     * Called when the thumbnail is clicked
     * @param url
     */
    public void onThumbnailClicked(String url) {
        view.openWebThumbnail(url);
    }

    /**
     * Retrofit data request builder
     * Url example: https://www.reddit.com/r/redditdev/.json?count=20&after=t3_643x7o
     */
    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient() {
                    @Override
                    public com.squareup.okhttp.Call newCall(Request request) {
                        return super.newCall(request);
                    }
                })
                .build();

        RedditRequest redditRequest = retrofit.create(RedditRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put(COUNT, String.valueOf(count));
        if (count > 0) {
            map.put(AFTER, String.valueOf(after));
        }
        Call<RedditResponse> call = redditRequest.getRedditPosts(map);

        call.enqueue(new DataLoaderCallback(this));
    }

    /**
     * static class for the networking job
     * Keeps the weak reference to the presenter to avoid memory leaks
     */
    private class DataLoaderCallback extends ApiCallback<RedditResponse> {
        WeakReference<RedditPresenter> ref;

        private DataLoaderCallback(RedditPresenter page) {
            this.ref = new WeakReference<>(page);
        }

        @Override
        public void onSuccess(RedditResponse response) {
            RedditPresenter presenter = ref.get();
            if (presenter == null) return;

            after = response.getData().getAfter();
            view.onDataAvailable(response.getData().getChildren().subList(0, 9));

            isLoading = false;
            count += ITEMS_PER_PAGE;
        }
    }

    /**
     * Detects whenever the pagination is needed
     * @param dy
     * @param visibleItemCount
     * @param totalItemCount
     * @param pastVisibleItems
     */
    public void onScrolled(int dy, int visibleItemCount, int totalItemCount, int pastVisibleItems) {
        if (dy > 0) {
            if (!isLoading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    isLoading = true;
                    getData();
                }
            }
        }
    }
}
