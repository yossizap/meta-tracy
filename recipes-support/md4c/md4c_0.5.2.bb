SUMMARY = "Markdown parser and renderer"
HOMEPAGE = "https://github.com/mity/md4c"
DESCRIPTION = "MD4C is a Markdown parser implementation in C"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=383f39920f391605af6e8e46e60e2378"

SRC_URI = "git://github.com/mity/md4c.git;branch=master;protocol=https"
SRCREV = "729e6b8b320caa96328968ab27d7db2235e4fb47"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIBS=OFF \
"

BBCLASSEXTEND = "native nativesdk"
