SUMMARY = "JSON for Modern C++"
HOMEPAGE = "https://github.com/nlohmann/json"
DESCRIPTION = "JSON for Modern C++ is a header-only C++ library for JSON parsing and serialization"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.MIT;md5=f969127d7b7ed0a8a63c2bbeae002588"

SRC_URI = "git://github.com/nlohmann/json.git;branch=master;protocol=https"
SRCREV = "65ee68451d8eb2b5f3a30b410476ab83deb3289b"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " \
    -DJSON_BuildTests=OFF \
    -DJSON_Install=ON \
"

# Header-only library
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
