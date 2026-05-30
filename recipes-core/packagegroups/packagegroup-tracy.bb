SUMMARY = "Tracy profiling package groups"
DESCRIPTION = "Package groups for Tracy target tools, examples, and on-target development files"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES =+ " \
    packagegroup-tracy-tools \
    packagegroup-tracy-examples \
    packagegroup-tracy-client-devel \
"

RDEPENDS:${PN} = " \
    packagegroup-tracy-tools \
    packagegroup-tracy-examples \
    packagegroup-tracy-client-devel \
"

RDEPENDS:packagegroup-tracy-tools = " \
    tracy-capture \
    tracy-csvexport \
"

RDEPENDS:packagegroup-tracy-examples = " \
    tracy-test \
    tracy-fibers \
"

RDEPENDS:packagegroup-tracy-client-devel = " \
    tracy-client-dev \
    tracy-client-staticdev \
"
