SUMMARY = "GLFW - OpenGL library"
HOMEPAGE = "https://www.glfw.org/"
DESCRIPTION = "GLFW is an Open Source, multi-platform library for OpenGL, OpenGL ES and Vulkan"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=98d93d1ddc537f9b9ea6def64e046b5f"

SRC_URI = "git://github.com/glfw/glfw.git;branch=master;protocol=https"
SRCREV = "7b6aead9fb88b3623e3b3725ebb42670cbe4c579"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "virtual/libgl libx11 libxrandr libxinerama libxcursor libxi"

EXTRA_OECMAKE = " \
    -DGLFW_BUILD_EXAMPLES=OFF \
    -DGLFW_BUILD_TESTS=OFF \
    -DGLFW_BUILD_DOCS=OFF \
    -DGLFW_INSTALL=ON \
    -DGLFW_BUILD_X11=ON \
    -DGLFW_BUILD_WAYLAND=OFF \
    -DBUILD_SHARED_LIBS=OFF \
"

BBCLASSEXTEND = "native nativesdk"
