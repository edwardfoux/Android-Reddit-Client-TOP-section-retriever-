package com.example.edwardfouxvictorious.redditclient.mvp;

import com.example.edwardfouxvictorious.redditclient.pojo.Child;

import java.util.List;

public interface ListView {
    void onDataAvailable(List<Child> data);
    void openWebThumbnail(String url);
}
