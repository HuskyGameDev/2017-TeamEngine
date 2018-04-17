#include "Oasis/Graphics/VertexBuffer.h"

#include <string.h>

namespace Oasis
{

VertexBuffer::VertexBuffer(int startElements, const VertexFormat& format, BufferUsage usage)
    : HardwareBuffer(usage)
    , format(format)
    , data()
{
    data.resize(startElements * format.GetSize());
}

VertexBuffer::~VertexBuffer() {}

void VertexBuffer::GetData(int start, int numElements, float* out) const
{
    int s = start * format.GetSize();
    int e = numElements * format.GetSize() * sizeof (float);

    memcpy(out, &data[s], e);
}

void VertexBuffer::SetData(int start, int numElements, const float* in)
{
    start *= format.GetSize();

    for (int i = 0; i < numElements * format.GetSize(); i++)
    {
        data[start + i] = in ? in[i] : 0;
    }

    UploadGpuSubData(
        start * sizeof (float),
        numElements * format.GetSize() * sizeof (float),
        in ? in : NULL);
}

void VertexBuffer::SetElementCount(int numElements)
{
    data.resize(numElements * format.GetSize());
}

}
