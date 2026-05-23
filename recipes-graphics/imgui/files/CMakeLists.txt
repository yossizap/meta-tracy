cmake_minimum_required(VERSION 3.10)
project(imgui)

find_package(Freetype REQUIRED)

set(IMGUI_SOURCES
    imgui.cpp
    imgui_demo.cpp
    imgui_draw.cpp
    imgui_tables.cpp
    imgui_widgets.cpp
    misc/freetype/imgui_freetype.cpp
    backends/imgui_impl_opengl3.cpp
)

add_library(imgui STATIC ${IMGUI_SOURCES})
target_include_directories(imgui PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(imgui PUBLIC Freetype::Freetype)
target_compile_definitions(imgui PUBLIC IMGUI_ENABLE_FREETYPE)

install(TARGETS imgui DESTINATION lib)
install(FILES imgui.h imconfig.h imgui_internal.h imstb_rectpack.h
              imstb_textedit.h imstb_truetype.h DESTINATION include/imgui)
install(FILES backends/imgui_impl_opengl3.h backends/imgui_impl_opengl3_loader.h
              backends/imgui_impl_glfw.h
              backends/imgui_impl_glfw.cpp DESTINATION include/imgui/backends)
install(FILES misc/freetype/imgui_freetype.h DESTINATION include/imgui/misc/freetype)
