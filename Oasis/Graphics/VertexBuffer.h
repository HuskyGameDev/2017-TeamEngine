#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/HardwareBuffer.h"
#include "Oasis/Graphics/VertexFormat.h"

namespace Oasis
{

class OASIS_API VertexBuffer
{
public:
    virtual ~VertexBuffer() {}

    virtual void Release() = 0;

    virtual BufferUsage GetUsage() const = 0;

    virtual const VertexFormat& GetFormat() const = 0;

    virtual int GetElementCount() const = 0;

    virtual void GetData(int start, int numElements, float* out) const = 0;

    virtual void SetUsage(BufferUsage usage) = 0;

    virtual void SetElementCount(int numElements) = 0;

    virtual void SetData(int start, int numElements, const float* in) = 0;
};

}
