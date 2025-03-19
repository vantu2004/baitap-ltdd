package com.vantu.retrofitwithrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vantu.retrofitwithrecyclerview.R;
import com.vantu.retrofitwithrecyclerview.model.Post;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Post> posts;

    public RecyclerViewAdapter(List<Post> posts){
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        if (post != null){
            holder.textView_id.setText(String.valueOf(post.getId()));
            holder.textView_title.setText(post.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_id, textView_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_id = itemView.findViewById(R.id.textView_id);
            textView_title = itemView.findViewById(R.id.textView_title);
        }
    }
}
