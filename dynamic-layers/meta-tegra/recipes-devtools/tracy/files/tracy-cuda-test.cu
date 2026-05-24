#include <cuda_runtime.h>

__global__ void tracy_cuda_touch_kernel(int* value)
{
    if (threadIdx.x == 0) {
        *value = blockIdx.x;
    }
}

extern "C" cudaError_t tracy_cuda_touch(int* value)
{
    tracy_cuda_touch_kernel<<<1, 32>>>(value);
    return cudaGetLastError();
}
