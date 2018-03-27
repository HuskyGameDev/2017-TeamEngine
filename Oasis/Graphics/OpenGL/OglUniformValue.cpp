#include "Oasis/Graphics/OpenGL/OglShader.h"

namespace Oasis
{

bool UniformValue::SetValue(int value)
{
    switch (m_type)
    {
    case UNIFORM_INT:
        m_intValue = value;
        break;
    case UNIFORM_FLOAT:
        m_floatValue = value;
        break;
    case UNIFORM_VECTOR2:
    case UNIFORM_VECTOR3:
    case UNIFORM_VECTOR4:
        m_vector4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(float value)
{
    switch (m_type)
    {
    case UNIFORM_INT:
        m_intValue = value;
        break;
    case UNIFORM_FLOAT:
        m_floatValue = value;
        break;
    case UNIFORM_VECTOR2:
    case UNIFORM_VECTOR3:
    case UNIFORM_VECTOR4:
        m_vector4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(const Vector2& value)
{
    switch (m_type)
    {
    case UNIFORM_VECTOR2:
    case UNIFORM_VECTOR3:
    case UNIFORM_VECTOR4:
        m_vector4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(const Vector3& value)
{
    switch (m_type)
    {
    case UNIFORM_VECTOR2:
    case UNIFORM_VECTOR3:
    case UNIFORM_VECTOR4:
        m_vector4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(const Vector4& value)
{
    switch (m_type)
    {
    case UNIFORM_VECTOR2:
    case UNIFORM_VECTOR3:
    case UNIFORM_VECTOR4:
        m_vector4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(const Matrix3& value)
{
    switch (m_type)
    {
    case UNIFORM_MATRIX3:
        m_matrix3Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

bool UniformValue::SetValue(const Matrix4& value)
{
    switch (m_type)
    {
    case UNIFORM_MATRIX4:
        m_matrix4Value = value;
        break;
    default:
        m_matrix4Value = 0;
        return false;
    }

    return true;
}

}
