package com.mirroring.bean;

import android.util.Log;

import com.mirroring.units.FileUnit;
import com.mirroring.units.HttpUnits;
import com.mirroring.units.StringUnits;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mirroring.activities.MainActivity.TAG;

/**
 * 从本地文件遍历得来的所有章节信息，最后用来和json比对
 */
public class ChapterInfo implements Comparable<ChapterInfo>{
    //用来保存所有的chapter
    private static List<ChapterInfo> chapterInfoList;

    private String chapterName;
    private String chapterContent;
    private File file;
    //小说的文件名格式xx_xx_xx.txt
    private String firstNum;//小说的标识
    private String secondNum;//unknown
    private String lastNum;//章节的标识

    public ChapterInfo(File file) {
        //名称暂时设置成文件名
        this.chapterName = file.getName();
        this.file = file;
        this.chapterContent = FileUnit.readFile(file.getAbsolutePath());

        String[] strings = chapterName.split("_");
        firstNum = strings[0];
        secondNum = strings[1];
        lastNum = strings[2].substring(0,strings[2].indexOf("."));

        //把标识给PreToParsing处理，不会重复
        PreToParsing.addNeedParsing(firstNum);
    }

    /**
     * 获取到chapterList对象
     * @return
     */
    public static List<ChapterInfo> getChapterInfoList() {
        if (chapterInfoList==null) chapterInfoList = new ArrayList<>();
        Collections.sort(chapterInfoList);
        return chapterInfoList;
    }

    public String getFirstNum() {
        return firstNum;
    }

    public String getSecondNum() {
        return secondNum;
    }

    public String getLastNum() {
        return lastNum;
    }

    public ChapterInfo(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public File getFile() {
        return file;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public static void clearChapterInfo() {
        chapterInfoList.clear();
    }

    @Override
    public int compareTo(ChapterInfo o) {
        if (this.chapterName.compareTo(o.getChapterName())>0) {
            return 1;
        }else if (this.chapterName.compareTo(o.getChapterName())<0){
            return -1;
        }
        return 0;
    }
}
