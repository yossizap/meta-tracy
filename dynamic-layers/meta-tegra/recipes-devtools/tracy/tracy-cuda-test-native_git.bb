SUMMARY = "Native Tracy CUDA validation example"
DESCRIPTION = "Host binary that builds Tracy CUDA instrumentation with meta-tegra native CUDA packages"
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

DEPENDS = "cuda-compiler-native cuda-cudart-native cuda-cupti-native cuda-driver-native"

inherit cmake features_check native

REQUIRED_DISTRO_FEATURES = "tracy-cuda-native"

EXTRA_OECMAKE += " \
    -DTRACY_SOURCE_DIR=${S} \
    -DCMAKE_CUDA_COMPILER=${STAGING_DIR_NATIVE}/usr/local/cuda-${CUDA_VERSION}/bin/nvcc \
    -DCMAKE_CUDA_TOOLKIT_ROOT_DIR=${STAGING_DIR_NATIVE}/usr/local/cuda-${CUDA_VERSION} \
    -DCMAKE_CUDA_ARCHITECTURES=OFF \
    -DCMAKE_CUDA_FLAGS=--allow-unsupported-compiler \
    -DCUDA_INCLUDE_DIR=${STAGING_DIR_NATIVE}/usr/local/cuda-${CUDA_VERSION}/include \
    -DCUDA_CUPTI_ROOT=${STAGING_DIR_NATIVE}/usr/local/cuda-${CUDA_VERSION}/extras/CUPTI \
    -DCUDA_TARGET_LIB_DIR=${STAGING_DIR_NATIVE}/usr/local/cuda-${CUDA_VERSION}/lib \
    -DTRACY_ENABLE=ON \
    -DTRACY_ON_DEMAND=ON \
"

do_configure:prepend() {
    cp ${WORKDIR}/CMakeLists.txt ${S}/
    cp ${WORKDIR}/tracy-cuda-test.cpp ${S}/
    cp ${WORKDIR}/tracy-cuda-test.cu ${S}/
}

do_install:append() {
    mv ${D}${bindir}/tracy-cuda-test ${D}${bindir}/tracy-cuda-test.bin
    cat > ${D}${bindir}/tracy-cuda-test <<EOF
#!/bin/sh
script_dir=\$(unset CDPATH; cd -- "\$(dirname -- "\$0")" && pwd)
native_usr=\$(unset CDPATH; cd -- "\${script_dir}/.." && pwd)
export LD_LIBRARY_PATH="\${native_usr}/local/cuda-${CUDA_VERSION}/lib\${LD_LIBRARY_PATH:+:\${LD_LIBRARY_PATH}}"
exec "\${script_dir}/tracy-cuda-test.bin" "\$@"
EOF
    chmod 0755 ${D}${bindir}/tracy-cuda-test
}

FILES:${PN} = "${bindir}/tracy-cuda-test ${bindir}/tracy-cuda-test.bin"
