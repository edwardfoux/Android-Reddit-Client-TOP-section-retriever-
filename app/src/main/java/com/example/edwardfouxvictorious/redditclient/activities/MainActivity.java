package com.example.edwardfouxvictorious.redditclient.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.edwardfouxvictorious.redditclient.R;
import com.example.edwardfouxvictorious.redditclient.adapter.RedditAdapter;
import com.example.edwardfouxvictorious.redditclient.mvp.ListView;
import com.example.edwardfouxvictorious.redditclient.mvp.RedditPresenter;
import com.example.edwardfouxvictorious.redditclient.pojo.Child;
import com.example.edwardfouxvictorious.redditclient.pojo.DataListing;
import com.example.edwardfouxvictorious.redditclient.pojo.RedditResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView {

    public static final String BASE_URL = "http://www.reddit.com/";
    public static final String URL_PATH = "top/.json";
    public static final String DATA = "data";

    private LinearLayoutManager linearLayoutManager;
    private RedditAdapter redditAdapter;
    private RedditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        redditAdapter = new RedditAdapter(new ImageListener());
        recyclerView.setAdapter(redditAdapter);
        recyclerView.addOnScrollListener(new ScrollListener());

        presenter = new RedditPresenter(this);

        presenter.onCreateCalled(savedInstanceState);
    }

    @Override
    public void onDataAvailable(List<Child> data) {
        redditAdapter.appendData(data);
        redditAdapter.notifyDataSetChanged();
    }

    @Override
    public void openWebThumbnail(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    /**
     * Persists the state of the list during the screen rotation
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        RedditResponse redditResponse = new RedditResponse();
        DataListing dataListing = new DataListing();
        dataListing.setChildren(redditAdapter.getData());
        redditResponse.setData(dataListing);
        outState.putParcelable(DATA, redditResponse);
    }

    private class ImageListener implements RedditAdapter.ImageClickListener {
        @Override
        public void onImageClicked(String url) {
            presenter.onThumbnailClicked(url);
        }
    }

    /**
     * Reports to the presenter the scrolling state
     */
    private class ScrollListener extends RecyclerView.OnScrollListener {
        int visibleItemCount;
        int totalItemCount;
        int pastVisibleItems;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

            presenter.onScrolled(dy, visibleItemCount, totalItemCount, pastVisibleItems);
        }
    }
}
