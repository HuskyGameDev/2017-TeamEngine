#pragma once

#include "Oasis/Graphics/VertexBuffer.h"

#include <GL/glew.h>

#include <vector>

namespace Oasis
{

class OASIS_API OglVertexBuffer : public VertexBuffer
{
public:
    OglVertexBuffer(int elements, const VertexFormat& format, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    ~OglVertexBuffer();

    GLuint GetId() const { return m_id; };

private:
    void UploadGpuData(int bytes, const void* data);
    void UploadGpuSubData(int offset, int bytes, const void* data);
    void ReleaseGpuData();

    GLuint m_id;
};

}
