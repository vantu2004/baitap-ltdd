package com.vantu.kiemtra;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.vantu.kiemtra.adapter.ProductAdapter;
import com.vantu.kiemtra.adapter.RecyclerViewAdapter;
import com.vantu.kiemtra.api.ApiClient;
import com.vantu.kiemtra.model.Category;
import com.vantu.kiemtra.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Category> categories = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.grivView_top10);

        recyclerView = findViewById(R.id.recycleView_categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        getAllCategories();
        getTopSellers();
    }

    private void getTopSellers() {
        ApiClient.apiService.getTopSellers().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> p = response.body();
                if (p != null){
                    products = p;

                    productAdapter = new ProductAdapter(MainActivity.this, R.layout.product_item, products);

                    // Gán adapter cho GridView
                    gridView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.e("REGISTER_FAILURE", "Lỗi kết nối API: " + throwable.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Lỗi kết nối: " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getAllCategories() {
        ApiClient.apiService.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> c = response.body();
                if (c != null){
                    categories = c;

                    recyclerViewAdapter = new RecyclerViewAdapter(categories, MainActivity.this);

                    // contacts dưới dạng livedata nên biến đổi liên tục -> set lại recyclerViewAdapter
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Log.e("REGISTER_FAILURE", "Lỗi kết nối API: " + throwable.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Lỗi kết nối: " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}