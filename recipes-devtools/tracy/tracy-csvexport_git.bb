SUMMARY = "Tracy CSV export utility"

require tracy-common.inc

SRC_URI += " \
    file://0001-profiler-respect-toolchain-IPO-setting.patch \
    file://0002-cmake-use-system-packages-for-Yocto-builds.patch \
"

# All dependencies for Tracy utilities
DEPENDS = " \
    zstd \
    capstone \
    ppqsort \
"

inherit cmake pkgconfig

EXTRA_OECMAKE = " \
    -DUSE_SYSTEM_PACKAGES=ON \
    -DTRACY_SERVER_TOOLS_ONLY=ON \
    -DNO_ISA_EXTENSIONS=ON \
    -DCMAKE_INTERPROCEDURAL_OPTIMIZATION=OFF \
    -DCMAKE_C_COMPILER_RANLIB:FILEPATH=${@d.getVar('RANLIB').split()[0]} \
    -DCMAKE_CXX_COMPILER_RANLIB:FILEPATH=${@d.getVar('RANLIB').split()[0]} \
"

OECMAKE_SOURCEPATH = "${S}/csvexport"

FILES:${PN} = "${bindir}/tracy-csvexport"

BBCLASSEXTEND = "native nativesdk"
