package com.photocatalogue.Gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.photocatalogue.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Suleiman19 on 10/22/15.
 */
public class GalleryFolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    HashMap<String, ArrayList<String>> imageMap;

    public GalleryFolderAdapter(Context context, HashMap<String, ArrayList<String>> imageMap) {
        this.context = context;
        this.imageMap = imageMap;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.folder_item, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        Glide.with(context).load(Uri.fromFile(new File(imageMap.get(new ArrayList<String>(imageMap.keySet()).get(position)).get(0))))
                .thumbnail(0.5f)
                .override(200, 200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((MyItemHolder) holder).mImg);

        String Path[] = new ArrayList<String>(imageMap.keySet()).get(position).split("/");

        ((MyItemHolder) holder).folderName.setText(Path[Path.length - 1]);

        ((MyItemHolder) holder).mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putStringArrayListExtra("ImageArray", imageMap.get(new ArrayList<String>(imageMap.keySet()).get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageMap.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView folderName;


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
            folderName = (TextView) itemView.findViewById(R.id.txt_folder_name);
        }

    }


}
