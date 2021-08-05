//
// Created by hurri on 14-06-2021.
//

#ifndef UXH_LOADER_HDIALOG_H
#define UXH_LOADER_HDIALOG_H


class hDialog {
private:
    JNIEnv *tEnv;
    jobject tThiz;
    jstring tPercentage;
public:
    hDialog() {
        tEnv = nullptr;
        tThiz = nullptr;
        tPercentage = nullptr;
    }
    hDialog(JNIEnv *env,jobject thiz,const char *txt){
        this->tThiz = thiz;
        this->tEnv = env;
        jstring pTxt = tEnv->NewStringUTF(txt);
        this->tPercentage = pTxt;
    }

    void mResponse(JNIEnv *env,jobject thiz,const char *txt) {
        jclass cls = env->GetObjectClass(thiz);
        jmethodID mResponse = env->GetMethodID(cls,"dPercentage","(Ljava/lang/String;)V");
        const jstring text = env->NewStringUTF(txt);
        env->CallVoidMethod(thiz,mResponse,text);
    }
};


#endif //UXH_LOADER_HDIALOG_H
