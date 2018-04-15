#pragma once

#ifdef _WIN32
    #ifdef OASIS_EXPORT
        #define OASIS_API __declspec(dllexport)
    #else
        #define OASIS_API __declspec(dllimport)
    #endif
#else
    #define OASIS_API
#endif

#include "Oasis/Core/Engine.h"

#include <cstdlib>

#define OASIS_MAIN(AppName) \
    int main(int argc, char** argv) { \
        AppName oasisApp; \
        return oasisApp.Start(); \
    }
