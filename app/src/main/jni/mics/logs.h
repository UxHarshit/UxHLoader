//
// Created by UxHurricane on 12-06-2021.
//

#ifndef UXH_LOADER_LOGS_H
#define UXH_LOADER_LOGS_H

#include <android/log.h>

#define TAG ""
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,    TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,     TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,     TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,    TAG, __VA_ARGS__)

#endif //UXH_LOADER_LOGS_H
