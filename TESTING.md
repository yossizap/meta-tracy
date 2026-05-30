# meta-tracy Validation Notes

Validated with Poky Scarthgap on qemux86-64 using a local build directory.

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

The target image was booted with QEMU slirp and Tracy port forwarding:

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

## Notes

- qemux86-64's default 256 MB memory did not give `tracy-test` enough room for
  the host-capture handshake; QEMU killed the test process under memory pressure.
  The validated host-capture flow uses `qemuparams='-m 1024'`.
- The recipes enable `TRACY_TIMER_FALLBACK` only for qemu target client and
  example builds. Non-QEMU targets use Tracy's normal platform timer path.
