#pragma once

#include "Oasis/Graphics/VertexBuffer.h"

#include <GL/glew.h>

#include <vector>

namespace Oasis
{

class OASIS_API OglVertexBuffer : public VertexBuffer
{
public:
    OglVertexBuffer(int elements, const VertexFormat& format, BufferUsage usage);
    ~OglVertexBuffer();

    void Release();

    BufferUsage GetUsage() const;

    const VertexFormat& GetFormat() const;

    int GetElementCount() const;
    void GetData(int start, int numElements, float* out) const;

    void SetElementCount(int numElements);
    void SetUsage(BufferUsage usage);
    void SetData(int start, int numElements, const float* in);

    GLuint GetId() const;

private:
    void Validate();

    VertexFormat m_format;
    BufferUsage m_usage;
    std::vector<float> m_data;

    GLuint m_id;
};

}
