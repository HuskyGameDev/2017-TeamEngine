#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Core/ResourceLoader.h"

#include <string>

namespace Oasis
{

class OASIS_API ResourceManager
{
public:
    ResourceManager();
    ~ResourceManager();

    void RegisterLoader(ResourceLoader* loader, const std::string& filetype);
    void DeregisterLoader(const std::string& filetype);
};

}

