#include <cuda_runtime.h>

#include <tracy/Tracy.hpp>
#include <tracy/TracyCUDA.hpp>

extern "C" cudaError_t tracy_cuda_touch(int* value);

int main()
{
    ZoneScoped;

    auto* tracy_context = TracyCUDAContext();
    TracyCUDAContextName(tracy_context, "default", 7);

    int* device_value = nullptr;
    if (cudaMalloc(&device_value, sizeof(*device_value)) != cudaSuccess) {
        TracyCUDAContextDestroy(tracy_context);
        return 1;
    }

    const auto launch_result = tracy_cuda_touch(device_value);
    const auto sync_result = launch_result == cudaSuccess ? cudaDeviceSynchronize() : launch_result;

    TracyCUDACollect(tracy_context);
    TracyCUDAContextDestroy(tracy_context);
    cudaFree(device_value);

    return sync_result == cudaSuccess ? 0 : 1;
}
