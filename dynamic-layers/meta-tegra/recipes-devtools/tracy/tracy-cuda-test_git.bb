SUMMARY = "Tracy CUDA validation example"
DESCRIPTION = "Small target binary that builds Tracy CUDA instrumentation with the meta-tegra CUDA toolchain"
HOMEPAGE = "https://github.com/wolfpld/tracy"

require recipes-devtools/tracy/tracy-common.inc

S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=a47e449e08c861b725d15c26107cd24e"

SRC_URI += " \
    file://0001-tracy-cuda-support-cupti-12.patch \
    file://CMakeLists.txt \
    file://tracy-cuda-test.cpp \
    file://tracy-cuda-test.cu \
"

DEPENDS = "tracy-client cuda-cudart cuda-cupti cuda-driver"

inherit cuda cmake features_check

CUDA_DEPENDS = "${CUDA_NATIVEDEPS} cuda-cudart cuda-cupti cuda-driver"
REQUIRED_MACHINE_FEATURES = "cuda"
COMPATIBLE_MACHINE = "(tegra)"

EXTRA_OECMAKE += " \
    -DTRACY_SOURCE_DIR=${S} \
    -DCUDA_INCLUDE_DIR=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/include \
    -DCUDA_CUPTI_ROOT=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/extras/CUPTI \
    -DCUDA_TARGET_LIB_DIR=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/${baselib} \
    -DTRACY_ENABLE=ON \
    -DTRACY_ON_DEMAND=ON \
"

do_configure:prepend() {
    cp ${WORKDIR}/CMakeLists.txt ${S}/
    cp ${WORKDIR}/tracy-cuda-test.cpp ${S}/
    cp ${WORKDIR}/tracy-cuda-test.cu ${S}/
}

FILES:${PN} = "${bindir}/tracy-cuda-test"
