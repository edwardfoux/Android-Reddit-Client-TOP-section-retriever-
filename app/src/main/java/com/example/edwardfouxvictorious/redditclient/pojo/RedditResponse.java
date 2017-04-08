package com.example.edwardfouxvictorious.redditclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class RedditResponse implements Parcelable {
    DataListing data;

    public DataListing getData() {
        return data;
    }

    public void setData(DataListing data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditResponse{" +
                "data=" + data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public RedditResponse() {
    }

    protected RedditResponse(Parcel in) {
        this.data = in.readParcelable(DataListing.class.getClassLoader());
    }

    public static final Parcelable.Creator<RedditResponse> CREATOR = new Parcelable.Creator<RedditResponse>() {
        @Override
        public RedditResponse createFromParcel(Parcel source) {
            return new RedditResponse(source);
        }

        @Override
        public RedditResponse[] newArray(int size) {
            return new RedditResponse[size];
        }
    };
}
