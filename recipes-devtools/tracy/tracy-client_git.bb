SUMMARY = "Tracy client library"

require tracy-common.inc

inherit cmake pkgconfig

EXTRA_OECMAKE = " \
    -DTRACY_ENABLE=ON \
    -DTRACY_ON_DEMAND=ON \
    -DTRACY_STATIC=ON \
    -DTRACY_NO_BROADCAST=OFF \
    -DTRACY_ONLY_LOCALHOST=OFF \
    -DTRACY_NO_CODE_TRANSFER=OFF \
    -DTRACY_NO_CONTEXT_SWITCH=OFF \
    -DTRACY_NO_EXIT=OFF \
    -DTRACY_NO_SAMPLING=OFF \
    -DTRACY_NO_VERIFY=OFF \
    -DTRACY_NO_VSYNC_CAPTURE=OFF \
    -DTRACY_NO_FRAME_IMAGE=OFF \
    -DTRACY_NO_SYSTEM_TRACING=OFF \
"

EXTRA_OECMAKE:append:class-target:qemuall = " -DTRACY_TIMER_FALLBACK=ON"

# Only build the client library
OECMAKE_TARGET_COMPILE = "TracyClient"

FILES:${PN}-dev += "${libdir}/cmake/Tracy"
FILES:${PN}-staticdev += "${libdir}/libTracyClient.a"
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
