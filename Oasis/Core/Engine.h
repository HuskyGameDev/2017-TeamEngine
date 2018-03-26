#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

class Application;
class Config;
class Graphics;
class Window;

namespace Engine
{

OASIS_API bool IsRunning();

OASIS_API int Start(Application* app);
OASIS_API void Stop();

OASIS_API double GetFps();
OASIS_API double GetUps();

OASIS_API Application* GetApplication();

OASIS_API Graphics* GetGraphics();

OASIS_API Window* GetWindow();

}

}
