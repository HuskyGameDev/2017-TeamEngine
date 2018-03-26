#pragma once

#include "Oasis/Oasis.h"

#include <string>

namespace Oasis
{

class OASIS_API Shader
{
public:
    virtual ~Shader() {}

    virtual void Release() = 0;

    virtual bool IsValid() const = 0;

    virtual const std::string& GetErrorMessage() const = 0;

    virtual const std::string& GetVertexSource() const = 0;
    virtual const std::string& GetFragmentSource() const = 0;
};

}
