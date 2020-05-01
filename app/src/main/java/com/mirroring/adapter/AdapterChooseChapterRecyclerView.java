package com.mirroring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirroring.activities.R;
import com.mirroring.bean.ChapterInfo;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterChooseChapterRecyclerView extends SwipeRecyclerView.Adapter<AdapterChooseChapterRecyclerView.MyViewHolder> {
    private Context context;
    private List<ChapterInfo> list;
    private View inflater;

    public AdapterChooseChapterRecyclerView(Context context, List<ChapterInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.choose_chapter_items, parent,false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_chapterName.setText(list.get(position).getChapterName());
    }
    @NonNull
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_chapterName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_chapterName = itemView.findViewById(R.id.text_recycler_item);
        }
    }
}
