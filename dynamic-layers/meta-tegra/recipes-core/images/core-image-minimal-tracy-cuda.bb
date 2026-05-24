SUMMARY = "Minimal Tracy CUDA validation image"
DESCRIPTION = "Console image with Tracy tools, examples, and the CUDA validation binary for Tegra targets"
LICENSE = "MIT"

require recipes-core/images/core-image-minimal-tracy.bb

IMAGE_INSTALL += "tracy-cuda-test"
