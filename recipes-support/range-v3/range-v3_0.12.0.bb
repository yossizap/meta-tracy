SUMMARY = "Range library for C++14/17/20"
HOMEPAGE = "https://github.com/ericniebler/range-v3"
DESCRIPTION = "Range-v3 is a generic range library for C++14 and later."
LICENSE = "BSL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5dc23d5193abaedb6e42f05650004624"

SRC_URI = "git://github.com/ericniebler/range-v3.git;branch=master;protocol=https"
SRCREV = "a81477931a8aa2ad025c6bda0609f38e09e4d7ec"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${includedir}
    cp -R --no-dereference --preserve=mode,links ${S}/include/* ${D}${includedir}/
}

ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev = "${includedir}"

BBCLASSEXTEND = "native nativesdk"
