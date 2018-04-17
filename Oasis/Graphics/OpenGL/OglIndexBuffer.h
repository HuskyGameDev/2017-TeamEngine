#pragma once

#include "Oasis/Graphics/IndexBuffer.h"

#include <GL/glew.h>

#include <vector>

namespace Oasis
{

class OASIS_API OglIndexBuffer : public IndexBuffer
{
public:
    OglIndexBuffer(int elements, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    ~OglIndexBuffer();

    GLuint GetId() const { return m_id; }

private:
    void UploadGpuData(int bytes, const void* data);
    void UploadGpuSubData(int offset, int bytes, const void* data);
    void ReleaseGpuData();

    GLuint m_id;
};

}

