package com.example.ketnoiappvoibluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlueControl extends AppCompatActivity {

    private ImageButton btnTb1, btnTb2, btnDis;
    private TextView txt1, txtMAC;
    private ProgressBar progressBar; // Thêm ProgressBar
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private boolean isBluetoothConnected = false;
    private String deviceAddress = null;
    private int flagLamp1;
    private int flagLamp2;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        Intent intent = getIntent();
        deviceAddress = intent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        initViews();
        setupClickListeners();
        connectBluetooth();
    }

    private void initViews() {
        btnTb1 = findViewById(R.id.btnTb1);
        btnTb2 = findViewById(R.id.btnTb2);
        txt1 = findViewById(R.id.textV1);
        txtMAC = findViewById(R.id.textViewMAC);
        btnDis = findViewById(R.id.btnDisc);
        progressBar = findViewById(R.id.progressBar); // Khởi tạo ProgressBar
    }

    private void setupClickListeners() {
        btnTb1.setOnClickListener(v -> toggleDevice1());
        btnTb2.setOnClickListener(v -> toggleDevice7());
        btnDis.setOnClickListener(v -> disconnectBluetooth());
    }

    private void toggleDevice1() {
        if (bluetoothSocket != null) {
            try {
                flagLamp1 = (flagLamp1 == 0) ? 1 : 0;
                int drawableRes = (flagLamp1 == 1) ? R.drawable.tb1on : R.drawable.tb1off;
                String message = (flagLamp1 == 1) ? "Thiết bị số 1 đang bật" : "Thiết bị số 1 đang tắt";
                String command = (flagLamp1 == 1) ? "1" : "A";

                btnTb1.setBackgroundResource(drawableRes);
                bluetoothSocket.getOutputStream().write(command.getBytes());
                txt1.setText(message);
            } catch (IOException e) {
                showToast("Lỗi");
            }
        }
    }

    private void toggleDevice7() {
        if (bluetoothSocket != null) {
            try {
                flagLamp2 = (flagLamp2 == 0) ? 1 : 0;
                int drawableRes = (flagLamp2 == 1) ? R.drawable.tb2on : R.drawable.tb2off;
                String message = (flagLamp2 == 1) ? "Thiết bị số 7 đang bật" : "Thiết bị số 7 đang tắt";
                String command = (flagLamp2 == 1) ? "7" : "G";

                btnTb2.setBackgroundResource(drawableRes);
                bluetoothSocket.getOutputStream().write(command.getBytes());
                txt1.setText(message);
            } catch (IOException e) {
                showToast("Lỗi");
            }
        }
    }

    private void disconnectBluetooth() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                showToast("Lỗi");
            }
        }
        finish();
    }

    private void connectBluetooth() {
        progressBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar

        executorService.execute(() -> {
            boolean isConnectionSuccessful = true;
            try {
                if (bluetoothSocket == null || !isBluetoothConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
                    if (ActivityCompat.checkSelfPermission(BlueControl.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                        bluetoothAdapter.cancelDiscovery();
                        bluetoothSocket.connect();
                    }
                }
            } catch (IOException e) {
                isConnectionSuccessful = false;
            }

            boolean finalIsConnectionSuccessful = isConnectionSuccessful;
            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE); // Ẩn ProgressBar
                if (!finalIsConnectionSuccessful) {
                    showToast("Kết nối thất bại! Kiểm tra thiết bị.");
                    finish();
                } else {
                    showToast("Kết nối thành công.");
                    isBluetoothConnected = true;
                    displayPairedDevices();
                }
            });
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void displayPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices != null && !pairedDevices.isEmpty()) {
                StringBuilder deviceList = new StringBuilder();
                for (BluetoothDevice device : pairedDevices) {
                    deviceList.append(device.getName()).append(" - ").append(device.getAddress()).append("\n");
                }
                txtMAC.setText(deviceList.toString());
            } else {
                showToast("Không tìm thấy thiết bị kết nối.");
            }
        }
    }
}