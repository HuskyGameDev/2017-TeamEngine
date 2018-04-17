#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/HardwareBuffer.h"

#include <vector>

namespace Oasis
{

class OASIS_API IndexBuffer : public HardwareBuffer
{
public:
    IndexBuffer(int startElements, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    virtual ~IndexBuffer();

    void Upload();

    int GetElementCount() const { return data.size(); }

    void GetData(int start, int numElements, short* out) const;

    void SetElementCount(int numElements);

    void SetData(int start, int numElements, const short* in);

protected:
    std::vector<short> data;
    bool dirty;
};

}
