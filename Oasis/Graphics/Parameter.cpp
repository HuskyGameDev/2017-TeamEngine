#include "Oasis/Graphics/Parameter.h"

#include <string.h>

namespace Oasis
{

Parameter::Parameter(const Parameter& param)
{
    *this = param;
}

Parameter::Parameter()
{
    *this = 0;
}

Parameter::Parameter(int value)
{
    *this = value;
}

Parameter::Parameter(float value)
{
    *this = value;
}

Parameter::Parameter(double value)
{
    *this = value;
}

Parameter::Parameter(const Vector2& value)
{
    *this = value;
}

Parameter::Parameter(const Vector3& value)
{
    *this = value;
}

Parameter::Parameter(const Vector4& value)
{
    *this = value;
}

Parameter::Parameter(const Matrix3& value)
{
    *this = value;
}

Parameter::Parameter(const Matrix4& value)
{
    *this = value;
}

Parameter& Parameter::operator=(const Parameter& param)
{
    m_type = param.m_type;

    switch (m_type)
    {
    case PARAMETER_INT:
        m_value.intValue = param.m_value.intValue;
        break;
    case PARAMETER_FLOAT:
        m_value.floatValue = param.m_value.floatValue;
        break;
    case PARAMETER_VECTOR2:
        m_value.vector2Value = param.m_value.vector2Value;
        break;
    case PARAMETER_VECTOR3:
        m_value.vector3Value = param.m_value.vector3Value;
        break;
    case PARAMETER_VECTOR4:
        m_value.vector4Value = param.m_value.vector4Value;
        break;
    case PARAMETER_MATRIX3:
        m_value.matrix3Value = param.m_value.matrix3Value;
        break;
    case PARAMETER_MATRIX4:
        m_value.matrix3Value = param.m_value.matrix3Value;
        break;
    default: // should not happen, instead update this if new types are added
        memcpy(&m_value, &param.m_value, sizeof (ParameterValue));
        break;
    }

    return *this;
}

Parameter& Parameter::operator=(int value)
{
    m_type = PARAMETER_INT;
    m_value.intValue = value;
    return *this;
}

Parameter& Parameter::operator=(float value)
{
    m_type = PARAMETER_FLOAT;
    m_value.floatValue = value;
    return *this;
}

Parameter& Parameter::operator=(double value)
{
    m_type = PARAMETER_FLOAT;
    m_value.floatValue = value;
    return *this;
}

Parameter& Parameter::operator=(const Vector2& value)
{
    m_type = PARAMETER_VECTOR2;
    m_value.vector2Value = value;
    return *this;
}

Parameter& Parameter::operator=(const Vector3& value)
{
    m_type = PARAMETER_VECTOR3;
    m_value.vector3Value = value;
    return *this;
}

Parameter& Parameter::operator=(const Vector4& value)
{
    m_type = PARAMETER_VECTOR4;
    m_value.vector4Value = value;
    return *this;
}

Parameter& Parameter::operator=(const Matrix3& value)
{
    m_type = PARAMETER_MATRIX3;
    m_value.matrix3Value = value;
    return *this;
}

Parameter& Parameter::operator=(const Matrix4& value)
{
    m_type = PARAMETER_MATRIX4;
    m_value.matrix4Value = value;
    return *this;
}

bool Parameter::operator==(const Parameter& param) const
{
    switch (param.m_type)
    {
    case PARAMETER_INT: return *this == param.m_value.intValue;
    case PARAMETER_FLOAT: return *this == param.m_value.floatValue;
    case PARAMETER_VECTOR2: return *this == param.m_value.vector2Value;
    case PARAMETER_VECTOR3: return *this == param.m_value.vector3Value;
    case PARAMETER_VECTOR4: return *this == param.m_value.vector4Value;
    case PARAMETER_MATRIX3: return *this == param.m_value.matrix3Value;
    case PARAMETER_MATRIX4: return *this == param.m_value.matrix4Value;\
    default: return memcmp(&m_value, &param.m_value, sizeof (ParameterValue));
    }
}

bool Parameter::operator==(int value) const
{
    return m_type == PARAMETER_INT && m_value.intValue == value;
}

bool Parameter::operator==(float value) const
{
    return m_type == PARAMETER_FLOAT && m_value.floatValue == value;
}

bool Parameter::operator==(double value) const
{
    return m_type == PARAMETER_FLOAT && m_value.floatValue == (float)value;
}

bool Parameter::operator==(const Vector2& value) const
{
    return m_type == PARAMETER_VECTOR2 && m_value.vector2Value == value;
}

bool Parameter::operator==(const Vector3& value) const
{
    return m_type == PARAMETER_VECTOR3 && m_value.vector3Value == value;
}

bool Parameter::operator==(const Vector4& value) const
{
    return m_type == PARAMETER_VECTOR4 && m_value.vector4Value == value;
}

bool Parameter::operator==(const Matrix3& value) const
{
    return m_type == PARAMETER_MATRIX3 && m_value.matrix3Value == value;
}

bool Parameter::operator==(const Matrix4& value) const
{
    return m_type == PARAMETER_MATRIX4 && m_value.matrix4Value == value;
}

int Parameter::GetInt() const
{
    switch (m_type)
    {
    case PARAMETER_INT:
        return m_value.intValue;
    case PARAMETER_FLOAT:
        return (int) m_value.floatValue;
    default:
        return 0;
    }
}

float Parameter::GetFloat() const
{
    switch (m_type)
    {
    case PARAMETER_INT:
        return (float) m_value.intValue;
    case PARAMETER_FLOAT:
        return m_value.floatValue;
    default:
        return 0.0;
    }
}

const Vector2& Parameter::GetVector2() const
{
    return m_type == PARAMETER_VECTOR2 ? m_value.vector2Value : Vector2::ZERO;
}

const Vector3& Parameter::GetVector3() const
{
    return m_type == PARAMETER_VECTOR3 ? m_value.vector3Value : Vector3::ZERO;
}

const Vector4& Parameter::GetVector4() const
{
    return m_type == PARAMETER_VECTOR4 ? m_value.vector4Value : Vector4::ZERO;
}

const Matrix3& Parameter::GetMatrix3() const
{
    return m_type == PARAMETER_MATRIX3 ? m_value.matrix3Value : Matrix3::IDENTITY;
}

const Matrix4& Parameter::GetMatrix4() const
{
    return m_type == PARAMETER_MATRIX4 ? m_value.matrix4Value : Matrix4::IDENTITY;
}

}
