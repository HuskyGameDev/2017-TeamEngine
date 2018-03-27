#pragma once

#include "Oasis/Graphics/IndexBuffer.h"

#include <GL/glew.h>

#include <vector>

namespace Oasis
{

class OASIS_API OglIndexBuffer : public IndexBuffer
{
public:
    OglIndexBuffer(int elements, BufferUsage usage);
    ~OglIndexBuffer();

    void Release();

    BufferUsage GetUsage() const;
    int GetElementCount() const;
    void GetData(int start, int numElements, short* out) const;

    void SetElementCount(int numElements);
    void SetUsage(BufferUsage usage);
    void SetData(int start, int numElements, const short* in);

    GLuint GetId() const;

private:
    void Validate();

    BufferUsage m_usage;
    std::vector<short> m_data;

    GLuint m_id;
};

}

