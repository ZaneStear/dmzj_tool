package com.mirroring.units;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirroring.bean.ChapterJsonBean;
import com.mirroring.bean.NovelJsonBean;
import com.mirroring.bean.PreToParsing;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.mirroring.activities.MainActivity.TAG;

public class HttpUnits {

    //获取小说信息的api
    public static final String novel_name_url = "http://v3api.dmzj.com/novel/";
    //获取小说章节信息的api
    public static final String chapter_name_url = "http://v3api.dmzj.com/novel/chapter/";

    /**
     * 请求时应从PreToParsing类中获取需要请求的id
     *
     * @param firstNum
     */
    public static void getChapterJsonByFileNum(final String firstNum, final OnSuccessInterface listener) {

        OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(chapter_name_url+firstNum+".json").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        listener.onSuccess(e.getMessage(),true);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String result = response.body().string();
                        //把结果解析成Bean，保存在map
                        List<ChapterJsonBean> beans = new Gson().fromJson(result,new TypeToken<List<ChapterJsonBean>>(){}.getType());
                        PreToParsing.getMap().put(firstNum, beans);
                        listener.onSuccess(result,false);
            }
        });

    }

    /**
     * 单独获取小说名
     * @param firstNum
     */
    public static void getNovelJsonByFileNum(final String firstNum) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(novel_name_url+firstNum+".json").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();
                Log.d(TAG, "danci" +novel_name_url+"\n"+ result);
                //把结果解析成Bean，保存在map
                NovelJsonBean novelJsonBean = new Gson().fromJson(result, NovelJsonBean.class);
                //只把小说名放到NovelNameMap
                PreToParsing.getMapNovelName().put(firstNum, novelJsonBean.getName());

            }
        });
    }

    public interface OnSuccessInterface {
        void onSuccess(String result,boolean err);
    }
}
