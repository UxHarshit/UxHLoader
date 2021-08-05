LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := curl
LOCAL_SRC_FILES := curl/libcurl.a
LOCAL_EXPORT_CFLAGS := $(LOCAL_PATH)/include/curl/
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := ssl
LOCAL_SRC_FILES := ssl/libssl.a
LOCAL_EXPORT_CFLAGS := $(LOCAL_PATH)/include/openssl/
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := crypto
LOCAL_SRC_FILES := ssl/libcrypto.a
LOCAL_EXPORT_CFLAGS := $(LOCAL_PATH)/include/openssl/
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := teamUxH
LOCAL_SRC_FILES := uxh.cpp
LOCAL_LDLIBS := -llog -landroid -lz
LOCAL_STATIC_LIBRARIES := libcurl libssl libcrypto
LOCAL_C_INCLUDES := $(LOCAL_PATH)/include/
include $(BUILD_SHARED_LIBRARY)
