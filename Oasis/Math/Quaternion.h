#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Math/Vector2.h"
#include "Oasis/Math/Vector3.h"
#include "Oasis/Math/Vector4.h"

#include <cmath>

namespace Oasis
{

struct Matrix4;

struct OASIS_API Quaternion
{
    float x, y, z, w;

    static Quaternion AxisAngle(const Vector3& axis, float radians)
    {
        float s = std::sin(radians * 0.5);
        float c = std::cos(radians * 0.5);

        return Quaternion(s * axis.Normalized(), c);
    }

    static Quaternion FromMatrix4(const Matrix4& m);

    static Quaternion Direction(const Vector3& dir, const Vector3& up = { 0, 1, 0 });

    Quaternion() : x(0), y(0), z(0), w(0) {}

    Quaternion(float a) : x(a), y(a), z(a), w(a) {}

    Quaternion(float a, float b) : x(a), y(b), z(0), w(1) {}

    Quaternion(const Vector2& a) : x(a.x), y(a.y), z(0), w(1) {}

    Quaternion(float a, float b, float c) : x(a), y(b), z(c), w(1) {}

    Quaternion(float a, float b, float c, float d) : x(a), y(b), z(c), w(d) {}

    Quaternion(const Vector2& ab, float c) : x(ab.x), y(ab.y), z(c), w(1) {}

    Quaternion(const Vector2& ab, float c, float d) : x(ab.x), y(ab.y), z(c), w(d) {}

    Quaternion(float a, const Vector2& bc, float d) : x(a), y(bc.x), z(bc.y), w(d) {}

    Quaternion(float a, const Vector2& bc) : x(a), y(bc.x), z(bc.y), w(1) {}

    Quaternion(float a, float b, const Vector2& cd) : x(a), y(b), z(cd.x), w(cd.y) {}

    Quaternion(const Vector3& r) : x(r.x), y(r.y), z(r.z), w(1) {}

    Quaternion(const Vector3& r, float d) : x(r.x), y(r.y), z(r.z), w(d) {}

    Quaternion(float a, const Vector3& r) : x(a), y(r.x), z(r.y), w(r.z) {}

    Quaternion(const Vector4& r) : x(r.x), y(r.y), z(r.z), w(r.w) {}

    Quaternion(const Quaternion& r) : x(r.x), y(r.y), z(r.z), w(r.w) {}

    bool operator==(const Quaternion& r) const { return x == r.x && y == r.y && z == r.z && w == r.w; }
    bool operator!=(const Quaternion& r) const { return !(*this == r); }

    Quaternion& operator=(const Quaternion& r)
    {
        if (this == &r) return *this;
        x = r.x;
        y = r.y;
        z = r.z;
        w = r.w;
        return *this;
    }

    const float& operator[](int index) const { return (&x)[index]; };
    float& operator[](int index) { return (&x)[index]; };

    float LengthSq() const { return x * x + y * y + z * z + w * w; }
    float Length() const { return std::sqrt(LengthSq()); }

    float Dot(const Quaternion& r) const { return x * r.x + y * r.y + z * r.z + w * r.w; }

    Quaternion Conjugate() const { return Quaternion(-x, -y, -z, w); }

    Quaternion Inverse() const { return Conjugate() *= (1 / LengthSq()); }

    Quaternion Normalized() const
    {
        float len = Length();

        if (len != 0)
        {
            float inv = 1 / len;
            return Quaternion(x * inv, y * inv, z * inv, w * inv);
        }

        return *this;
    }

    Quaternion operator-() const { return Quaternion(-x, -y, -z, -w); }

    Quaternion& operator*=(const Quaternion& r)
    {
        Quaternion out;
        out.w = w * r.w - x * r.x - y * r.y - z * r.z;
        out.x = w * r.x + x * r.w + y * r.z - z * r.y;
        out.y = w * r.y - x * r.z + y * r.w + z * r.x;
        out.z = w * r.z + x * r.y - y * r.x + z * r.w;
        return *this = out;
    }
};

inline Quaternion operator*(Quaternion a, const Quaternion& b) { return a *= b; }

inline Vector3 operator*(Quaternion a, const Vector3& b)
{
    Quaternion res = a * Quaternion(b, 0) * a.Conjugate();
    return Vector3(res.x, res.y, res.z);
}

inline std::ostream& operator<<(std::ostream& out, const Quaternion& r)
{
    return out << "(" << r.x << ", " << r.y << ", " << r.z << ", " << r.w << ")";
}

}
