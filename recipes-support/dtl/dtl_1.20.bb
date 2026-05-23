SUMMARY = "Diff Template Library"
HOMEPAGE = "https://github.com/cubicdaiya/dtl"
DESCRIPTION = "DTL is a header-only C++ library for computing differences between sequences"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=9b0534944ce056d57a760f156553528a"

# DTL is bundled with Tracy
SRC_URI = "git://github.com/cubicdaiya/dtl.git;branch=master;protocol=https"
SRCREV = "cd45dd48089c6e9f7a595d920ddfb82c08d59150"

S = "${WORKDIR}/git"

# Header-only library
do_install() {
    install -d ${D}${includedir}/dtl
    cp -r ${S}/dtl/*.hpp ${D}${includedir}/dtl/
}

ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev = "${includedir}/dtl"

BBCLASSEXTEND = "native nativesdk"
