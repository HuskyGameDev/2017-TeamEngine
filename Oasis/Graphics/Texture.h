#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

enum TextureFormat
{
    TEXTURE_FORMAT_RGBA8,
    TEXTURE_FORMAT_RGBA16F,
    TEXTURE_FORMAT_RGBA32F,
    TEXTURE_FORMAT_DEPTH16,
    TEXTURE_FORMAT_DEPTH24,
    TEXTURE_FORMAT_DEPTH24STENCIL8,
    TEXTURE_FORMAT_DEPTH32,

    TEXTURE_FORMAT_COUNT
};

enum TextureFilter
{
    FILTER_NEAREST,
    FILTER_BILINEAR,
    FILTER_TRILINEAR,

    MIN_FILTER_COUNT
};
enum Axis
{
    AXIS_X,
    AXIS_Y,
    AXIS_Z,

    AXIS_COUNT
};

enum WrapMode
{
    WRAP_MODE_CLAMP,
    WRAP_MODE_REPEAT,

    WRAP_MODE_COUNT
} ;

class OASIS_API Texture
{
public:
    virtual Texture() {}

    virtual void Release() = 0;

    virtual int GetWidth() const = 0;
    virtual int GetHeight() const = 0;

    virtual TextureFilter GetFilter() const = 0;
    virtual WrapMode GetWrapMode(Axis axis) const = 0;
    virtual int GetMipmapLevels() const = 0;

    virtual void SetSize(int width, int height) = 0;

    virtual void SetFilter(TextureFilter filter) = 0;
    virtual void SetWrapMode(WrapMode mode) = 0;
    virtual void SetWrapMode(Axis axis, WrapMode mode) = 0;
    virtual void SetMipmapLevels(int levels) = 0;
};

}
