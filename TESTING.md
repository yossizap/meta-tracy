# meta-tracy Validation Notes

GitHub Actions runs `yocto-check-layer` and builds the target client, capture,
and CSV export recipes on Scarthgap and Kirkstone with meta-oe present.

Validated with Poky Scarthgap on qemux86-64 using a local build directory.
Kirkstone was also checked with qemux86-64 and with a meta-tegra Jetson TX2i
configuration.

## Build

This command completed:

```bash
bitbake core-image-minimal-tracy
bitbake -c addto_recipe_sysroot \
    tracy-profiler-native \
    tracy-capture-native \
    tracy-csvexport-native
```

The image manifest contains the target tools and examples:

- `tracy-capture`
- `tracy-csvexport`
- `tracy-test`
- `tracy-fibers`

## Native Tools

Run the native tools through `oe-run-native`:

```bash
oe-run-native tracy-capture-native tracy-capture --help
oe-run-native tracy-csvexport-native tracy-csvexport --help
oe-run-native tracy-profiler-native tracy-profiler
```

`tracy-csvexport-native` parsed both generated sample traces:

- `qemu-sample.tracy`
- `host-qemu-sample.tracy`

`tracy-profiler-native` opened a generated trace from a graphical host session.

## QEMU Target Runtime

We booted the target image with QEMU slirp and Tracy port forwarding:

```bash
QB_SLIRP_OPT='-netdev user,id=net0,hostfwd=tcp:127.0.0.1:8086-:8086,tftp=./tmp/deploy/images/qemux86-64' \
runqemu ./tmp/deploy/images/qemux86-64/core-image-minimal-tracy-qemux86-64.rootfs.qemuboot.conf \
    nographic slirp qemuparams='-m 1024'
```

The following target binaries ran in QEMU:

- `tracy-test`
- `tracy-fibers`
- `tracy-capture`
- `tracy-csvexport`

In-guest capture from `tracy-test` produced `qemu-sample.tracy`, and native
CSV export parsed it.

Host-to-QEMU capture also worked through forwarded `127.0.0.1:8086`:

```text
Frames: 1891
Time span: 9.72 s
Zones: 1,554,392
Trace size: 12.79 MB
```

Native CSV export parsed `host-qemu-sample.tracy`, and the native profiler
opened it.

## Tegra CUDA Build

With Poky Kirkstone and meta-tegra, this layer parsed and built for
`MACHINE = "jetson-tx2-devkit-tx2i"` using shared downloads and sstate caches.

These targets completed:

```bash
bitbake tracy-cuda-test
bitbake core-image-minimal-tracy-cuda
```

The image manifest contains:

- `tracy-cuda-test`
- `tracy-capture`
- `tracy-csvexport`
- `tracy-client`
- `tracy-test`
- `tracy-fibers`

The CUDA support lives under `dynamic-layers/meta-tegra`, so BitBake only
parses it when the `tegra` collection is present. The validation recipe
requires the `cuda` machine feature and only matches Tegra machines.

## Native CUDA Build

With meta-tegra present, `tracy-cuda-test-native` checks that the same Tracy
CUDA instrumentation builds against the host CUDA packages from meta-tegra.
The recipe requires this native distro feature:

```conf
DISTRO_FEATURES_NATIVE:append = " tracy-cuda-native"
```

The native binary is separate from `tracy-profiler-native`; the profiler does
not need CUDA to open traces with CUDA events.

This host validation completed on an NVIDIA RTX A4000 with driver 570.133.07:

```bash
bitbake tracy-cuda-test-native
bitbake -c addto_recipe_sysroot tracy-cuda-test-native
oe-run-native tracy-cuda-test-native tracy-cuda-test
```

Host capture of the same binary also completed:

```bash
oe-run-native tracy-capture-native tracy-capture \
    -o native-cuda-test.tracy \
    -a 127.0.0.1 \
    -p 8086 \
    -s 2

oe-run-native tracy-cuda-test-native tracy-cuda-test
oe-run-native tracy-csvexport-native tracy-csvexport native-cuda-test.tracy
oe-run-native tracy-profiler-native tracy-profiler native-cuda-test.tracy
```

The resulting trace had CUDA zones and opened in `tracy-profiler-native`.

## Notes

- qemux86-64's default 256 MB memory did not give `tracy-test` enough room for
  the host-capture handshake; QEMU killed the test process under memory pressure.
  The validated host-capture flow uses `qemuparams='-m 1024'`.
- The recipes enable `TRACY_TIMER_FALLBACK` only for qemu target client and
  example builds. Non-QEMU targets use Tracy's normal platform timer path.
