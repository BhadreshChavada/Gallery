package com.photocatalogue.Gallery;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.photocatalogue.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by AMD21 on 26/8/17.
 */

public class GalleryFolderActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<String> imagesList = getFilePaths();
        Log.d("ImageNo", String.valueOf(imagesList.size()));
        Set<String> ImagePath = new HashSet<>();
        for (int i = 0; i < imagesList.size(); i++) {
            ImagePath.add(imagesList.get(i).substring(0, imagesList.get(i).lastIndexOf("/")));
        }
        Log.d("ImageNo", String.valueOf(ImagePath.size()));

        HashMap<String, ArrayList<String>> imageMap = new HashMap<>();


        if (ImagePath != null) {
            Iterator<String> iterator = ImagePath.iterator();
            while (iterator.hasNext()) {
                String url = iterator.next();
                Log.d("Folder", url);
                ArrayList<String> Images = new ArrayList<>();
                for (int i = 0; i < imagesList.size(); i++) {
                    String Imagepath = imagesList.get(i);
                    if (Imagepath.contains(url)) {
                        Images.add(Imagepath);
                    }
                }
                imageMap.put(url, Images);
                Log.d("Images", String.valueOf(Images.size()));


            }
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);


        GalleryFolderAdapter mAdapter = new GalleryFolderAdapter(GalleryFolderActivity.this, imageMap);
        mRecyclerView.setAdapter(mAdapter);

    }

    public ArrayList<String> getFilePaths() {


        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null) {
            c = managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    dirList.add(tempDir);
                } catch (Exception e) {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for (int i = 0; i < dirList.size(); i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();

                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            ) {


                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;


    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }
}
