#include <jni.h>
#include <string.h>
#include <string>
#include <stdio.h>
#include "curl/curl.h"
#include "mics.h"
#include "jclasses.h"
#include "mics/logs.h"
#include "mics/hDialog.h"


JNIEnv *tEnv;
jobject tThiz;
jstring tPercentage;
hDialog sPercentage;

static size_t WriteCallback(void *contents, size_t size, size_t nmemb, void *userp)
{
    ((std::string*)userp)->append((char*)contents, size * nmemb);
    return size * nmemb;
}
static size_t WriteData(void *ptr,size_t size,size_t nmemb,FILE *stream)
{
    size_t written = fwrite(ptr, size , nmemb ,stream);
    return written;
}
const static int progressCallback(double t, double d, double ultotal,double ulnow)
{
    float percentage;
    int total = (int) d;
    int tDownload = (int) ultotal;
    percentage = (static_cast<float>(tDownload)/ static_cast<float>(total)) * 100.0;
    std::string fPer = "";
    fPer = std::to_string(percentage);
    lResponse(tEnv,tThiz,fPer.c_str());
    return 0;
}
extern "C"
{
    JNIEXPORT void JNICALL Java_com_teamuxh_uxhloader_MainActivity_mcontent(JNIEnv *env,jobject thiz, jobject con){
        CURL *curl;
        CURLcode res;
        std::string readBuffer;
        curl = curl_easy_init();

        if(curl){
            curl_easy_setopt(curl, CURLOPT_URL, "http://35.198.199.229/UxHGCore/");
            curl_easy_setopt(curl, CURLOPT_POSTFIELDS, "aResponse=3943:4443:1.0.0");
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, &readBuffer);
            res = curl_easy_perform(curl);
            curl_easy_cleanup(curl);
            std::string sDown = "down";
            if(!readBuffer.compare(sDown)){
                mResponse(env,con,"Server under maintenance!! ");
            } else {
                mResponse(env,con,readBuffer.c_str());
            }
        }
    }

    JNIEXPORT void JNICALL Java_com_teamuxh_uxhloader_MainActivity_dcontent(JNIEnv *env,jobject thiz,jobject con){
        CURL *curl;
        FILE *fp;
        CURLcode res;
        tThiz = con;
        tEnv = env;

        char *url = "http://35.198.199.229/UxHGCore/app-debug.apk";
        char outfilename[FILENAME_MAX] = "/storage/emulated/0/Android/data/com.teamuxh.uxhloader/files/glPlugin.apk";
        curl = curl_easy_init();
        if(curl){
            fp = fopen(outfilename,"wb");
            curl_easy_setopt(curl,CURLOPT_URL,url);
            curl_easy_setopt(curl, CURLOPT_POSTFIELDS, "aResponse=3943");
            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, NULL);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
            curl_easy_setopt(curl, CURLOPT_NOPROGRESS, 0L);
            curl_easy_setopt(curl, CURLOPT_PROGRESSFUNCTION, progressCallback);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, fp);
            res = curl_easy_perform(curl);
            curl_easy_cleanup(curl);
            fclose(fp);
        }
        fResponse(env,con,"성공적으로 다운로드 됨");
    }
}
