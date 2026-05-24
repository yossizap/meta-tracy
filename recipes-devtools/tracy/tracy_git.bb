SUMMARY = "Tracy target tools"
HOMEPAGE = "https://github.com/wolfpld/tracy"
DESCRIPTION = "Tracy target utility meta-package for capture and CSV export"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS:${PN} = "tracy-csvexport tracy-capture"

# This is a meta-package, no actual build
ALLOW_EMPTY:${PN} = "1"
