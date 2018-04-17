#include "Oasis/Graphics/OpenGL/OglIndexBuffer.h"

#include <string.h>

using namespace std;

namespace Oasis
{

OglIndexBuffer::OglIndexBuffer(int elements, BufferUsage usage)
    : IndexBuffer(elements, usage)
    , m_id(0)
{
    glGenBuffers(1, &m_id);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements * sizeof (short), NULL, GL_DYNAMIC_DRAW);
}

OglIndexBuffer::~OglIndexBuffer() { ReleaseGpuData(); }

void OglIndexBuffer::UploadGpuData(int bytes, const void* data)
{
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, bytes, data, GL_DYNAMIC_DRAW);
}

void OglIndexBuffer::UploadGpuSubData(int offset, int bytes, const void* data)
{
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, bytes, data);
}

void OglIndexBuffer::ReleaseGpuData()
{
    if (m_id)
    {
        glDeleteBuffers(1, &m_id);
        m_id = 0;
    }
}

}

