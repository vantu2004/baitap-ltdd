package com.vantu.slide01;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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

        Objects.requireNonNull(getSupportActionBar()).show();


        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.radioButton2);
        RadioButton radioButton2 = findViewById(R.id.radioButton3);

        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton2);
        radioButton2 = findViewById(R.id.radioButton3);

// Kiểm tra xem RadioGroup có bị null không
        if (radioGroup == null) {
            Log.e("checkId", "radioGroup is null! Kiểm tra lại ID trong XML.");
        } else {
            Log.d("checkId", "radioGroup đã tìm thấy!");

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                Log.d("checkId", "onCheckedChanged: " + checkedId);

                if (checkedId == R.id.radioButton2) {
                    Toast.makeText(MainActivity.this, "Bạn chọn Option 1", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radioButton3) {
                    Toast.makeText(MainActivity.this, "Bạn chọn Option 2", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("checkId", "Không khớp ID nào, giá trị: " + checkedId);
                }
            });
        }

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int current = progressBar.getProgress();
//                //chạy đều đều >100 quay về 0
//                if (current>= progressBar.getMax()){
//                    current=0;
//                }
//                progressBar.setProgress(current + 10); //thiết lập progress

                //tự đếm progress
                CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int current = progressBar.getProgress();
                        //chạy đều đều >100 quay về 0
                        if (current>= progressBar.getMax()){
                            current=0;
                        }
                        progressBar.setProgress(current + 10); //thiết lập progress
                    }
                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this, "Hết giờ",Toast.LENGTH_LONG).show();
                    }
                };
                countDownTimer.start();
            }
        });

        //Seekbar
        SeekBar seekBar= findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progress: giá trị của seekbar
                Log.d("AAA","Giá trị:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("AAA","Start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("AAA","Stop");
            }
        });

        // popup menu cho button
        Button button_popup = findViewById(R.id.button_popup);
        button_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupMenu(); // Gọi popup menu khi nhấn button
            }
        });

        //đăng ký view cho context menu trong onCreate
        Button btnContextMenu = findViewById(R.id.button_button_context_menu);
        registerForContextMenu(btnContextMenu);

        //đăng ký show alert dialog
        Button btnShowDialog = findViewById(R.id.buton_alertDialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            Toast.makeText(this, "Clicked Setting", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "Clicked Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_logout) {
            Toast.makeText(this, "Clicked Logout", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //popup menu
    private void ShowPopupMenu(){
        Button button = findViewById(R.id.button_popup);
        PopupMenu popupMenu = new PopupMenu(this, button);
        popupMenu.getMenuInflater().inflate(R.menu.menu_setting,popupMenu.getMenu());
        // Bắt sự kiện click menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_setting) {
                    Toast.makeText(MainActivity.this, "Bạn đang chọn Setting", Toast.LENGTH_LONG).show();
                } else if (id == R.id.action_share) {
                    button.setText("Chia sẻ");
                } else if (id == R.id.action_logout) {
                    // Xử lý khi chọn Logout (nếu cần)
                }

                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        menu.setHeaderTitle("Context Menu");
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Button button = findViewById(R.id.button_button_context_menu);

        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Toast.makeText(MainActivity.this, "Bạn đang chọn Setting", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_share) {
            button.setText("Chia sẻ");
        } else if (id == R.id.action_logout) {
            // Xử lý khi chọn Logout (nếu cần)
        }

        return super.onContextItemSelected(item);
    }

    private void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn đăng xuất không");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút có
                Toast.makeText(MainActivity.this, "Bạn đang chọn 'có'", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lệnh nút không
                Toast.makeText(MainActivity.this, "Bạn đang chọn 'không'", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }
}