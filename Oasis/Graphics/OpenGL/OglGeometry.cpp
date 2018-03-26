#include "Oasis/Graphics/OpenGL/OglGeometry.h"

namespace Oasis
{

OglGeometry::OglGeometry()
    : m_indexBuffer(NULL)
    , m_vertexBuffers() {}

OglGeometry::~OglGeometry() {}

void OglGeometry::Release() {}

void OglGeometry::SetIndexBuffer(IndexBuffer* ib)
{
    m_indexBuffer = ib;
}

void OglGeometry::SetVertexBuffers(int count, VertexBuffer** vbs)
{
    m_vertexBuffers.clear();

    for (int i = 0; i < count; i++) m_vertexBuffers.push_back(vbs[i]);
}

void OglGeometry::SetVertexBuffer(VertexBuffer* vb)
{
    if (vb)
    {
        SetVertexBuffers(1, &vb);
    }
    else
    {
        SetVertexBuffers(0, NULL);
    }
}

bool OglGeometry::HasIndexBuffer() const
{
    return m_indexBuffer != NULL;
}

IndexBuffer* OglGeometry::GetIndexBuffer() const
{
    return m_indexBuffer;
}

int OglGeometry::GetVertexBufferCount() const
{
    return m_vertexBuffers.size();
}

VertexBuffer* OglGeometry::GetVertexBuffer(int index) const
{
    return m_vertexBuffers[index];
}


}
