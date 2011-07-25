#!/bin/sh

### build zlib
cd zlib
./ndk-build.sh clean
./ndk-build.sh -B -V1
cd ..

### build libpng
cd libpng
pwd
./ndk-build.sh clean
./ndk-build.sh -B -V1
cd ..
