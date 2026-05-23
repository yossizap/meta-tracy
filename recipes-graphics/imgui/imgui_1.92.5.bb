SUMMARY = "Dear ImGui C++ immediate-mode GUI library"
HOMEPAGE = "https://github.com/ocornut/imgui"
DESCRIPTION = "Dear ImGui is a bloat-free graphical user interface library for C++"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5ef20940e74d064639c66f32813613b0"

SRC_URI = "git://github.com/ocornut/imgui.git;branch=docking;protocol=https \
           file://CMakeLists.txt \
           file://0001-opengl3-loader-add-tracy-required-symbols.patch \
"
SRCREV = "3912b3d9a9c1b3f17431aebafd86d2f40ee6e59c"

S = "${WORKDIR}/git"

do_configure:prepend() {
    cp ${WORKDIR}/CMakeLists.txt ${S}/CMakeLists.txt
}

inherit cmake pkgconfig

DEPENDS = "freetype"

# Tracy includes the GLFW backend source directly when it links against system ImGui.
FILES:${PN}-dev += "${includedir}/imgui/backends/imgui_impl_glfw.cpp"
FILES:${PN}-staticdev += "${libdir}/libimgui.a"
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
