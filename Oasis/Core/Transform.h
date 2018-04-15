#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/MathUtil.h"

namespace Oasis
{

class OASIS_API Transform
{
public:
    Transform();
    ~Transform();

    const Vector3& GetPosition() const { return m_position; }
    Vector3& GetPosition() { return m_position; }

    const Quaternion& GetRotation() const { return m_rotation; }
    Quaternion& GetRotation() { return m_rotation; }

    const Vector3& GetScale() const { return m_scale; }
    Vector3& GetScale() { return m_scale; }

    Matrix4 GetModelMatrix() const
    {
        Matrix4 m = Matrix4::Translation(m_position);
        m *= Matrix4::FromQuaternion(m_rotation);
        m *= Matrix4::Scale(m_scale);
        return m;
    }

private:
    Vector3 m_position;
    Quaternion m_rotation;
    Vector3 m_scale;
};

}
