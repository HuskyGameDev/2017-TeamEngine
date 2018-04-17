#pragma once

#include "Oasis/Oasis.h"

namespace Oasis
{

enum BufferUsage
{
    BUFFER_USAGE_STATIC,
    BUFFER_USAGE_DYNAMIC,
    BUFFER_USAGE_STREAM,

    BUFFER_USAGE_COUNT
};

class HardwareBuffer
{
public:
    HardwareBuffer(BufferUsage usage = BUFFER_USAGE_DYNAMIC) : usage(usage) {}
    virtual ~HardwareBuffer() {}

    void Release() { ReleaseGpuData(); }

    virtual void Upload() = 0;

    BufferUsage GetUsage() const { return usage; }
    void SetUsage(BufferUsage usage) { usage = usage; }

protected:
    virtual void UploadGpuData(int bytes, const void* data) = 0;
    virtual void UploadGpuSubData(int offset, int bytes, const void* data) = 0;
    virtual void ReleaseGpuData() = 0;

    BufferUsage usage;
};

}
