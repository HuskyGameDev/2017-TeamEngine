#include "Oasis/Core/Application.h"

#include "Oasis/Core/Engine.h"
#include "Oasis/Core/Timer.h"
#include "Oasis/Core/Window.h"

#include <iostream>

using namespace std;

namespace Oasis
{

Application::Application()
    : m_running(false)
    , m_fps(0)
    , m_ups(0) {}

int Application::Start()
{
    if (m_running) return -1;

    m_running = true;

    Config conf = GetConfig();

    Engine::Start(conf);

    Init();

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
            Engine::PreUpdate(dt);
            Update(dt);
            Engine::PostUpdate(dt);

            tickTimer += skipTicks;
            tickCount++;
        }

        if (frameTimer < timer.GetSeconds())
        {
            Engine::PreRender();
            Render();
            Engine::PostRender();

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

        if (Engine::GetWindow()->IsCloseRequested())
        {
            Stop();
        }
    }

    Exit();

    Engine::Stop();

    return 0;
}

void Application::Stop()
{
    m_running = false;
}

}
