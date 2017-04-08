package com.example.edwardfouxvictorious.redditclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Child implements Parcelable {
    private Data data;
    private String kind;

    public Data getData() {
        return data;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "Child{" +
                "data=" + data +
                ", kind='" + kind + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.kind);
    }

    protected Child(Parcel in) {
        this.data = in.readParcelable(Data.class.getClassLoader());
        this.kind = in.readString();
    }

    public static final Parcelable.Creator<Child> CREATOR = new Parcelable.Creator<Child>() {
        @Override
        public Child createFromParcel(Parcel source) {
            return new Child(source);
        }

        @Override
        public Child[] newArray(int size) {
            return new Child[size];
        }
    };
}
