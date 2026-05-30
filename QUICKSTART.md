# Quick Start Guide

## Prerequisites

- Yocto-compatible Linux distribution
- Poky Scarthgap checkout
- Build packages required by Poky
- Enough disk space for a full image and native GUI build

## Setup

```bash
source poky-scarthgap/oe-init-build-env build
bitbake-layers add-layer ../meta-tracy
```

Set this in `conf/local.conf`:

```conf
MACHINE = "qemux86-64"
```

## Build

Build the target validation image and native host tools:

```bash
bitbake core-image-minimal-tracy
bitbake -c addto_recipe_sysroot \
    tracy-profiler-native \
    tracy-capture-native \
    tracy-csvexport-native
```

Use the Yocto native sysroot flow for host tools:

```bash
oe-run-native tracy-capture-native tracy-capture --help
oe-run-native tracy-csvexport-native tracy-csvexport --help
oe-run-native tracy-profiler-native tracy-profiler
```

`tracy-profiler-native` needs a graphical host session. Use
`tracy-capture-native` and `tracy-csvexport-native` for headless checks.

## Run QEMU

For host-to-QEMU capture, forward Tracy's default port and give the test
program enough memory:

```bash
QB_SLIRP_OPT='-netdev user,id=net0,hostfwd=tcp:127.0.0.1:8086-:8086,tftp=./tmp/deploy/images/qemux86-64' \
runqemu ./tmp/deploy/images/qemux86-64/core-image-minimal-tracy-qemux86-64.rootfs.qemuboot.conf \
    nographic slirp qemuparams='-m 1024'
```

Inside QEMU:

```bash
tracy-test >/tmp/tracy-test.out 2>&1 &
```

From the host:

```bash
oe-run-native tracy-capture-native tracy-capture \
    -o host-qemu-sample.tracy \
    -a 127.0.0.1 \
    -p 8086 \
    -s 2

oe-run-native tracy-csvexport-native tracy-csvexport host-qemu-sample.tracy
oe-run-native tracy-profiler-native tracy-profiler host-qemu-sample.tracy
```

The default qemux86-64 memory can be too small for `tracy-test` during the
capture handshake. Use `qemuparams='-m 1024'` for this validation flow, or use
`tracy-capture -m <megabytes>` when intentionally limiting capture memory.

## Target Checks

Inside the image:

```bash
tracy-test
tracy-fibers
tracy-capture --help
tracy-csvexport --help
```

The image installs these package groups:

- `packagegroup-tracy-tools`: `tracy-capture`, `tracy-csvexport`
- `packagegroup-tracy-examples`: `tracy-test`, `tracy-fibers`
- `packagegroup-tracy-client-devel`: headers, CMake config, and static client library

## Integrating Tracy

Add the client library to your recipe:

```bitbake
DEPENDS += "tracy-client"
```

In C++:

```cpp
#include <tracy/Tracy.hpp>

void work()
{
    ZoneScoped;
}
```

Compile with `-DTRACY_ENABLE` and link with `Tracy::TracyClient` from the
installed CMake package, or with `-lTracyClient -lpthread -ldl` in manual builds.
