#pragma once

#include "Oasis/Oasis.h"

#include <vector>

namespace Oasis
{

class IndexBuffer;
class VertexBuffer;

class OASIS_API Geometry
{
public:
    virtual ~Geometry() {}

    virtual void Release();

    virtual void SetIndexBuffer(IndexBuffer* ib);

    virtual void SetVertexBuffers(int count, VertexBuffer** vbs);

    virtual void SetVertexBuffer(VertexBuffer* vb) { SetVertexBuffers(1, &vb); }

    virtual bool HasIndexBuffer() const { return indexBuffer != NULL; }

    virtual IndexBuffer* GetIndexBuffer() const { return indexBuffer; }

    virtual int GetVertexBufferCount() const { return vertexBuffers.size(); }

    virtual VertexBuffer* GetVertexBuffer(int index) const { return vertexBuffers[index]; }

protected:


    std::vector<VertexBuffer*> vertexBuffers;
    IndexBuffer* indexBuffer;
};

}
