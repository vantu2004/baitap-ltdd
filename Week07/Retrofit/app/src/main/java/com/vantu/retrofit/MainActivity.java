package com.vantu.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vantu.retrofit.api.ApiClient;
import com.vantu.retrofit.model.Comment;
import com.vantu.retrofit.model.Post;
import com.vantu.retrofit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView_name, textView_email, textView_address, textView_phone, textView_company, textView_post;
    private Button button_callApi, button_insertPost;

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

        textView_name = findViewById(R.id.textView_name);
        textView_email = findViewById(R.id.textView_email);
        textView_address = findViewById(R.id.textView_address);
        textView_phone = findViewById(R.id.textView_phone);
        textView_company = findViewById(R.id.textView_company);
        textView_post = findViewById(R.id.textView_post);

        button_callApi = findViewById(R.id.button_callApi);
        button_insertPost = findViewById(R.id.button_insertPost);

        button_callApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi();
            }
        });

        button_insertPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPost();
            }
        });

        callApiWithPathAndQuery(1);
    }

    private void callApiWithPathAndQuery(int postId) {
        ApiClient.apiService.getCommentByPostIdWithPath(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                if (comments != null) {
                    Log.d("comments", "getCommentByPostIdWithPath: " + comments);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Can not call API.", Toast.LENGTH_SHORT).show();
            }
        });

        ApiClient.apiService.getCommentByPostIdWithQuery(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                if (comments != null) {
                    Log.d("comments", "getCommentByPostIdWithQuery: " + comments);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Can not call API.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApi() {
        // hàm enqueue gọi API dạng async tránh chặn Main thread (UI thread)
        ApiClient.apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Toast.makeText(MainActivity.this, "Call API success.", Toast.LENGTH_SHORT).show();

                List<User> users = response.body();
                assert users != null;
                for (User user : users) {
                    textView_name.setText(user.getName());
                    textView_email.setText(user.getEmail());
                    textView_address.setText(user.getAddress().toString());
                    textView_phone.setText(user.getPhone());
                    textView_company.setText(user.getCompany().toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Can not call API.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertPost(){
        Post post = new Post(1, 1, "tu", "le van tu");

        ApiClient.apiService.insertPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this, "Call API success.", Toast.LENGTH_SHORT).show();

                Post p = response.body();
                if (p!=null){
                    textView_post.setText(p.toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Can not call API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}