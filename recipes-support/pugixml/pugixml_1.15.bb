SUMMARY = "Pugixml - Light-weight C++ XML processing library"
HOMEPAGE = "https://pugixml.org/"
DESCRIPTION = "pugixml is a C++ XML processing library with XPath support"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=5d518c0f61f7c223654635a98775b6da"

SRC_URI = "git://github.com/zeux/pugixml.git;branch=master;protocol=https"
SRCREV = "ee86beb30e4973f5feffe3ce63bfa4fbadf72f38"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIBS=OFF \
"

BBCLASSEXTEND = "native nativesdk"
ALLOW_EMPTY:${PN} = "1"
