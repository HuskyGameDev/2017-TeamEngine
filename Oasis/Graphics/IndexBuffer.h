#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/HardwareBuffer.h"

namespace Oasis
{

class OASIS_API IndexBuffer
{
public:
    virtual ~IndexBuffer() {}

    virtual void Release() = 0;

    virtual BufferUsage GetUsage() const = 0;

    virtual int GetElementCount() const = 0;

    virtual void GetData(int start, int numElements, short* out) const = 0;

    virtual void SetUsage(BufferUsage usage) = 0;

    virtual void SetElementCount(int numElements) = 0;

    virtual void SetData(int start, int numElements, const short* in) = 0;
};

}
