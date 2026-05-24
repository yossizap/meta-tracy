SUMMARY = "Tracy Profiler - Fibers Example"
HOMEPAGE = "https://github.com/wolfpld/tracy"
DESCRIPTION = "Lightweight example demonstrating Tracy fiber tracking"

require tracy-common.inc

S = "${WORKDIR}/git"

DEPENDS = "tracy-client"

TRACY_FIBERS_TIMER_FLAGS = ""
TRACY_FIBERS_TIMER_FLAGS:append:qemuall = " -DTRACY_TIMER_FALLBACK"

do_compile() {
    ${CXX} ${CPPFLAGS} ${CXXFLAGS} \
        -std=c++11 \
        -DTRACY_ENABLE \
        -DTRACY_FIBERS \
        ${TRACY_FIBERS_TIMER_FLAGS} \
        -I${STAGING_INCDIR}/tracy/tracy \
        ${S}/examples/fibers.cpp \
        ${LDFLAGS} \
        -L${STAGING_LIBDIR} -lTracyClient \
        -pthread -ldl \
        -o tracy-fibers
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/tracy-fibers ${D}${bindir}/
}

FILES:${PN} = "${bindir}/tracy-fibers"
