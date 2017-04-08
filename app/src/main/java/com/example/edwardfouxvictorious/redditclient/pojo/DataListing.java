package com.example.edwardfouxvictorious.redditclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DataListing implements Parcelable {
    private List<Child> children;
    private String after;


    public List<Child> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    @Override
    public String toString() {
        return "DataListing{" +
                "children=" + children +
                ", after='" + after + '\'' +
                '}';
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.children);
        dest.writeString(this.after);
    }

    public DataListing() {
    }

    protected DataListing(Parcel in) {
        this.children = in.createTypedArrayList(Child.CREATOR);
        this.after = in.readString();
    }

    public static final Parcelable.Creator<DataListing> CREATOR = new Parcelable.Creator<DataListing>() {
        @Override
        public DataListing createFromParcel(Parcel source) {
            return new DataListing(source);
        }

        @Override
        public DataListing[] newArray(int size) {
            return new DataListing[size];
        }
    };
}
