#pragma once

#include "Oasis/Oasis.h"

#ifdef _WIN32
#include "Oasis/OasisWindows.h"
#endif // WIN32

namespace Oasis
{

class OASIS_API Timer
{
public:
    Timer();
    ~Timer();

    void Stop();
    void Start();
    void Reset();

    double GetSeconds();

private:
    #ifdef _WIN32
    LARGE_INTEGER m_freq;
    LARGE_INTEGER m_start;
    LARGE_INTEGER m_stop;
    bool m_running;
    #endif // _WIN32
};

}
