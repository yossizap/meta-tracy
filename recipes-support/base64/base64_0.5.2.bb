SUMMARY = "Base64 encoding/decoding library"
HOMEPAGE = "https://github.com/aklomp/base64"
DESCRIPTION = "Fast Base64 stream encoder/decoder in C99, with SIMD acceleration"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6281c1e587393eb2099f2d9219034d24"

SRC_URI = "git://github.com/aklomp/base64.git;branch=master;protocol=https"
SRCREV = "8bdda2d47caf8b066999c5bd01069e55bcd0d396"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
    -DBASE64_BUILD_CLI=OFF \
    -DBASE64_WITH_OpenMP=OFF \
    -DBUILD_SHARED_LIBS=OFF \
"

BBCLASSEXTEND = "native nativesdk"
ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-staticdev += "${libdir}/libbase64.a"
