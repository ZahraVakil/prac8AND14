package com.example.prac8;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    Button write, clear;

    public boolean isReadWriteStorageAllowed() {
        //Getting permission status
        int resultRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int resultWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //if permission is granted returning true
        if (resultRead == PackageManager.PERMISSION_GRANTED && resultWrite == PackageManager.PERMISSION_GRANTED)
            return true;

        //if permission denied
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText);
        write = findViewById(R.id.write);
        clear = findViewById(R.id.clear);


        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReadWriteStorageAllowed())
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                else {
                    String message = e1.getText().toString();
                    try {
                        // Get the external storage directory for this app
                        File externalStorageDir = getExternalFilesDir(null);

                        // Create a subdirectory called "myDir" if it doesn't exist
                        File myDir = new File(externalStorageDir, "myDir");
                        if (!myDir.exists()) {
                            myDir.mkdir();
                        }

                        // Create a file called "Prac8.txt" in the "myDir" directory
                        File file = new File(myDir, "Prac8.txt");
                        FileOutputStream fout = new FileOutputStream(file);
                        fout.write(message.getBytes());
                        fout.close();
                        Toast.makeText(getBaseContext(), "Data Written in SdCard" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
            }
        });
    }
}
