package com.photocatalogue;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.photocatalogue.Gallery.GalleryFolderActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createFolder(View view) {
        if (checkPermissions()) {

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.layout_create_folder);
            final EditText edtFolderName = (EditText) dialog.findViewById(R.id.edt_folder_name);
            dialog.findViewById(R.id.btn_create_folder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtFolderName.getText().toString().length() > 0) {
                        if (FolderCreate(edtFolderName.getText().toString()))
                            dialog.dismiss();

                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter Folder Name", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }

    }

    private boolean FolderCreate(String folderName) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "photocatalogue/" + folderName);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
            Toast.makeText(this, "create folder SuccessFully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            // Do something else on failure
            Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void openCatalogueActivity(View view) {
        if (checkPermissions()) {
            startActivity(new Intent(this, GalleryFolderActivity.class));
        }
    }

    public void setDefaultFolder(View view) {
        if (checkPermissions()) {
            getAllFilesOfDir(new File(Environment.getExternalStorageDirectory() +
                    File.separator + "photocatalogue" + File.separator));
        }
    }

    public void openCamera(View view) {
        if (checkPermissions()) {
            Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivity(intent);
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 0);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera Not Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


    private void getAllFilesOfDir(File directory) {
        Log.d("TAG", "Directory: " + directory.getAbsolutePath() + "\n");

        final File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) {  // it is a folder...
                        getAllFilesOfDir(file);
                    } else {  // it is a file...
                        Log.d("TAG", "File: " + file.getAbsolutePath() + "\n");
                    }
                }
            }
        }
    }


}
