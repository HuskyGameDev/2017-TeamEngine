#include "Oasis/Math/Quaternion.h"

#include "Oasis/Math/Matrix4.h"

namespace Oasis
{

Quaternion Quaternion::FromMatrix4(const Matrix4& m)
{
    Quaternion q;

    float t = 1 + m[0] + m[5] + m[10];

    if (t > 0.001) {
        float s = 1.0 / (2 * std::sqrt(t));
        q.x = (m[9] - m[6]) * s;
        q.y = (m[2] - m[8]) * s;
        q.z = (m[4] - m[1]) * s;
        q.w = 0.25 / s;
        return q;
    }

    if (m[0] > m[5] && m[0] > m[10]) {
        float s = 1.0 / (2 * std::sqrt(1 + m[0] - m[5] - m[10]));
        q.x = 0.25 / s;
        q.y = (m[4] + m[1]) * s;
        q.z = (m[2] + m[8]) * s;
        q.w = (m[9] - m[6]) * s;
        return q;
    }
    else if (m[5] > m[10]) {
        float s = 1.0 / (2 * std::sqrt(1 + m[5] - m[0] - m[10]));
        q.x = (m[4] + m[1]) * s;
        q.y = 0.25 / s;
        q.z = (m[9] + m[6]) * s;
        q.w = (m[2] - m[8]) * s;
        return q;
    }
    else {
        float s = 1.0 / (2 * std::sqrt(1 + m[10] - m[0] - m[5]));
        q.x = (m[2] + m[8]) * s;
        q.y = (m[9] + m[6]) * s;
        q.z = 0.25 / s;
        q.w = (m[4] - m[1]) * s;
        return q;
    }
}

Quaternion Quaternion::Direction(const Vector3& dir, const Vector3& up)
{
    return FromMatrix4(Matrix4::LookAt(0, dir, up));
}

}

