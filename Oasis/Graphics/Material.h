#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/MathUtil.h"

#include <string>
#include <unordered_map>

namespace Oasis
{

enum ParameterType
{
    PARAMETER_INT,
    PARAMETER_FLOAT,
    PARAMETER_VECTOR2,
    PARAMETER_VECTOR3,
    PARAMETER_VECTOR4,
    PARAMETER_MATRIX3,
    PARAMETER_MATRIX4,

    PARAMETER_COUNT
};

struct OASIS_API Parameter
{
    ParameterType type;
    union
    {
        int intValue;
        float floatValue;
        Vector2 vector2Type;
        Vector3 vector3Type;
        Vector4 vector4Type;
        Matrix3 matrix3Type;
        Matrix4 matrix4Type;
    };
};

class OASIS_API Material
{
public:
    Material();
    ~Material();

    void SetParameter(const std::string& name, int value);
    void SetParameter(const std::string& name, float value);
    void SetParameter(const std::string& name, const Vector2& value);
    void SetParameter(const std::string& name, const Vector3& value);
    void SetParameter(const std::string& name, const Vector4& value);
    void SetParameter(const std::string& name, const Matrix3& value);
    void SetParameter(const std::string& name, const Matrix4& value);

    const Parameter* GetParameter(const std::string& name) const;

    int GetParameterCount() const;
    void GetParameters(int start, int count, const Parameter* params) const;

private:
    std::unordered_map<std::string, Parameter> m_parameters;
};

}
