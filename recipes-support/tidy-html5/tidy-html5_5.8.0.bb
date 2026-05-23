SUMMARY = "HTML Tidy static library"
HOMEPAGE = "http://www.html-tidy.org/"
DESCRIPTION = "Tidy is a console application for correcting and cleaning up HTML and XML documents"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README/LICENSE.md;md5=fca2fcc0c318cb66d3871f8906117b17"

SRC_URI = "git://github.com/htacg/tidy-html5.git;branch=master;protocol=https"
SRCREV = "1ca37471b48a3498f985509828cb3cf85ea129f8"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIB=OFF \
    -DTIDY_CONSOLE_SHARED=OFF \
"

FILES:${PN}-staticdev += "${libdir}/libtidy.a"
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
