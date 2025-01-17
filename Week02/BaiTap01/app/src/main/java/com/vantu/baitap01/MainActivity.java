package com.vantu.baitap01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Integer> randomArr = new ArrayList<>();
    List<Integer> soChan = new ArrayList<>();
    List<Integer> soLe = new ArrayList<>();
    private EditText editText_input;
    private TextView textView_output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText_input = findViewById(R.id.editText_input);
        textView_output = findViewById(R.id.textView_output);

        soChanSoLe();
        phanLoaiSoChanSoLe();


    }

    private void phanLoaiSoChanSoLe() {
        for (int number : randomArr) {
            if (number % 2 == 0) {
                soChan.add(number);
            } else {
                soLe.add(number);
            }
        }
        Log.d("soChan", "phanLoaiSoChanSoLe: " + soChan);
        Log.d("soLe", "phanLoaiSoChanSoLe: " + soLe);
    }

    private void soChanSoLe() {
        for (int i = 0; i < 20; i++) {
            randomArr.add(generateRandomNumbers());
        }
    }

    // Hàm tạo mảng ArrayList số ngẫu nhiên
    private int generateRandomNumbers() {
        // Tạo đối tượng Random
        Random random = new Random();
        // Sinh số nguyên ngẫu nhiên trong khoảng từ 0 đến 100
        return random.nextInt(101);
    }

    public void reverse(View view) {
        String input = editText_input.getText().toString().toUpperCase();

        // Tách chuỗi thành các từ
        String[] words = input.split(" ");

        // Đảo ngược thứ tự các từ trong mảng
        StringBuilder reversedSentence = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reversedSentence.append(words[i]).append(" ");
        }

        // Cập nhật kết quả lên TextView
        textView_output.setText(reversedSentence.toString().trim());
        Toast.makeText(this, reversedSentence, Toast.LENGTH_SHORT).show();
    }

}