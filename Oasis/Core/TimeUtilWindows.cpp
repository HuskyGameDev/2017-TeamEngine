#ifdef _WIN32

#include "Oasis/Core/TimeUtil.h"

#include "Oasis/OasisWindows.h"

namespace Oasis
{

namespace Time
{

uint64_t Millis()
{
    return GetTickCount64();
}

void Sleep(uint64_t millis)
{
    ::Sleep(millis);
}

}

}

#endif // _WIN32
