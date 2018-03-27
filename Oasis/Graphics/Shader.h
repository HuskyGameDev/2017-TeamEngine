#pragma once

#include "Oasis/Oasis.h"

#include <string>

namespace Oasis
{

enum Uniform
{
    UNIFORM_NONE,
    UNIFORM_UNKNOWN,
    UNIFORM_INT,
    UNIFORM_FLOAT,
    UNIFORM_VECTOR2,
    UNIFORM_VECTOR3,
    UNIFORM_VECTOR4,
    UNIFORM_MATRIX3,
    UNIFORM_MATRIX4,

    UNIFORM_COUNT
};

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
