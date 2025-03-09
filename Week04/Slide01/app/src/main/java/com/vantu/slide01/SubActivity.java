package com.vantu.slide01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {

    private TextView textView_name;
    private Button button_return_name;
    private EditText editText_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView_name = findViewById(R.id.textView_name);
        button_return_name = findViewById(R.id.button_return_name);
        editText_name = findViewById(R.id.editText_name);

        Intent intent = new Intent();
        String name = getIntent().getStringExtra("username");
        textView_name.setText(name);
        editText_name.setText(name);
    }

    public void returnName(View view) {
        String returnName = String.valueOf(editText_name.getText());

        Intent intent = new Intent();
        intent.putExtra("returnUserName", returnName);
        setResult(RESULT_OK, intent);
        finish();
    }
}