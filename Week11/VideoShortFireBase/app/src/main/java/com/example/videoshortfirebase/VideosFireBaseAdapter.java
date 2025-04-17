package com.example.videoshortfirebase;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<Video1Model, VideosFireBaseAdapter.MyHolder> {

    private boolean isFav = false;

    public VideosFireBaseAdapter (@NonNull FirebaseRecyclerOptions<Video1Model> options) {
        super(options);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;

        private ProgressBar videoProgressBar;

        private TextView textVideoTitle;

        private TextView textVideoDescription;

        private ImageView imPerson, favorites, imShare,imMore;

        public MyHolder (@NonNull View itemView) {

            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);

            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);

            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);

            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);

            imPerson = itemView.findViewById(R.id.imPerson);

            favorites = itemView.findViewById(R.id.favorites);

            imShare = itemView.findViewById(R.id.imShare);

            imMore = itemView.findViewById(R.id.imMore);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Video1Model model) {
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());

        holder.videoProgressBar.setVisibility(View.VISIBLE);
        holder.videoView.setVideoURI(Uri.parse(model.getUrl()));

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.videoProgressBar.setVisibility(View.GONE);
                mp.setLooping(true);
                holder.videoView.start(); // CHỈ start sau khi đã chuẩn bị xong

                // scale đúng tỉ lệ video nếu cần
                float videoRatio = (float) mp.getVideoWidth() / mp.getVideoHeight();
                float screenRatio = (float) holder.videoView.getWidth() / holder.videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if (scale >= 1f) {
                    holder.videoView.setScaleX(scale);
                } else {
                    holder.videoView.setScaleY(1f / scale);
                }
            }
        });

        holder.videoView.setOnErrorListener((mp, what, extra) -> {
            holder.videoProgressBar.setVisibility(View.GONE);
            // Có thể log ra hoặc hiển thị thông báo
            return true;
        });

        holder.favorites.setOnClickListener(v -> {
            isFav = !isFav;
            holder.favorites.setImageResource(R.drawable.ic_favorite);
        });
    }

}

