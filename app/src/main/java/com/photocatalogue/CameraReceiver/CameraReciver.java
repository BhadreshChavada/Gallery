package com.photocatalogue.CameraReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.photocatalogue.Utils.Prefs;

import java.io.File;

/**
 * Created by rujul on 3/2/18.
 */

public class CameraReciver extends BroadcastReceiver {
    private String desPath;

    @Override
    public synchronized void onReceive(Context context, Intent intent) {
        if (intent.getData() != null) {
            Cursor cursor = context.getContentResolver().query(intent.getData(),
                    null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String image_path = cursor.getString(cursor.getColumnIndex("_data"));
                String pathArray[] = image_path.split("/");
                desPath = Prefs.newInstance(context).getDesPath();
                if (desPath.length() > 0) {
                    if (!new File(desPath).exists()) {
                        new File(desPath).mkdir();
                    }
                    moveImage(context, image_path, pathArray);
                } else {
                    return;
                }
            }
        }
    }

    private void moveImage(Context context, String image_path, String[] pathArray) {
        File from = new File(image_path);
        if (pathArray.length > 1) {
            File to = new File(desPath + File.separator + pathArray[pathArray.length - 1]);
            from.renameTo(to);
        } else {
            // When image stored but file path is not exist
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

}