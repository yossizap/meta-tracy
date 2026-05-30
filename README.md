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

## Dependencies

This layer depends on:
- meta (core layer)

## Compatibility

Tested:
- Yocto Scarthgap (5.0 LTS), qemux86-64

Expected:
- Yocto Kirkstone (4.0 LTS)

## Maintainer

- yossizap <yossizap@gmail.com>

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

4. Populate the native sysroot for host tools:
   ```
   bitbake -c addto_recipe_sysroot tracy-profiler-native tracy-capture-native tracy-csvexport-native
   ```

5. Run native host tools through the Yocto native sysroot:
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
