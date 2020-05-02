package com.mirroring.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mirroring.bean.ChapterInfo;
import com.mirroring.bean.Paths;
import com.mirroring.bean.PreToParsing;
import com.mirroring.units.FileUnit;
import com.mirroring.units.HttpUnits;
import com.mirroring.units.StringUnits;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HttpUnits.OnSuccessInterface, NavigationView.OnNavigationItemSelectedListener {
    private int successTimes = 0;//请求成功的次数，当等于总个数，跳转

    public static final String TAG = "wujx";
    private DrawerLayout mDrawerLayout;
    private EditText mEditNovelName;//文件名输入框
    private FloatingActionButton mButtonFloat;//制作按钮
    private FloatingActionButton mButtonFloatDelete;//删除小说按钮
    private NavigationView mNavigationMain;//侧边栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//init控件
        checkWritePermission();//申请权限
        //首次启动
        if (FileUnit.isFirstLaunch(this)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //最主要部分
    private void parseFileNameToChapter() {
        //遍历dmzj小说文件夹，存储到ChapterInfo
        List<File> files = new ArrayList<>();
        FileUnit.ListFiles(Paths.getPaths().getDmzjNovlePath(), files);
        for (File f :
                files) {
            if (!StringUnits.isCorrectFileName(f.getName())) {
                showDialog("错误","检测到有非动漫之家下载的小说文件，请先清空已下载的小说，重新从动漫之家下载后重试");
                Log.d(TAG, "文件名不合法"+f.getName());
                return;
            }
            ChapterInfo.getChapterInfoList().add(new ChapterInfo(f));
        }
        //开始遍历待请求标识并请求,在Pre中获取needParsing，不会请求重复id
        List<String> list = PreToParsing.getNeedParsing();
        //没有找到文件
        if (list.size() == 0) {
            toast("没有小说，请先去动漫之家下载");
            return;
        }
        for (String s :
                list) {
            HttpUnits.getNovelJsonByFileNum(s);//请求小说信息json
            HttpUnits.getChapterJsonByFileNum(s, this);//请求小说所有章节json
        }
        Toast.makeText(this, "加载中......", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "准备请求的id：" + PreToParsing.needParsingToString());
    }

    /**
     * 网络请求回调
     *
     * @param result
     */
    @Override
    public void onSuccess(final String result, boolean err) {
        if (err) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    toast("错误：" + result);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("加载小说名失败了，但是可以继续提取，是否继续");
                    builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String name = mEditNovelName.getText().toString();
                            Intent intent = new Intent(MainActivity.this, ChooseChapterActivity.class);
                            intent.setAction("err");
                            intent.putExtra("fileName", name);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.setTitle("提示");
                    builder.show();
                }
            });
            return;
        }
        //请求成功的次数，当等于总个数，跳转
        successTimes++;
        Log.d(TAG, "请求成功，第几次" + successTimes);
        if (successTimes == PreToParsing.getNeedParsing().size()) {
            Log.d(TAG, "所有请求完毕" + successTimes);
            //PreToParsing.mapToString();
            String name = mEditNovelName.getText().toString();
            Intent intent = new Intent(this, ChooseChapterActivity.class);
            intent.setAction("et");
            intent.putExtra("fileName", name);
            startActivity(intent);
        }
    }

    //初始化控件
    private void initView() {
        Toolbar mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        mNavigationMain = (NavigationView) findViewById(R.id.navigation_main);
        mButtonFloatDelete = (FloatingActionButton) findViewById(R.id.button_float_delete);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mEditNovelName = (EditText) findViewById(R.id.edit_novel_name);
        mButtonFloat = (FloatingActionButton) findViewById(R.id.button_float);

        mToolbarMain.setOnClickListener(this);
        mButtonFloat.setOnClickListener(this);
        mDrawerLayout.setOnClickListener(this);
        mButtonFloatDelete.setOnClickListener(this);
        mNavigationMain.setNavigationItemSelectedListener(this);//侧边栏菜单点击监听

        //绑定toolbar和DrawerLayout
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbarMain, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    //吐司
    public void toast(String content) {
        Snackbar.make(mButtonFloat, content, Snackbar.LENGTH_LONG).show();
    }

    //floatingButton点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_float:
                if (mEditNovelName.getText().toString().equals("")) {
                    toast("文件名不能是空");
                    return;
                }
                parseFileNameToChapter();//根据数字获取小说名和章节json
                break;
            case R.id.button_float_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定删除动漫之家下载的小说吗");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUnit.deleteFile(Paths.getPaths().getDmzjNovlePath());
                        FileUnit.createDir(Paths.getPaths().getDmzjNovlePath());
                        init();
                        toast("删除成功");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setTitle("提示");
                builder.show();

                break;
        }
    }

    //返回时清空数据，防止添加到RecyclerView重复
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    //初始化状态,防止添加到RecyclerView重复
    private void init() {
        successTimes = 0;
        if (PreToParsing.getNeedParsing()!=null) PreToParsing.clearNeedParsing();
        if (ChapterInfo.getChapterInfoList() != null) ChapterInfo.clearChapterInfo();
    }

    //返回键关闭抽屉
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {  //如果打开了DrawerLayout则返回键是关闭
                mDrawerLayout.closeDrawers();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //检查权限
    public void checkWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 1);
        }
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            toast("存储权限已获得");
        } else {
            // 跳转到当前应用的设置界面
            boolean b = shouldShowRequestPermissionRationale(permissions[0]);

            checkWritePermission();
            toast("没有存储权限不能使用");
            if (!b) {
                // 用户还是想用我的 APP 的
                // 提示用户去应用设置界面手动开启权限

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                Toast.makeText(this, "请开启权限", Toast.LENGTH_LONG).show();
                startActivityForResult(intent, 123);
            }
        }
    }

    /**
     * 导航菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_question) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("作者：贴吧@吐泡泡great\n\n遇到问题请及时反馈以更新\n\n2020.5");
            builder.setPositiveButton("复制QQ号", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", "1030349525");
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    toast("已复制");
                }
            });
            builder.setNegativeButton("取消", null);
            builder.setTitle("About");
            builder.show();
            return true;
        }
        return false;
    }

    public void showDialog(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}