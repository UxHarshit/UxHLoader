package com.teamuxh.uxhloader;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import com.teamuxh.uxhloader.loader.DxEmu;
import com.teamuxh.uxhloader.loader.UxH;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class csl extends Application {

    private Context cc;
    private Object j;
    private static boolean isOpen = true;
    private Stack<ClassLoader> CLs;
    private static Stack<Object> parents;
    private String dexPath;
    private UxH<Object> sm;
    private AssetManager assetManager;
    private Resources resources;
    private Resources.Theme theme;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        cc = base;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.j != null){
            try {
                this.j.getClass().getDeclaredMethod("onConfigurationChanged",new Class[]{Configuration.class}).invoke(this.j,new Object[]{});
                return;
            } catch (Exception e){
                Log.e("onLowMemory()",e.toString());
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.j == null){
            try {
                this.j.getClass().getDeclaredMethod("onLowMemory",new Class[0]).invoke(this.j,new Object[0]);
                return;
            } catch (Exception exception){
                Log.w("onLowMemory()",exception);
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (this.j != null){
            try {
                this.j.getClass().getDeclaredMethod("onTerminate",new Class[0]).invoke(this.j, new Object[0]);
                return;
            } catch (Exception e){
                Log.e("onTerminate()",e.toString());
            }
        }
    }

    public void LoadApk(String filename){
        if (getExternalFilesDir(null) != null){
            dexPath = new File(getExternalFilesDir(null),filename).getAbsolutePath();
        } else {
            if(getFilesDir() != null){
                dexPath = new File(getFilesDir(),filename).getAbsolutePath();
            }
        }
        ClassLoader defaultCl = getClass().getClassLoader();
        sm = new UxH<>(defaultCl);
        if (CLs.size() == 0)
            CLs.push(defaultCl);
        try {
            Object defaultP = sm.get();
            if (parents.size() == 0)
                parents.push(defaultP);
            Object layerP = new DxEmu(this,dexPath,cc.getApplicationInfo().nativeLibraryDir,(ClassLoader)sm.get());
            if (!parents.peek().equals(layerP)) {
                parents.push(layerP);
                CLs.push((ClassLoader)layerP);
            }
            sm.set(parents.peek());
            loadResources();
        } catch (Exception e){
            Log.e("onCreate catch",e.toString());
        }
    }

    @Override
    public String getPackageName() {
        if (isOpen){
            return "";
        } else {
            return getBaseContext().getPackageName();
        }
    }

    @Override
    public synchronized void onCreate() {
        super.onCreate();
        this.isOpen = true;
        CLs =new Stack<>();
        parents = new Stack<>();
        createPath();
    }

    public void RemoveApk(){
        try {
            if (CLs.size() > 1){
                CLs.pop();
                parents.pop();
            }
            UxH<Object> sm = new UxH<>(CLs.peek());
            sm.set(parents.peek());
        } catch (Exception e){
            Log.e("RemoveApk : ",e.toString());
            if (e instanceof IllegalArgumentException){
                return;
            }
        }
        theme = null;
        resources = null;;
        assetManager = null;
    }
    protected void loadResources() throws InstantiationException, IllegalAccessException, IllegalArgumentException,InvocationTargetException,NoSuchMethodException, NoSuchFieldException
    {
        AssetManager am = (AssetManager) AssetManager.class.newInstance();
        am.getClass().getMethod("addAssetPath",String.class).invoke(am,dexPath);
        this.assetManager = am;
        Constructor<?> constructor = Resources.class.getConstructor(AssetManager.class, cc.getResources().getDisplayMetrics().getClass(),cc.getResources().getConfiguration().getClass());
        resources =  (Resources) constructor.newInstance(am, cc.getResources().getDisplayMetrics(),cc.getResources().getConfiguration());
        theme = resources.newTheme();
        theme.applyStyle(android.R.style.Theme_DeviceDefault, false);
    }
    public void createPath(){
        File file;
        if (getExternalFilesDir(null) != null){
            file = getExternalFilesDir(null);
        } else {
            file = getFilesDir();
        }
        if (!file.exists()){
            file.mkdir();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (CLs == null || CLs.size() == 0 ){
            return super.getClassLoader();
        }
        Log.d("CLass Layer", String.valueOf(CLs.size()));
        return CLs.peek();
    }

    @Override
    public AssetManager getAssets() {
        return assetManager == null ? super.getAssets() : assetManager;
    }

    @Override
    public Resources getResources() {
        return resources == null ? super.getResources() : resources;
    }

    @Override
    public Resources.Theme getTheme() {
        return theme == null ? super.getTheme() : theme;
    }
}
