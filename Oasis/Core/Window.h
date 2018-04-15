#pragma once

#include "Oasis/Oasis.h"

#include <string>

namespace Oasis
{

class OASIS_API Window
{
public:
    virtual ~Window() {}

    virtual const std::string& GetTitle() const = 0;
    virtual int GetWidth() const = 0;
    virtual int GetHeight() const = 0;

    virtual float GetAspectRatio() const
    {
        return (float) GetWidth() / GetHeight();
    }

    virtual bool IsCloseRequested() const = 0;

    virtual void SetTitle(const std::string& title) = 0;
    virtual void SetSize(int width, int height) = 0;

    virtual void Center() = 0;

    virtual void PollEvents() = 0 ;
    virtual void SwapBuffers() = 0;
};

}
