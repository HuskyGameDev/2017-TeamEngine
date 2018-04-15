#pragma once

#include "Oasis/Core/Window.h"

#include <SDL2/SDL.h>

namespace Oasis
{

class OASIS_API SdlWindow : public Window
{
public:
    explicit SdlWindow(const std::string& title = "Oasis Engine");
    virtual ~SdlWindow();

    virtual const std::string& GetTitle() const;
    virtual int GetWidth() const;
    virtual int GetHeight() const;

    bool IsCloseRequested() const { return m_close; }

    virtual void SetTitle(const std::string& title);
    virtual void SetSize(int width, int height);

    virtual void Center();

    virtual void PollEvents();
    virtual void SwapBuffers();

private:
    static bool m_sdlInit;

    std::string m_title;
    SDL_Window* m_window;
    SDL_GLContext m_context;
    bool m_close;
};

}
