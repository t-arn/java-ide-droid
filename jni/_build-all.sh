#!/bin/sh
#

### Cygwin script to build all the native code

### build libpng
cd libpng
pwd
./ndk-build.sh
cp libs/armeabi/libpng.so ../../libs/armeabi/
cd ..

### build expat
cd expat
pwd
./ndk-build.sh
cp libs/armeabi/libpng.so ../../libs/armeabi/
cd ..

### show built libs
ls -l ../libs/armeabi/
