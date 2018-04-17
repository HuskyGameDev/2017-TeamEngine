#include "Oasis/Graphics/OpenGL/OglVertexBuffer.h"

#include <string.h>

#include <iostream>

using namespace std;

namespace Oasis
{

OglVertexBuffer::OglVertexBuffer(int elements, const VertexFormat& format, BufferUsage usage)
    : VertexBuffer(elements, format, usage)
    , m_id(0)
{
    glGenBuffers(1, &m_id);
    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, elements * format.GetSize() * sizeof (float), NULL, GL_DYNAMIC_DRAW);
}

OglVertexBuffer::~OglVertexBuffer() { ReleaseGpuData(); }

void OglVertexBuffer::ReleaseGpuData()
{
    if (m_id)
    {
        glDeleteBuffers(1, &m_id);
        m_id = 0;
    }
}

void OglVertexBuffer::UploadGpuData(int bytes, const void* data)
{
    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, bytes, data, GL_DYNAMIC_DRAW);
}

void OglVertexBuffer::UploadGpuSubData(int offset, int bytes, const void* data)
{
    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferSubData(GL_ARRAY_BUFFER, offset, bytes, data);
}

}
