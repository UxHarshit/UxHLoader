APP_STL :=  c++_static
APP_ABI := armeabi-v7a
APP_CPPFLAGS := -std=c++17 -fno-rtti -fno-exceptions -ltinfo -DNDDEBUG -Wall -fpermissive -fpic
APP_LDFLAGS := -llog -lz
APP_PIE := true
APP_PLATFORM := android-21