package com.teamuxh.uxhloader;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamuxh.uxhloader.mics.config.*;

import java.io.File;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphImageView;

import static com.teamuxh.uxhloader.mics.config.glClass;
import static com.teamuxh.uxhloader.mics.config.glName;

public class MainActivity extends Activity {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    public static ProgressBar progressBar;
    public static TextView textView;
    private TextView announcement,glversion,glstatus,glupdatetime,krversion,krstatus,krupdatetime,vngversion,vngstatus,vngupdatetime;
    private Context 문맥;
    private NeumorphImageView lglobal;
    private NeumorphImageView dglobal;
    private Class<?> msrc;
    Handler handler;
    MainActivity mainActivity;
    //
    // 네이티브 문자열
    //
    private native static void mcontent(Context con);
    private native static void dcontent(Context con);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        문맥 = this;
        announcement = findViewById(R.id.anouncement);
        glstatus = findViewById(R.id.glstatus);
        glversion = findViewById(R.id.glversion);
        glupdatetime = findViewById(R.id.glupdateTime);
        krstatus = findViewById(R.id.krstatus);
        krversion = findViewById(R.id.krversion);
        krupdatetime = findViewById(R.id.krupdate);
        vngstatus = findViewById(R.id.vngstatus);
        vngupdatetime = findViewById(R.id.vngupdatetime);
        vngversion = findViewById(R.id.vngversion);
        lglobal = findViewById(R.id.launchGlobal);
        dglobal = findViewById(R.id.downloadGL);

        builder = new AlertDialog.Builder(this,R.style.ShapeAppearanceOverlay_MyApp_Dialog_Rounded);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_ui,null);
        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.show();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textView = view.findViewById(R.id.txtview);
        progressBar = view.findViewById(R.id.progressLoad);


        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                mcontent(문맥);
                progressBar.setIndeterminate(true);
            }
        }.start();

        lglobal.setOnClickListener(v -> {
            File glSrc = new File(getExternalFilesDir(null),glName);
            if(glSrc.exists()){
                loadCls(glName,glClass);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, msrc);
                startActivity(intent);
                finishAffinity();
            } else {
                Toast.makeText(문맥,"PUBGM 리소스를 찾을 수 없습니다! 먼저 다운로드",Toast.LENGTH_LONG).show();
            }
        });
        dglobal.setOnClickListener(v -> {
            AsyncTask.execute(() -> {
                dcontent(
                        문맥
                );
            });
        });
    }

    public void loadCls(String sName,String sClass){
        ((csl) getApplication()).LoadApk(sName);
        try {
            msrc = getClassLoader().loadClass(sClass);
        } catch (ClassNotFoundException e){
            Log.e("Loading class error ",e.toString());
        }
    }

    @WorkerThread
    void fDownload(String string){
        ContextCompat.getMainExecutor(문맥).execute(()->{
            Toast.makeText(문맥,string,Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });
    }
    @WorkerThread
     public void dPercentage(String string){
        ContextCompat.getMainExecutor(문맥).execute(()->{
            textView.setText("Downloading  ...");
            progressBar.setIndeterminate(false);
            dialog.show();
            if (!string.equals("nan") && !string.equals("-nan")){
                double dStr = Double.parseDouble(string);
                int value = (int) dStr;
                progressBar.setProgress(value);
            }
        });
    }
    public void mResponse(String string){


        String[] response = new String[10];
        response = string.split(":");
        announcement.setText(response[0]);
        glversion.setText(response[1]);
        glstatus.setText(response[2]);
        glupdatetime.setText(response[3]);
        krversion.setText(response[4]);
        krstatus.setText(response[5]);
        krupdatetime.setText(response[6]);
        vngversion.setText(response[7]);
        vngstatus.setText(response[8]);
        vngupdatetime.setText(response[9]);
        dialog.dismiss();
    }
    static {
        System.loadLibrary("teamUxH");
    }

}