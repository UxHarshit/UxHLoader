//
// Created by UxHurricane on 07-06-2021.
//

#ifndef UXH_LOADER_JCLASSES_H
#define UXH_LOADER_JCLASSES_H

void mResponse(JNIEnv *env,jobject thiz,const char *txt) {
    jclass cls = env->GetObjectClass(thiz);
    jmethodID mResponse = env->GetMethodID(cls,"mResponse","(Ljava/lang/String;)V");
    const jstring text = env->NewStringUTF(txt);
    env->CallVoidMethod(thiz,mResponse,text);
}
void lResponse(JNIEnv *env,jobject thiz,const char *txt) {
    jclass cls = env->GetObjectClass(thiz);
    jmethodID mRespon = env->GetMethodID(cls,"dPercentage","(Ljava/lang/String;)V");
    const jstring text = env->NewStringUTF(txt);
    env->CallVoidMethod(thiz,mRespon,text);
    env->DeleteLocalRef(text);
    env->DeleteLocalRef(cls);
}

const void fResponse(JNIEnv *env,jobject thiz,const char *txt) {
    jclass cls = env->GetObjectClass(thiz);
    jmethodID mResponse = env->GetMethodID(cls,"fDownload","(Ljava/lang/String;)V");
    const jstring text = env->NewStringUTF(txt);
    env->CallVoidMethod(thiz,mResponse,text);
    env->DeleteLocalRef(text);
}

#endif //UXH_LOADER_JCLASSES_H
