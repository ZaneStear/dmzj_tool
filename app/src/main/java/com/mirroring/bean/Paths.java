package com.mirroring.bean;

import android.os.Environment;

import com.mirroring.units.FileUnit;

import java.io.File;

public class Paths {
    private static Paths paths;

    private String storeRootPath;//手机根目录
    private String dmzjNovlePath;//动漫之家储存小说的目录
    private String finalFilePath;//生成文件的目录

    private Paths(String storeRootPath, String dmzjNovlePath, String finalFilePath) {
        this.storeRootPath = storeRootPath;
        this.dmzjNovlePath = dmzjNovlePath;
        this.finalFilePath = finalFilePath;
    }

    public static Paths getPaths() {
        if (paths == null) {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            paths = new Paths(root,
                    root + "Android/data/com.dmzjsq.manhua/files/dmzjsq/Document/",
                    root+"0_fetch/");
        }

        return paths;
    }

    public String getFinalFilePath() {
        return finalFilePath;
    }

    public void setFinalFilePath(String finalFilePath) {
        this.finalFilePath = finalFilePath;
    }

    public String getStoreRootPath() {
        return storeRootPath;
    }

    public String getDmzjNovlePath() {
        return dmzjNovlePath;
    }
}
