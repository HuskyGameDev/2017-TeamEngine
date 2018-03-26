#include "Oasis/Graphics/OpenGL/OglVertexBuffer.h"

#include <string.h>

using namespace std;

namespace Oasis
{

void OglVertexBuffer::Validate()
{

}

OglVertexBuffer::OglVertexBuffer(int elements, const VertexFormat& format, BufferUsage usage)
    : m_format(format)
    , m_usage(usage)
    , m_data()
{
    glGenBuffers(1, &m_id);
    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, elements * format.GetSize(), NULL, GL_DYNAMIC_DRAW);
}

OglVertexBuffer::~OglVertexBuffer()
{
    Release();
}

void OglVertexBuffer::Release()
{
    if (m_id)
    {
        glDeleteBuffers(1, &m_id);
        m_id = 0;
    }
}

BufferUsage OglVertexBuffer::GetUsage() const
{
    return m_usage;
}

const VertexFormat& OglVertexBuffer::GetFormat() const
{
    return m_format;
}

int OglVertexBuffer::GetElementCount() const
{
    return m_data.size() / m_format.GetSize();
}

void OglVertexBuffer::GetData(int start, int numElements, float* out) const
{
    int s = start * m_format.GetSize();
    int e = numElements * m_format.GetSize() * sizeof (float);

    memcpy(out, &m_data[s], e);
}

void OglVertexBuffer::SetUsage(BufferUsage usage)
{
    m_usage = usage;
}

void OglVertexBuffer::SetData(int numElements, const float* in)
{
    m_data.clear();

    for (int i = 0; i < numElements * m_format.GetSize(); i++)
    {
        m_data.push_back(in[i]);
    }

    // TODO glBufferSubData

    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, m_data.size() * sizeof (float), &m_data[0], GL_DYNAMIC_DRAW);
}

GLuint OglVertexBuffer::GetId() const
{
    return m_id;
}

}
