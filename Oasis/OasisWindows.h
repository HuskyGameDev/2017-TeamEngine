#pragma once

#ifdef _WIN32

// for GetTickCount64
#undef _WIN32_WINNT
#define _WIN32_WINNT 0x0600

#include "windows.h"

#undef near
#undef far

#endif // _WIN32
