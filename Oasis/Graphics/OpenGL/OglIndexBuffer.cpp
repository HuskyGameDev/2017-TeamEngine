#include "Oasis/Graphics/OpenGL/OglIndexBuffer.h"

#include <string.h>

using namespace std;

namespace Oasis
{

void OglIndexBuffer::Validate()
{

}

OglIndexBuffer::OglIndexBuffer(int elements, BufferUsage usage)
    : m_usage(usage)
    , m_data()
{
    m_data.resize(elements);

    glGenBuffers(1, &m_id);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements * sizeof (short), NULL, GL_DYNAMIC_DRAW);
}

OglIndexBuffer::~OglIndexBuffer()
{
    Release();
}

void OglIndexBuffer::Release()
{
    if (m_id)
    {
        glDeleteBuffers(1, &m_id);
        m_id = 0;
    }
}

BufferUsage OglIndexBuffer::GetUsage() const
{
    return m_usage;
}

int OglIndexBuffer::GetElementCount() const
{
    return m_data.size();
}

void OglIndexBuffer::GetData(int start, int numElements, short* out) const
{
    int s = start;
    int e = numElements * sizeof (short);

    memcpy(out, &m_data[s], e);
}

void OglIndexBuffer::SetUsage(BufferUsage usage)
{
    m_usage = usage;
}

void OglIndexBuffer::SetElementCount(int numElements)
{
    m_data.resize(numElements);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, m_data.size() * sizeof (short), &m_data[0], GL_DYNAMIC_DRAW);
}

void OglIndexBuffer::SetData(int start, int numElements, const short* in)
{
    for (int i = 0; i < numElements; i++)
    {
        m_data[start + i] = in[i];
    }

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_id);
    // TODO doing something wrong here, glBufferData works though so look at it later
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, m_data.size() * sizeof (short), &m_data[0], GL_DYNAMIC_DRAW);
}

GLuint OglIndexBuffer::GetId() const
{
    return m_id;
}

}

