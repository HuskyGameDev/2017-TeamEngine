#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

class Config;
class Graphics;
class Renderer;
class Window;

namespace Engine
{

OASIS_API bool IsRunning();

OASIS_API void Start(const Config& config);
OASIS_API void Stop();

OASIS_API void PreUpdate(float dt);
OASIS_API void PostUpdate(float dt);

OASIS_API void PreRender();
OASIS_API void PostRender();

OASIS_API Graphics* GetGraphics();

OASIS_API Window* GetWindow();

OASIS_API Renderer* GetRenderer();

}

}
