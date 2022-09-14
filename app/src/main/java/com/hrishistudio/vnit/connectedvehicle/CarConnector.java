package com.hrishistudio.vnit.connectedvehicle;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.UUID;

public class CarConnector extends AsyncTask<Void, Void, Void> {

    private BluetoothAdapter adapter = DrivingMode.adapter;
    private BluetoothSocket socket = DrivingMode.socket;
    private UUID uuid = DrivingMode.cUUID;
    private ProgressDialog dialog = DrivingMode.mDialog;
    private boolean connected = DrivingMode.connected;
    private String address = "address";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            if ( socket == null || !connected ) {
                adapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = adapter.getRemoteDevice(address);
                socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
                adapter.cancelDiscovery();
                socket.connect();
                connected = true;
            }
        }
        catch (Exception e){
            connected = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
