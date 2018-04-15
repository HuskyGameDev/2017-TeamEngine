#pragma once

#include "Oasis/Graphics/Texture.h"

namespace Oasis
{

class OASIS_API Texture2D : public Texture
{
public:
    virtual Texture2D() {}

    virtual void GetPixels(int x, int y, int w, int h, void* out) const = 0;

    virtual void SetPixels(int x, int y, int w, int h, const void* in) = 0;
};

}
