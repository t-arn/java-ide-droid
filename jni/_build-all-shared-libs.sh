#!/bin/sh
#

### Cygwin script to build all the seperate shared libraries

### build libpng
cd libpng
pwd
./ndk-build.sh
cd ..

### build expat
cd expat
pwd
./ndk-build.sh
cd ..

### build liblog
cd liblog
pwd
./ndk-build.sh
cd ..

### build libcutils
cd libcutils
pwd
./ndk-build.sh
cd ..

### build libhost
cd libhost
pwd
./ndk-build.sh
cd ..

### build libutils
cd libutils
pwd
./ndk-build.sh
cd ..

### show built libs
echo ""
pwd
ls -l *.so
