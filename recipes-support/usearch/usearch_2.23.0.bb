SUMMARY = "USearch C++ vector search headers"
HOMEPAGE = "https://github.com/unum-cloud/usearch"
DESCRIPTION = "Single-header vector search engine for C++, Python, JavaScript, Rust, Java, C, and Swift"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/unum-cloud/usearch.git;branch=main;protocol=https"
SRCREV = "7306bb446be5f0f0c529ec8acdc57361cef8a8a7"

S = "${WORKDIR}/git"

# Header-only library
do_install() {
    install -d ${D}${includedir}/usearch
    install -m 0644 ${S}/include/usearch/*.hpp ${D}${includedir}/usearch/
}

ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev = "${includedir}/usearch"

BBCLASSEXTEND = "native nativesdk"
