SUMMARY = "Parallel Pattern-defeating Quicksort"
HOMEPAGE = "https://github.com/GabTux/PPQSort"
DESCRIPTION = "PPQSort is a header-only parallel sorting library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34962c6a3b16ea1551726f924c3a30f3"

SRC_URI = "git://github.com/GabTux/PPQSort.git;branch=master;protocol=https \
           file://0001-guard-ranges-comparators.patch \
"
SRCREV = "21c5a20ca3daf572caaf0a8522db32e27e28fa1d"

S = "${WORKDIR}/git"

# Header-only library - just install headers
do_install() {
    install -d ${D}${includedir}
    cp -r ${S}/include/* ${D}${includedir}/
}

# Header-only, no runtime package needed
ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev = "${includedir}"

BBCLASSEXTEND = "native nativesdk"
