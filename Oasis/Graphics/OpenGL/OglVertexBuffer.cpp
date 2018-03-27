#include "Oasis/Graphics/OpenGL/OglVertexBuffer.h"

#include <string.h>

#include <iostream>

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
    m_data.resize(elements * format.GetSize());

    glGenBuffers(1, &m_id);
    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, elements * format.GetSize() * sizeof (float), NULL, GL_DYNAMIC_DRAW);
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

void OglVertexBuffer::SetElementCount(int numElements)
{
    m_data.resize(numElements * m_format.GetSize());

    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferData(GL_ARRAY_BUFFER, m_data.size() * sizeof (float), &m_data[0], GL_DYNAMIC_DRAW);
}

void OglVertexBuffer::SetData(int start, int numElements, const float* in)
{
    start *= m_format.GetSize();

    for (int i = 0; i < numElements * m_format.GetSize(); i++)
    {
        m_data[start + i] = in[i];
    }

    glBindBuffer(GL_ARRAY_BUFFER, m_id);
    glBufferSubData(GL_ARRAY_BUFFER, start * sizeof (float), numElements * m_format.GetSize() * sizeof (float), &m_data[start]);
}

GLuint OglVertexBuffer::GetId() const
{
    return m_id;
}

}
