#include "Oasis/Graphics/IndexBuffer.h"

#include <string.h>

namespace Oasis
{

IndexBuffer::IndexBuffer(int startElements, BufferUsage usage)
    : HardwareBuffer(usage)
    , data()
    , dirty(true)
{
    data.resize(startElements);
}

IndexBuffer::~IndexBuffer() {}

void IndexBuffer::Upload()
{
    if (dirty) UploadGpuData(data.size() * sizeof (short), &data[0]);

    dirty = false;
}

void IndexBuffer::GetData(int start, int numElements, short* out) const
{
    int s = start;
    int e = numElements * sizeof (short);

    memcpy(out, &data[s], e);
}

void IndexBuffer::SetElementCount(int numElements)
{
    if (data.size() != (unsigned) numElements) dirty = true;

    data.resize(numElements);
}

void IndexBuffer::SetData(int start, int numElements, const short* in)
{
    dirty = true;

    for (int i = 0; i < numElements; i++)
    {
        data[start + i] = in ? in[i] : 0;
    }
}

}
