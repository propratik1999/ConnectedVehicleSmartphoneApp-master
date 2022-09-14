package com.hrishistudio.vnit.connectedvehicle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class DrivingMode extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 10;
    private static final int BLUETOOTH_REQUEST = 20;
    public static BluetoothSocket socket;
    public static BluetoothAdapter adapter;
    public static ProgressDialog mDialog;
    public static boolean connected = false;
    public static final UUID cUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_mode);
        adapter = BluetoothAdapter.getDefaultAdapter();
        socket = null;
        mDialog = new ProgressDialog(DrivingMode.this);
        mDialog.setTitle("Connecting with car");
        mDialog.setMessage("Please wait");
        mDialog.setCanceledOnTouchOutside(false);

        if(adapter == null) {
            Toast.makeText(DrivingMode.this, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        }

        if (adapter.isEnabled()) {
            connectWithCar();
        }
        else {
            Intent bluetoothRequest = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothRequest,BLUETOOTH_REQUEST);
        }

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish();
        }

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }

    private void connectWithCar(){
        CarConnector connector = new CarConnector();
        connector.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BLUETOOTH_REQUEST && resultCode == RESULT_OK){
            connectWithCar();
        }
    }

    private void startTrackerService() {
        startService(new Intent(this, TrackingService.class));
        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startTrackerService();
        } else {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
    }
}