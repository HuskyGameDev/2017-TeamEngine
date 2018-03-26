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

    virtual void GetData(int start, int numElements, int* out) const = 0;

    virtual void SetUsage(BufferUsage usage) = 0;

    virtual void SetData(int numElements, const int* in) = 0;
};

}
