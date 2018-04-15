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

    Graphics* m_graphics = NULL;
    Window* m_window = NULL;
}

bool Engine::IsRunning()
{
    return m_running;
}

void Engine::Start(const Config& conf)
{
    cout << "Starting engine..." << endl;

    if (IsRunning())
    {
        cerr << "Engine is already running, cannot start!" << endl;
        return;
    }

    m_running = true;

    m_window = new SdlWindow();
    m_graphics = new OglGraphics();

    return;
}

void Engine::Stop()
{
    if (!m_running)
    {
        cerr << "Engine attempted to stop but is not running!" << endl;
    }

    cout << "Stopping application..." << endl;

    //delete m_graphics;
    m_graphics = NULL;

    //delete m_window;
    m_window = NULL;

    cout << "Done!" << endl;

    m_running = false;
}

Graphics* Engine::GetGraphics()
{
    return m_graphics;
}

Window* Engine::GetWindow()
{
    return m_window;
}

void Engine::PreUpdate(float dt)
{
    m_window->PollEvents();
}

void Engine::PostUpdate(float dt)
{

}

void Engine::PreRender()
{
    m_graphics->PreRender();
}

void Engine::PostRender()
{
    m_graphics->PostRender();
    m_window->SwapBuffers();
}

}
