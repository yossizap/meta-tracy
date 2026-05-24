SUMMARY = "Tracy Profiler - Test Application"
HOMEPAGE = "https://github.com/wolfpld/tracy"
DESCRIPTION = "Comprehensive test application demonstrating Tracy profiling features"

require tracy-common.inc

S = "${WORKDIR}/git/test"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=a47e449e08c861b725d15c26107cd24e"

DEPENDS = "tracy-client"

inherit cmake

EXTRA_OECMAKE = " \
    -DTRACY_ENABLE=ON \
    -DTRACY_NO_SYSTEM_TRACING=ON \
    -DNO_LUA=ON \
"

EXTRA_OECMAKE:append:qemuall = " -DTRACY_TIMER_FALLBACK=ON"

CXXFLAGS:append = " \
    -fdebug-prefix-map=${WORKDIR}/git=/usr/src/debug/${PN}/${PV} \
    -ffile-prefix-map=${WORKDIR}/git=/usr/src/debug/${PN}/${PV} \
    -fmacro-prefix-map=${WORKDIR}/git=/usr/src/debug/${PN}/${PV} \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/tracy-test ${D}${bindir}/
}

FILES:${PN} = "${bindir}/tracy-test"
