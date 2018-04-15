#include "Oasis/Core/SDL/SdlWindow.h"

#include <GL/glew.h>

#include <iostream>

using namespace std;

namespace Oasis
{

bool SdlWindow::m_sdlInit = false;

SdlWindow::SdlWindow(const std::string& title)
    : m_title(title)
    , m_window(NULL)
    , m_context(NULL)
    , m_close(false)
{
    // init SDL if it has not already been
    if (!m_sdlInit) SDL_Init(SDL_INIT_EVERYTHING);

    m_window = SDL_CreateWindow
    (
        title.c_str(),
        SDL_WINDOWPOS_CENTERED,
        SDL_WINDOWPOS_CENTERED,
        640, 480,
        SDL_WINDOW_SHOWN | SDL_WINDOW_OPENGL | SDL_WINDOW_RESIZABLE
    );

    SDL_GL_SetAttribute(SDL_GL_CONTEXT_PROFILE_MASK, SDL_GL_CONTEXT_PROFILE_COMPATIBILITY);
    SDL_GL_SetAttribute(SDL_GL_CONTEXT_MAJOR_VERSION, 2);
    SDL_GL_SetAttribute(SDL_GL_CONTEXT_MINOR_VERSION, 1);

    m_context = SDL_GL_CreateContext(m_window);
    SDL_GL_MakeCurrent(m_window, m_context);

    glewExperimental = GL_TRUE;
    glewInit();
}

SdlWindow::~SdlWindow()
{
    SDL_GL_DeleteContext(m_context);
    SDL_DestroyWindow(m_window);

    m_context = NULL;
    m_window = NULL;
}

const std::string& SdlWindow::GetTitle() const
{
    return m_title;
}

int SdlWindow::GetWidth() const
{
    int width;
    SDL_GetWindowSize(m_window, &width, NULL);
    return width;
}

int SdlWindow::GetHeight() const
{
    int height;
    SDL_GetWindowSize(m_window, NULL, &height);
    return height;
}

void SdlWindow::SetTitle(const std::string& title)
{
    m_title = title;
    SDL_SetWindowTitle(m_window, title.c_str());
}

void SdlWindow::SetSize(int width, int height)
{
    SDL_SetWindowSize(m_window, width, height);
}

void SdlWindow::Center()
{
    SDL_SetWindowPosition(m_window, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED);
}

void SdlWindow::PollEvents()
{
    SDL_Event e;

    while (SDL_PollEvent(&e))
    {
        switch (e.type)
        {
        case SDL_QUIT:
            m_close = true;
        }
    }
}

void SdlWindow::SwapBuffers()
{
    SDL_GL_SwapWindow(m_window);
}

}
