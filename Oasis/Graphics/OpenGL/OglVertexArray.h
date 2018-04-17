#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/VertexArray.h"
#include "Oasis/Graphics/IndexBuffer.h"
#include "Oasis/Graphics/VertexBuffer.h"

#include <vector>

namespace Oasis
{

class OASIS_API OglVertexArray : public VertexArray
{
public:
    OglVertexArray();
    ~OglVertexArray();

private:
    void SetGpuBuffers(int count, VertexBuffer** vbs, IndexBuffer* ib);
    void ReleaseGpuData() {}

    IndexBuffer* m_indexBuffer;
    std::vector<VertexBuffer*> m_vertexBuffers;
};

}
