package com.example.edwardfouxvictorious.redditclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardfouxvictorious.redditclient.R;
import com.example.edwardfouxvictorious.redditclient.pojo.Child;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RedditAdapter extends RecyclerView.Adapter<RedditAdapter.RedditViewHolder> {
    private static final String HOURS_PLACEHOLDER = "%02d hours ago";
    private static final String AUTHOR = "Author: ";
    private static final String UPS = "UPS: ";

    private List<Child> data = new ArrayList<>();
    private ImageClickListener imageClickListener;

    public RedditAdapter(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public void appendData(List<Child> data) {
        this.data.addAll(this.data.size(), data);
    }

    public List<Child> getData() {
        return data;
    }

    @Override
    public RedditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, null);
        return new RedditViewHolder(view);    }

    @Override
    public void onBindViewHolder(RedditViewHolder holder, int position) {
        holder.setup(data.get(position), imageClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RedditViewHolder extends RecyclerView.ViewHolder {
        TextView ups;
        TextView author;
        TextView title;
        View view;
        ImageView imageView;
        TextView created;

        RedditViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ups = (TextView) itemView.findViewById(R.id.ups);
            author = (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            created = (TextView) itemView.findViewById(R.id.created);
        }

        void setup(final Child child, final ImageClickListener listener) {
            author.setText(AUTHOR + String.valueOf(child.getData().getName()));
            ups.setText(UPS + String.valueOf(child.getData().getUps()));
            title.setText(String.valueOf(child.getData().getTitle()));
            long timeAgo = System.currentTimeMillis() - child.getData().getCreated();
            String hours = String.format(Locale.US, HOURS_PLACEHOLDER,
                    TimeUnit.MILLISECONDS.toHours(timeAgo));
            created.setText(String.valueOf(hours));
            Picasso.with(view.getContext()).load(child.getData().getThumbnail()).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClicked(child.getData().getThumbnail());
                }
            });
        }
    }

    public interface ImageClickListener {
        void onImageClicked(String url);
    }
}
