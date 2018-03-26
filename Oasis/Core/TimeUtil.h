#pragma once

#include "Oasis/Oasis.h"

#include <stdint.h>

namespace Oasis
{

namespace Time
{

const static uint64_t SECOND = 1000;

OASIS_API uint64_t Millis();

OASIS_API void Sleep(uint64_t millis);

}

}
