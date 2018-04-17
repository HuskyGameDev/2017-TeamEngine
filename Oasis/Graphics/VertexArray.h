#pragma once

#include "Oasis/Oasis.h"

#include <vector>

namespace Oasis
{

class IndexBuffer;
class VertexBuffer;

class OASIS_API VertexArray
{
public:
    VertexArray() : vertexBuffers(), indexBuffer(NULL) {}
    virtual ~VertexArray() {}

    void Release() { ReleaseGpuData(); }

    void Upload() { SetGpuBuffers(vertexBuffers.size(), &vertexBuffers[0], indexBuffer); }

    void SetIndexBuffer(IndexBuffer* ib) { indexBuffer = ib; }

    void SetVertexBuffers(int count, VertexBuffer** vbs)
    {
        vertexBuffers.clear();
        for (int i = 0; i < count; i++) vertexBuffers.push_back(vbs[i]);
    }

    void SetVertexBuffer(VertexBuffer* vb) { SetVertexBuffers(1, &vb); }

    bool HasIndexBuffer() const { return indexBuffer != NULL; }

    IndexBuffer* GetIndexBuffer() const { return indexBuffer; }

    int GetVertexBufferCount() const { return vertexBuffers.size(); }

    VertexBuffer* GetVertexBuffer(int index) const { return vertexBuffers[index]; }

protected:
    virtual void SetGpuBuffers(int count, VertexBuffer** vbs, IndexBuffer* ib) = 0;
    virtual void ReleaseGpuData() = 0;

    std::vector<VertexBuffer*> vertexBuffers;
    IndexBuffer* indexBuffer;
};

}
