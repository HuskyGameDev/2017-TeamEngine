#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

enum GraphicsBackend
{
    GRAPHICS_BACKEND_NONE = 0,
    GRAPHICS_BACKEND_OPENGL = 1,
    GRAPHICS_BACKEND_DONT_CARE = 2
};

struct OASIS_API Config
{
    Config()
        : graphicsBackend(GRAPHICS_BACKEND_DONT_CARE)
        , targetFps(60)
        , targetUps(60) {}

    GraphicsBackend graphicsBackend;
    double targetFps;
    double targetUps;
};

}
