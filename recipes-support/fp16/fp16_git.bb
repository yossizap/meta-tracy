SUMMARY = "Header-only library for half-precision floating point conversion"
HOMEPAGE = "https://github.com/Maratyszcza/FP16"
DESCRIPTION = "FP16 provides conversion routines between half-precision and single-precision floating point formats."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=998fb0b16ad8a4fb8bd41bf3faf2d21c"

SRC_URI = "git://github.com/Maratyszcza/FP16.git;branch=master;protocol=https"
SRCREV = "3d2de1816307bac63c16a297e8c4dc501b4076df"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${includedir}
    cp -R --no-dereference --preserve=mode,links ${S}/include/* ${D}${includedir}/
}

ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev = "${includedir}"

BBCLASSEXTEND = "native nativesdk"
