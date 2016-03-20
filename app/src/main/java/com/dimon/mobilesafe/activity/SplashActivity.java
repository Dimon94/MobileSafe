package com.dimon.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.dimon.mobilesafe.R;
import com.dimon.mobilesafe.Update;
import com.dimon.mobilesafe.utils.GsonTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_ENTER_HOME = 4;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    private Update update;
    public Message msg;
    public String jsonString = "";


    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    System.out.println("进入Handler,准备生成Dailog");
                    showUpdateDialog();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvVersion.setText("Version:" + getVersionName());
        checkVersion();

    }

    /**
     * 获取版本名
     *
     * @return
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//获取包信息
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取版本号
     *
     * @return
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//获取包信息
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 从服务器获取版本信息进行校验
     */
    private void checkVersion() {

        final long startTime = System.currentTimeMillis();
        //启动子线程去异步加载
        new Thread() {
            @Override
            public void run() {

                msg = Message.obtain();

                String url_path = "http://192.168.1.102:8080/update.json";
                //String jsonString = HttpUtils.getJsonContent(url_path);//从网络获取数据
                HttpURLConnection connection = null;
                try {
                    //本机地址是localhost,但是如果用模拟器加载本机的地址时，可以用IP（10.0.2.2）来替换
                    URL url = new URL(url_path);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    //connection.connect();
                    System.out.println("网络返回" + connection.getResponseCode());
                    if (connection.getResponseCode() == 200) {
                        jsonString = changInputStream(connection.getInputStream());
                        update = GsonTools.getUpdate(jsonString, Update.class);     //解析json
                        System.out.println("解析完json：" + update.toString());
                        if (update.getVersionCode() > getVersionCode()) {       //检测是否需要升级
                            System.out.println("需要升级：" + update.getVersionCode() + ">" + getVersionCode());
                            msg.what = CODE_UPDATE_DIALOG;
                        }
                    } else {
                        msg.what = CODE_ENTER_HOME;
                    }
                } catch (MalformedURLException e) {
                    //url错误异常
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    //网络错误异常
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = startTime - endTime; //访问网络花费的时间
                    if (timeUsed < 2000) {

                        //强制休眠一段时间，保证闪屏页展示2秒钟
                        try {
                            Thread.sleep(2000 - timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    mHandler.sendMessage(msg);
                    if (connection != null) {
                        //关闭网络连接
                        connection.disconnect();
                    }
                }


            }
        }.start();


    }

    /**
     * 将流里的数据转换为字符串
     *
     * @param inputStream
     * @return
     */
    private static String changInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * 显示升级对话框
     */
    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        System.out.println("生成Dialo...");
        builder.setTitle("最新版本" + update.getVersionName());
        builder.setMessage(update.getDescription());
        //builder.setCancelable(false);     //不让用户返回,用户体验太差，尽量不用
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("立即更新");
                download();
            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("不更新你后悔！！！");
                enterHome();
            }
        });

        //设置取消的监听，用户点击返回会触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();

    }


    /**
     *  下载apk文件
     */
    private void download() {
        String target = Environment.getExternalStorageDirectory() + "update.apk";
        HttpUtils utils = new HttpUtils();
        utils.download(update.getDownloadUrl(), target, new RequestCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                //跳转到系统下载页面
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(responseInfo.result),
                        "application/vnd.android.package-archive");
                //startActivity(intent);
                startActivityForResult(intent,0);               //如果用户取消安装的话，会返回结果，回调方法onActivityResult
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 如果用户取消安装的话,回回调此方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 进入主界面
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
