package com.hrishistudio.vnit.connectedvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class HealthDashboard extends AppCompatActivity {
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_dashboard);
        try {
            interpreter = new Interpreter(loadModelFile(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float getInferences(String val){
        float[] input = new float[16];

        float[][] output = new float[1][1];
        interpreter.run(input, output);
        return output[0][0];
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor descriptor = HealthDashboard.this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(descriptor.getFileDescriptor());
        FileChannel channel = inputStream.getChannel();
        long startOffset = descriptor.getStartOffset();
        long length = descriptor.getLength();
        return channel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }
}