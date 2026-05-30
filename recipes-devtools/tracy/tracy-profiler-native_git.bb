SUMMARY = "Tracy profiler GUI for the build host"

require tracy-common.inc

SRC_URI += " \
    file://0001-profiler-respect-toolchain-IPO-setting.patch \
    file://0002-cmake-use-system-packages-for-Yocto-builds.patch \
    file://0003-profiler-use-range-v3-for-LLM-ranges-code.patch \
    file://0004-profiler-support-cmake-3.22.patch \
"

DEPENDS = " \
    base64-native \
    capstone-native \
    curl-native \
    fp16-native \
    freetype-native \
    glfw-native \
    imgui-native \
    md4c-native \
    nlohmann-json-native \
    ppqsort-native \
    pugixml-native \
    range-v3-native \
    tidy-html5-native \
    usearch-native \
    zstd-native \
"

inherit cmake pkgconfig native

OECMAKE_SOURCEPATH = "${S}/profiler"

EXTRA_OECMAKE = " \
    -DUSE_SYSTEM_PACKAGES=ON \
    -DNO_FILESELECTOR=ON \
    -DLEGACY=ON \
    -DNO_ISA_EXTENSIONS=ON \
    -DCMAKE_INTERPROCEDURAL_OPTIMIZATION=OFF \
    -DCMAKE_C_COMPILER_RANLIB:FILEPATH=${@d.getVar('RANLIB').split()[0]} \
    -DCMAKE_CXX_COMPILER_RANLIB:FILEPATH=${@d.getVar('RANLIB').split()[0]} \
"

FILES:${PN} = "${bindir}/tracy-profiler"
