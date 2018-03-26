#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

class IndexBuffer;
class VertexBuffer;

class OASIS_API Geometry
{
public:
    virtual ~Geometry() {}

    virtual void Release() = 0;

    virtual void SetIndexBuffer(IndexBuffer* ib) = 0;

    virtual void SetVertexBuffers(int count, VertexBuffer** vbs) = 0;

    virtual void SetVertexBuffer(VertexBuffer* vb) = 0;

    virtual bool HasIndexBuffer() const = 0;

    virtual IndexBuffer* GetIndexBuffer() const = 0;

    virtual int GetVertexBufferCount() const = 0;

    virtual VertexBuffer* GetVertexBuffer(int index) const = 0;
};

}
