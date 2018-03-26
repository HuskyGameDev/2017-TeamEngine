#include "Oasis/Core/Engine.h"

#include "Oasis/Core/Application.h"
#include "Oasis/Core/Timer.h"

#include "Oasis/Core/SDL/SdlWindow.h"

#include "Oasis/Graphics/OpenGL/OglGraphics.h"

#include <iostream>

using namespace std;

namespace Oasis
{

namespace // private data
{
    bool m_running = false;
    double m_fps = 0;
    double m_ups = 0;

    Application* m_app = NULL;
    Graphics* m_graphics = NULL;
    Window* m_window = NULL;
}

void PreUpdate(float dt);
void PostUpdate(float dt);

void PreRender();
void PostRender();

bool Engine::IsRunning()
{
    return m_running;
}

int Engine::Start(Application* app)
{
    cout << "Starting engine..." << endl;

    if (IsRunning())
    {
        cerr << "Engine is already running, cannot start!" << endl;
        return 1;
    }

    Config conf = app->GetConfig();

    m_running = true;
    m_app = app;

    m_window = new SdlWindow();
    m_graphics = new OglGraphics();

    // engine is valid and is not running at this point

    app->Init();

    int tickCount = 0;
    int frameCount = 0;

    float dt = 1.0 / conf.targetUps;

    Timer timer;
    Timer secondTimer;

    double frameTimer = 0;
    double skipFrames = 1.0 / conf.targetFps;

    double tickTimer = 0;
    double skipTicks = 1.0 / conf.targetUps;

    while (m_running)
    {
        int loop = 0;

        while (loop++ < 10 && tickTimer < timer.GetSeconds())
        {
            PreUpdate(dt);
            app->Update(dt);
            PostUpdate(dt);

            tickTimer += skipTicks;
            tickCount++;
        }

        if (frameTimer < timer.GetSeconds())
        {
            PreRender();
            app->Render();
            PostRender();

            frameTimer += skipFrames;
            frameCount++;
        }

        if (secondTimer.GetSeconds() >= 1)
        {
            cout << "FPS: " << frameCount << ", Ticks: " << tickCount << endl;
            m_fps = frameCount;
            m_ups = tickCount;
            tickCount = frameCount = 0;

            secondTimer.Reset();
        }
    }

    app->Exit();

    cout << "Stopping application..." << endl;

    //delete m_graphics;
    m_graphics = NULL;

    //delete m_window;
    m_window = NULL;

    //delete m_app;
    m_app = NULL;

    cout << "Done!" << endl;
    return 0;
}

void Engine::Stop()
{
    if (!m_running)
    {
        cerr << "Engine attempted to stop but is not running!" << endl;
    }

    m_running = false;
}

double Engine::GetFps()
{
    return m_fps;
}

double Engine::GetUps()
{
    return m_ups;
}

Application* Engine::GetApplication()
{
    return m_app;
}

Graphics* Engine::GetGraphics()
{
    return m_graphics;
}

Window* Engine::GetWindow()
{
    return m_window;
}

void PreUpdate(float dt)
{
    m_window->PollEvents();
}

void PostUpdate(float dt)
{

}

void PreRender()
{
    m_graphics->PreRender();
}

void PostRender()
{
    m_graphics->PostRender();
    m_window->SwapBuffers();
}

}
