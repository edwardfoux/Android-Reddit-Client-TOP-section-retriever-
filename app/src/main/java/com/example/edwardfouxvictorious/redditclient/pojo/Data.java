package com.example.edwardfouxvictorious.redditclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private int ups;
    private String title;
    private String thumbnail;
    private String name;
    private long created;

    public int getUps() {
        return ups;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public long getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Data{" +
                "ups=" + ups +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ups);
        dest.writeString(this.title);
        dest.writeString(this.thumbnail);
        dest.writeString(this.name);
        dest.writeLong(this.created);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.ups = in.readInt();
        this.title = in.readString();
        this.thumbnail = in.readString();
        this.name = in.readString();
        this.created = in.readLong();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}