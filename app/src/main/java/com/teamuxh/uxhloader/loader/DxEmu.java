package com.teamuxh.uxhloader.loader;

import android.content.Context;

import com.teamuxh.uxhloader.csl;

import java.net.URL;
import java.util.Enumeration;

import dalvik.system.DexClassLoader;

public class DxEmu extends DexClassLoader {

    public DxEmu(csl application,String dexPath,String soPath,ClassLoader classLoader) {
        super(dexPath, application.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(), soPath.replace("files","lib"), classLoader);

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    public String findLibrary(String name) {
        return super.findLibrary(name);
    }

    @Override
    protected URL findResource(String name) {
        return super.findResource(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) {
        return super.findResources(name);
    }

    @Override
    protected synchronized Package getPackage(String name) {
        return super.getPackage(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}
