package com.mirroring.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mirroring.activities.MainActivity.TAG;

/**
 * 这个类用来保存请求结果，防止请求重复
 */
public class PreToParsing {
    //用来储存需要解析的id，避免重复请求同一个ID
    private static List<String> needParsing =new ArrayList<>();

    //用来保存id对应的小说名，因为json里不包含小说名，要单独获取
    private static Map<String, String> mapNovelName = new HashMap<>();
    public static Map<String, String> getMapNovelName() {
        return mapNovelName;
    }

    //用来保存对应id的请求结果,最后替换名称时从这里查找
    private static Map<String, List<ChapterJsonBean>> map = new HashMap<>();
    public static Map<String, List<ChapterJsonBean>> getMap() {
        return map;
    }

    /**
     * HttpUnits请求成功会加入待请求id，处理重复
     * @param num
     */
    public static void addNeedParsing(String num) {
        if (!needParsing.contains(num)) {
            needParsing.add(num);
        }
    }
    public static List<String> getNeedParsing() {
        return needParsing;
    }

    /**
     * 罗列needParsing
     */
    public static String needParsingToString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s :
                getNeedParsing()) {
            stringBuffer.append(s+"-");
        }
        return stringBuffer.toString();
    }

}
