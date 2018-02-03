package com.photocatalogue.CameraReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by rujul on 3/2/18.
 */

public class CameraReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.i("INFO", "Enter BroadcastReceiver");


        Cursor cursor = context.getContentResolver().query(intent.getData(),
                null, null, null, null);
        cursor.moveToFirst();
        String image_path = cursor.getString(cursor.getColumnIndex("_data"));
        Toast.makeText(context, "New Photo is Saved as : " + image_path, Toast.LENGTH_SHORT)
                .show();
        String Path[] = image_path.split("/");

        File from = new File(image_path);
        File to = new File(Environment.getExternalStorageDirectory() +
                File.separator + "photocatalogue/" + Path[Path.length - 1]);
        from.renameTo(to);
    }

}