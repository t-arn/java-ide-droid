#!/bin/sh
#

### Cygwin script to build all the seperate shared libraries

### build libpng
cd libpng
pwd
./ndk-build.sh
cp libs/armeabi/libpng.so ..
cd ..

### build expat
cd expat
pwd
./ndk-build.sh
cp libs/armeabi/libpng.so ..
cd ..

### build libcutils
cd libcutils
pwd
./ndk-build.sh
cp libs/armeabi/libcutils.so ..
cd ..

### build libcutils
cd libcutils
pwd
./ndk-build.sh
cp libs/armeabi/libcutils.so ..
cd ..

### build libhost
cd libhost
pwd
./ndk-build.sh
cp libs/armeabi/libhost.so ..
cd ..

### show built libs
echo ""
pwd
ls -l *.so
