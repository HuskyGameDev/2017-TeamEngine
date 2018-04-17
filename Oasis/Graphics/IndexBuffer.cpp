#include "Oasis/Graphics/IndexBuffer.h"

#include <string.h>

namespace Oasis
{

IndexBuffer::IndexBuffer(int startElements, BufferUsage usage)
    : HardwareBuffer(usage)
    , data()
{
    data.resize(startElements);
}

IndexBuffer::~IndexBuffer() {}

void IndexBuffer::GetData(int start, int numElements, short* out) const
{
    int s = start;
    int e = numElements * sizeof (short);

    memcpy(out, &data[s], e);
}

void IndexBuffer::SetElementCount(int numElements)
{
    data.resize(numElements);

    UploadGpuData(data.size() * sizeof (short), &data[0]);
}

void IndexBuffer::SetData(int start, int numElements, const short* in)
{
    for (int i = 0; i < numElements; i++)
    {
        data[start + i] = in ? in[i] : 0;
    }

    UploadGpuSubData(start * sizeof (short), numElements * sizeof (short), in ? in : NULL);
}

}
