SUMMARY = "Native File Dialog Extended"
HOMEPAGE = "https://github.com/btzy/nativefiledialog-extended"
DESCRIPTION = "A small C library for portable file dialogs"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b0ee024d29a336de81aa17cc53a8f020"

SRC_URI = "git://github.com/btzy/nativefiledialog-extended.git;branch=master;protocol=https"
SRCREV = "fc168e8605bfa51aaec22ab0c4e46b9de665a437"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "gtk+3"

EXTRA_OECMAKE = " \
    -DNFD_PORTAL=ON \
    -DBUILD_SHARED_LIBS=OFF \
"

BBCLASSEXTEND = "native nativesdk"
