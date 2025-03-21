package com.example.lamthukiemtra.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lamthukiemtra.R;

import com.example.lamthukiemtra.model.Product;

import java.util.List;
public class ProductAdapter extends BaseAdapter {
    //khai báo
    private Context context;
    private int layout;
    private List<Product> products;

    public ProductAdapter(Context context, int layout, List<Product> products) {
        this.context = context;
        this.layout = layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //khởi tạo viewholder
        ViewHolder viewHolder;
        //lấy context
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            convertView = inflater.inflate(layout,null);
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textView_productName = (TextView) convertView.findViewById(R.id.textView_productName);
            viewHolder.imageView_productPic = convertView.findViewById(R.id.imageView_productPic);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //gán giá trị
        Product product = products.get(position);
        viewHolder.textView_productName.setText(product.getName());

        Glide.with(context)
                .load(product.getImgPath())
                .into(viewHolder.imageView_productPic);



        //trả về view
        return convertView;
    }

    //tạo class viewholder
    private class ViewHolder{
        TextView textView_productName;
        ImageView imageView_productPic;
    }
}

