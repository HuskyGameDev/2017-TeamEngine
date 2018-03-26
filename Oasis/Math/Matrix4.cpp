#include "Oasis/Math/Matrix4.h"

#include "Oasis/Math/Quaternion.h"

namespace Oasis
{

Matrix4 Matrix4::FromQuaternion(const Quaternion& r)
{
    Matrix4 out;
    out.m00 = 1 - 2 * r.y * r.y - 2 * r.z * r.z;
    out.m01 = 2 * r.x * r.y - 2 * r.z * r.w;
    out.m02 = 2 * r.x * r.z + 2 * r.y * r.w;
    out.m10 = 2 * r.x * r.y + 2 * r.z * r.w;
    out.m11 = 1 - 2 * r.x * r.x - 2 * r.z * r.z;
    out.m12 = 2 * r.y * r.z - 2 * r.x * r.w;
    out.m20 = 2 * r.x * r.z - 2 * r.y * r.w;
    out.m21 = 2 * r.y * r.z + 2 * r.x * r.w;
    out.m22 = 1 - 2 * r.x * r.x - 2 * r.y * r.y;
    out.m33 = 1;
    return out;
}

}
