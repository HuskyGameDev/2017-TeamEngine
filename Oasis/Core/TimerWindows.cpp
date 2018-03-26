#ifdef _WIN32

#include "Oasis/Core/Timer.h"

namespace Oasis
{

Timer::Timer()
    : m_running(true)
{
    QueryPerformanceFrequency(&m_freq);
    Reset();
}

Timer::~Timer() {}

void Timer::Stop()
{
    if (m_running)
    {
        QueryPerformanceCounter(&m_stop);
        m_running = false;
    }
}

void Timer::Start()
{
    m_running = true;
}

void Timer::Reset()
{
    QueryPerformanceCounter(&m_start);
    m_stop = m_start;
}

double Timer::GetSeconds()
{
    if (m_running)
    {
        QueryPerformanceCounter(&m_stop);
    }

    return static_cast<double>(m_stop.QuadPart - m_start.QuadPart) / m_freq.QuadPart;
}

}

#endif // _WIN32
