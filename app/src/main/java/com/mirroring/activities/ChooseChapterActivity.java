package com.mirroring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirroring.adapter.AdapterChooseChapterRecyclerView;
import com.mirroring.bean.ChapterInfo;
import com.mirroring.bean.ChapterJsonBean;
import com.mirroring.bean.Paths;
import com.mirroring.bean.PreToParsing;
import com.mirroring.units.FileUnit;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseChapterActivity extends AppCompatActivity implements View.OnClickListener {

    private AdapterChooseChapterRecyclerView adapter;
    private List<ChapterInfo> list;//所有小说文件信息
    private String name;//文件名

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_chapter);
        //获取MainActivity传过来的小说name，如果Action不是et，说明网络请求失败，不执行从Pre拿小说名
        Intent intent = getIntent();
        String string = intent.getAction();
        if (string.equals("et")) {
            //把文件名替换成小说名
            initData();
        } else {
            //只罗列文件名
            initDataWithoutName();
        }
        //拿到name
        name = intent.getStringExtra("fileName");
        initView();//初始化控件

    }

    //这是网络请求失败调用
    private void initDataWithoutName() {
        //拿到遍历好的ChapterInfo，这里是遍历到的所有文件
        list = ChapterInfo.getChapterInfoList();
    }

    //网路请求成功调用，所有ChapterInfo和Pre的Map中的json匹配
    private void initData() {
        //拿到遍历好的ChapterInfo，这里是遍历到的所有文件
        list = ChapterInfo.getChapterInfoList();
        //遍历所有文件的名字，根据每个文件的firstName去Map里寻找对应的章节名，Map早已储存好
        for (int i = 0; i < list.size(); i++) {
            //一个章节文件
            ChapterInfo chapterInfo = list.get(i);
            //该章LastName
            int lastNum = Integer.parseInt(chapterInfo.getLastNum());
            //该章FirstNum
            String firstNum = chapterInfo.getFirstNum();
            //根据firstNum从Pre里拿到小说名，和这章在哪个小说里
            String novelName = PreToParsing.getMapNovelName().get(firstNum);
            //这是对应小说的所有卷
            List<ChapterJsonBean> chapterJsonBeanList = PreToParsing.getMap().get(firstNum);
            //遍历所有卷
            for (int j = 0; j < chapterJsonBeanList.size(); j++) {
                //该卷名
                String juan = chapterJsonBeanList.get(j).getVolume_name();
                //该卷里的所有章
                List<ChapterJsonBean.ChaptersBean> chaptersBeanList = chapterJsonBeanList.get(j).getChapters();
                //遍历所有章
                for (int k = 0; k < chaptersBeanList.size(); k++) {
                    //该章
                    ChapterJsonBean.ChaptersBean chaptersBean = chaptersBeanList.get(k);
                    //如果这章的id和当前文件的lastNu一样,说明找到这章的名字了
                    if (lastNum == chaptersBean.getChapter_id()) {
                        //替换名称，小说名+卷名+章名
                        chapterInfo.setChapterName(novelName + "\n" + juan + "\n" + chaptersBean.getChapter_name());
                    }
                }
            }
        }
    }

    private void initView() {
        SwipeRecyclerView mRecyclerView = (SwipeRecyclerView) findViewById(R.id.recycler_view);
        adapter = new AdapterChooseChapterRecyclerView(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setLongPressDragEnabled(true); // 拖拽排序
        mRecyclerView.setItemViewSwipeEnabled(true); // 侧滑删除
        mRecyclerView.setOnItemMoveListener(mItemMoveListener);

        mRecyclerView.setAdapter(adapter);
        //制作按钮
        FloatingActionButton mFloatChoose = (FloatingActionButton) findViewById(R.id.float_choose);
        mFloatChoose.setOnClickListener(this);
    }

    //View滑动和改变位置，同时改变数据顺序
    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 此方法在Item拖拽交换位置时被调用。
            // 第一个参数是要交换为之的Item，第二个是目标位置的Item。
            // 交换数据，并更新adapter。
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            Collections.swap(list, fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);

            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            // 此方法在Item在侧滑删除时被调用。

            // 从数据源移除该Item对应的数据，并刷新Adapter。
            int position = srcHolder.getAdapterPosition();
            list.remove(position);
            adapter.notifyItemRemoved(position);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_choose:
                List<String> files = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    files.add(list.get(i).getFile().getAbsolutePath());
                    if (!FileUnit.exists(Paths.getPaths().getFinalFilePath()))
                        FileUnit.createDir(Paths.getPaths().getFinalFilePath());
                    FileUnit.writeFile(FileUnit.collectFiles(files), Paths.getPaths().getFinalFilePath() + name + ".txt");
                    Toast.makeText(this, "制作成功\n根目录:0_fetch/" + name + ".txt", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}
