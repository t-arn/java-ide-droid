#!/bin/sh

### build libpng
cd libpng
pwd
./ndk-build.sh clean
./ndk-build.sh -B -V1
cp libs/armeabi/libpng.so ../libs/armeabi/
cd ..


### show libs
ls -l libs/armeabi/
