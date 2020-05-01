package com.mirroring.units;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUnit {
    private static SharedPreferences sharedPreferences;

    /**
     * 创建文件夹
     * @param path 文件夹路径
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    /**
     * 读取文本内容
     * @param path 文本路径
     * @return 文本内容
     */
    public static String readFile(String path) {
        File novelFile = new File(path);
        StringBuffer sb = new StringBuffer();
        if (!novelFile.exists() || !novelFile.isFile()) {
            return "err";
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(novelFile));
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 向文本内写入字符串
     * @param content 内容
     * @param filePath 文件路径
     */
    public static void writeFile(String content,String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            return;
        } else {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 列出文件夹下所有文件路径
     * @param dirPath 文件夹路径
     * @param resultList 用来存放结果文件路径
     */
    public static void ListFilePaths(String dirPath, List<String> resultList) {
        File file = new File(dirPath);
        if (!file.isFile() && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                resultList.add(f.getAbsolutePath());
            }
        }
    }

    /**
     * 列出文件夹下所有文件的File对象
     * @param dirPath 文件夹路径
     * @param resultList 结果
     */
    public static void ListFiles(String dirPath, List<File> resultList) {
        File file = new File(dirPath);
        if (!file.isFile() && file.exists()) {
            File[] files = file.listFiles();
            resultList.addAll(Arrays.asList(files));
        }
    }

    /**
     * 合并文本文件
     * @param listFiles 文本路径
     * @return 合并后的字符串
     */
    public static String collectFiles(List<String> listFiles) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s :
                listFiles) {
            stringBuffer.append(readFile(s) + "\n\n\n\n");
        }
        String finalText = StringUnits.replaceHtmlTags(stringBuffer.toString());
        return finalText;
    }

    /**
     * 删除文件，文件夹
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] ff = file.listFiles();
            for (int i = 0; i < ff.length; i++) {
                deleteFile(ff[i].getPath());
            }
        }
        file.delete();
    }

    /**
     * 查看文件夹下文件数目
     * @param path 文件夹路径
     * @return 文件数，-1：文件夹不存在
     */
    public static int countFileOfPath(String path) {
        if (exists(path))
        return new File(path).list().length;
        return -1;
    }

    /**
     * 检查文件夹，文件是否存在
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static boolean isFirstLaunch(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("first", Context.MODE_PRIVATE);
        }
        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
            return true;
        }
        return false;
    }
}
