#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/MathUtil.h"

#include "Oasis/Graphics/Parameter.h"

#include <string>
#include <unordered_map>

namespace Oasis
{

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
