# meta-tracy

Yocto/OpenEmbedded layer for Tracy Profiler integration.

## Description

This layer provides recipes for the Tracy Profiler client library, target
utilities, target examples, and native host tools. The layer packages
dependencies as separate recipes, so builds do not rely on Tracy's CPM
dependency downloads.

## Components

- **tracy-client**: Client library for profiling applications
- **tracy-csvexport** / **tracy-csvexport-native**: CSV export utility for target and build host use
- **tracy-capture** / **tracy-capture-native**: Capture utility for target and build host use
- **tracy-profiler-native**: Native Tracy GUI profiler for the build host
- **tracy-test**: Test application demonstrating Tracy features
- **tracy-fibers**: Example demonstrating fiber tracking
- **tracy-cuda-test**: Optional Tegra CUDA validation binary when `meta-tegra` is in `BBLAYERS`
- **tracy-cuda-test-native**: Optional host CUDA validation binary gated by `tracy-cuda-native`

## Dependencies

This layer depends on:
- meta (core layer)

Optional integration:
- meta-tegra, for Tegra CUDA validation builds

When meta-oe is present, its recipes take precedence over the common library
recipes in this layer.

## Tested configurations

- Scarthgap: `qemux86-64`
- Kirkstone: `qemux86-64`
- Kirkstone with meta-tegra: `jetson-tx2-devkit-tx2i` (image build)

See [TESTING.md](TESTING.md) for the build and runtime checks. AGX Orin on
Blacksail has not been validated with this layer.

## Maintainer

- yossizap <yossizap@gmail.com>

## Contributing

Send patches and build reports to yossizap <yossizap@gmail.com>. Include the
Yocto release, machine, and layers used when reporting a build failure.

## Patch Policy

Patches under recipe `files/` directories should include `Signed-off-by`
and `Upstream-Status`.

## Usage

1. Add this layer to your `bblayers.conf`:
   ```
   bitbake-layers add-layer /path/to/meta-tracy
   ```

2. Build Tracy components:
   ```
   bitbake tracy-client
   bitbake tracy-csvexport
   bitbake tracy-capture
   bitbake tracy-profiler-native tracy-capture-native tracy-csvexport-native
   ```

3. Or build the complete image:
   ```
   bitbake core-image-minimal-tracy
   ```

4. With `meta-tegra` enabled, build the CUDA validation image for a Tegra
   machine with CUDA support:
   ```
   bitbake core-image-minimal-tracy-cuda
   ```

5. To check host CUDA instrumentation through meta-tegra's native CUDA packages,
   add `tracy-cuda-native` to `DISTRO_FEATURES_NATIVE` and build the optional
   native validation binary:
   ```
   DISTRO_FEATURES_NATIVE:append = " tracy-cuda-native"
   ```
   Then build:
   ```
   bitbake tracy-cuda-test-native
   bitbake -c addto_recipe_sysroot tracy-cuda-test-native
   oe-run-native tracy-cuda-test-native tracy-cuda-test
   ```

6. Populate the native sysroot for host tools:
   ```
   bitbake -c addto_recipe_sysroot tracy-profiler-native tracy-capture-native tracy-csvexport-native
   ```

7. Run native host tools through the Yocto native sysroot:
   ```
   oe-run-native tracy-capture-native tracy-capture --help
   oe-run-native tracy-csvexport-native tracy-csvexport --help
   oe-run-native tracy-profiler-native tracy-profiler
   ```

`tracy-profiler-native` is a GUI tool and needs a working host display and
OpenGL stack. `tracy-capture-native` and `tracy-csvexport-native` cover
headless capture and export flows.

## License

MIT (see COPYING.MIT)
