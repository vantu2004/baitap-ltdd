package com.vantu.kiemtra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vantu.kiemtra.R;
import com.vantu.kiemtra.model.Category;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Category> categories;
    private Context context;

    public RecyclerViewAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    // tạo ra một ViewHolder mới để hiển thị một mục (item) trong danh sách
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = Objects.requireNonNull(categories.get(position));
        holder.categoryName.setText(category.getName());
    }

    // trả về số lượng mục dữ liệu mà adapter sẽ hiển thị
    @Override
    public int getItemCount() {
        return categories.size();
    }

    // giữ các tham chiếu đến các widget trong view đã được truyền vào
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryName = itemView.findViewById(R.id.textView_productName);

        }

    }
}
