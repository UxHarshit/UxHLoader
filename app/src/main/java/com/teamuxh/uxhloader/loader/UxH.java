package com.teamuxh.uxhloader.loader;

import java.lang.reflect.Field;

public class UxH<T> {
    private Object obj;
    private boolean inited;
    private Field field;
    public  UxH(Object obj){
        if (obj == null){
            throw new IllegalArgumentException();
        }
        this.obj = obj;
    }
    private void prepare(){
        if (inited)
            return;
        inited = true;
        Class<?> c = obj.getClass();
        while (c != null){
            try {
                Field f = c.getDeclaredField("parent");
                f.setAccessible(true);
                field = f;
                return;
            } catch (Exception e){

            } finally {
                c = c.getSuperclass();
            }
        }
    }
    public  T get() throws NoSuchFieldException,IllegalAccessException,IllegalArgumentException{
        prepare();
        if (field == null){
            throw new NoSuchFieldException();
        }
        try {

            T r = (T) field.get(obj);
            return  r;
        } catch (ClassCastException e){
            throw new IllegalArgumentException("개체를 캐스팅 할 수 없음");
        }
    }
    public void set(T val) throws NoSuchFieldException,IllegalArgumentException,IllegalAccessException{
        prepare();
        if (field == null)
            throw new NoSuchFieldException();
        field.set(obj,val);
    }
}
