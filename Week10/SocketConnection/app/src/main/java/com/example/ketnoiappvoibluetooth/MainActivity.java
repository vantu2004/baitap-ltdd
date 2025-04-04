package com.example.ketnoiappvoibluetooth;

import static android.Manifest.permission.BLUETOOTH_CONNECT;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button btnPaired;
    private ListView listDanhSach;

    public static final String EXTRA_ADDRESS = "device_address";

    private BluetoothAdapter myBluetooth = null;

    private final ActivityResultLauncher<Intent> enableBtLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            showToast("Bluetooth đã được bật.");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                pairedDevicesList(); // Get devices list after bluetooth enabled
                            }
                        } else {
                            showToast("Bluetooth không được bật.");
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        checkBluetoothAvailability();
        setupClickListeners();
    }

    private void initViews() {
        btnPaired = findViewById(R.id.btnTimthietbi);
        listDanhSach = findViewById(R.id.listTb);
    }

    private void checkBluetoothAvailability() {
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            showToast("Thiết bị không hỗ trợ Bluetooth.");
            finish();
        } else if (!myBluetooth.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtLauncher.launch(enableBtIntent);
        }
    }

    private void setupClickListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            btnPaired.setOnClickListener(v -> pairedDevicesList());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void pairedDevicesList() {
        if (ContextCompat.checkSelfPermission(this, BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevices = myBluetooth.getBondedDevices();
            ArrayList<String> deviceList = new ArrayList<>();

            if (pairedDevices != null && !pairedDevices.isEmpty()) {
                for (BluetoothDevice device : pairedDevices) {
                    deviceList.add(device.getName() + "\n" + device.getAddress());
                }
            } else {
                showToast("Không tìm thấy thiết bị kết nối.");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
            listDanhSach.setAdapter(adapter);
            listDanhSach.setOnItemClickListener(myListClickListener);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{BLUETOOTH_CONNECT}, 1);
        }
    }

    private final AdapterView.OnItemClickListener myListClickListener = (av, v, arg2, arg3) -> {
        String info = ((TextView) v).getText().toString();
        String address = info.substring(info.length() - 17);

        Intent intent = new Intent(MainActivity.this, BlueControl.class);
        intent.putExtra(EXTRA_ADDRESS, address);
        startActivity(intent);
    };

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}