#pragma once

#include "Oasis/Oasis.h"

#include "MathUtil.h"

namespace Oasis
{

class OASIS_API Transform
{
public:
    Transform() : m_position(0), m_rotation(Quaternion::AxisAngle({0, 1, }, 0)), m_scale(1) {}
    ~Transform() {}

    const Vector3& GetPosition() const { return m_position; }
    Vector3& GetPosition() { return m_position; }

    const Quaternion& GetRotation() const { return m_rotation; }
    Quaternion& GetRotation() { return m_rotation; }

    const Vector3& GetScale() const { return m_scale; }
    Vector3& GetScale() { return m_scale; }

    Matrix4 GetMatrix() const { return Matrix4::Translation(m_position) * Matrix4::FromQuaternion(m_rotation) * Matrix4::Scale(m_scale); }

private:
    Vector3 m_position;
    Quaternion m_rotation;
    Vector3 m_scale;
};

}
