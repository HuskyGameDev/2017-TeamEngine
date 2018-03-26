#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/Geometry.h"
#include "Oasis/Graphics/IndexBuffer.h"
#include "Oasis/Graphics/VertexBuffer.h"

#include <vector>

namespace Oasis
{

class OASIS_API OglGeometry : public Geometry
{
public:
    OglGeometry();
    ~OglGeometry();

    void Release();

    void SetIndexBuffer(IndexBuffer* ib);

    void SetVertexBuffers(int count, VertexBuffer** vbs);

    void SetVertexBuffer(VertexBuffer* vb);

    bool HasIndexBuffer() const;

    IndexBuffer* GetIndexBuffer() const;

    int GetVertexBufferCount() const;

    VertexBuffer* GetVertexBuffer(int index) const;

private:
    IndexBuffer* m_indexBuffer;
    std::vector<VertexBuffer*> m_vertexBuffers;
};

}
