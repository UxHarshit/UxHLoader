//
// Created by UxHurricane on 06-06-2021.
//
#ifndef UXH_LOADER_MICS_H
#define UXH_LOADER_MICS_H
#include <jni.h>
void toast(JNIEnv *env,jobject thiz,jobject text){
    jclass toast = env->FindClass("android/widget/Toast");
    jmethodID methodMakeText = env->GetStaticMethodID(toast, "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");
    if(methodMakeText == NULL){
        return;
    }
    jobject toastobj = env->CallStaticObjectMethod(toast, methodMakeText, thiz, text, 0);
    jmethodID methodShow = env->GetMethodID(toast, "show", "()V");
    env->CallVoidMethod(toastobj, methodShow);
}
#endif //UXH_LOADER_MICS_H
