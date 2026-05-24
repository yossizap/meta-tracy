cmake_minimum_required(VERSION 3.18)

project(tracy_cuda_test LANGUAGES CXX CUDA)

add_executable(tracy-cuda-test
    tracy-cuda-test.cpp
    tracy-cuda-test.cu
    ${TRACY_SOURCE_DIR}/public/TracyClient.cpp
)

target_compile_options(tracy-cuda-test PRIVATE
    $<$<COMPILE_LANGUAGE:CXX>:-std=c++17>
)
target_compile_definitions(tracy-cuda-test PRIVATE
    TRACY_ENABLE
    TRACY_ON_DEMAND
)
target_include_directories(tracy-cuda-test PRIVATE
    ${TRACY_SOURCE_DIR}/public
    ${CUDA_INCLUDE_DIR}
    ${CUDA_CUPTI_ROOT}/include
)
target_link_directories(tracy-cuda-test PRIVATE
    ${CUDA_CUPTI_ROOT}/lib64
    ${CUDA_TARGET_LIB_DIR}
    ${CUDA_TARGET_LIB_DIR}/stubs
)
target_link_libraries(tracy-cuda-test PRIVATE
    cuda
    cudart
    cupti
    dl
    pthread
)

install(TARGETS tracy-cuda-test RUNTIME DESTINATION bin)
