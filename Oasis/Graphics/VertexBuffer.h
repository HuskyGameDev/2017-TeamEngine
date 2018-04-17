#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/HardwareBuffer.h"
#include "Oasis/Graphics/VertexFormat.h"

#include <vector>

namespace Oasis
{

class OASIS_API VertexBuffer : public HardwareBuffer
{
public:
    VertexBuffer(int startElements, const VertexFormat& format, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    virtual ~VertexBuffer();

    void Upload();

    const VertexFormat& GetFormat() const { return format; }

    int GetElementCount() const { return data.size() / format.GetSize(); }

    void GetData(int start, int numElements, float* out) const;

    void SetElementCount(int numElements);

    void SetData(int start, int numElements, const float* in);

protected:
    VertexFormat format;
    std::vector<float> data;
    bool dirty;
};

}
