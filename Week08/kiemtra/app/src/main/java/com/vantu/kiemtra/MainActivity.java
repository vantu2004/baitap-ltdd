package com.vantu.kiemtra;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vantu.kiemtra.api.ApiClient;
import com.vantu.kiemtra.api.ApiService;
import com.vantu.kiemtra.model.Users;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameUser = findViewById(R.id.name_user);

        loadUsers();
    }

    private void loadUsers() {
        ApiService apiService = ApiClient.apiService;
        apiService.getUsers().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Users> userList = response.body();
                    if (!userList.isEmpty()) {
                        nameUser.setText("Hi! " + userList.get(0).getUserName());

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
