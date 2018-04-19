#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Math/Vector2.h"
#include "Oasis/Math/Vector3.h"

#include <cmath>

namespace Oasis
{

struct OASIS_API Vector4
{
    static const Vector4 ZERO;

    float x, y, z, w;

    Vector4() : x(0), y(0), z(0), w(0) {}

    Vector4(float a) : x(a), y(a), z(a), w(a) {}

    Vector4(float a, float b) : x(a), y(b), z(0), w(1) {}

    Vector4(const Vector2& a) : x(a.x), y(a.y), z(0), w(1) {}

    Vector4(float a, float b, float c) : x(a), y(b), z(c), w(1) {}

    Vector4(float a, float b, float c, float d) : x(a), y(b), z(c), w(d) {}

    Vector4(const Vector2& ab, float c) : x(ab.x), y(ab.y), z(c), w(1) {}

    Vector4(const Vector2& ab, float c, float d) : x(ab.x), y(ab.y), z(c), w(d) {}

    Vector4(float a, const Vector2& bc, float d) : x(a), y(bc.x), z(bc.y), w(d) {}

    Vector4(float a, const Vector2& bc) : x(a), y(bc.x), z(bc.y), w(1) {}

    Vector4(float a, float b, const Vector2& cd) : x(a), y(b), z(cd.x), w(cd.y) {}

    Vector4(const Vector3& r) : x(r.x), y(r.y), z(r.z), w(1) {}

    Vector4(const Vector3& r, float d) : x(r.x), y(r.y), z(r.z), w(d) {}

    Vector4(float a, const Vector3& r) : x(a), y(r.x), z(r.y), w(r.z) {}

    Vector4(const Vector4& r) : x(r.x), y(r.y), z(r.z), w(r.w) {}

    bool operator==(const Vector4& r) const { return x == r.x && y == r.y && z == r.z && w == r.w; }
    bool operator!=(const Vector4& r) const { return !(*this == r); }

    Vector4& operator=(const Vector4& r)
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

    float Dot(const Vector4& r) const { return x * r.x + y * r.y + z * r.z + w * r.w; }

    Vector4 Normalized() const
    {
        float len = Length();

        if (len != 0)
        {
            float inv = 1 / len;
            return Vector4(x * inv, y * inv, z * inv, w * inv);
        }

        return *this;
    }

    Vector4& operator+=(const Vector4& r)
    {
        x += r.x;
        y += r.y;
        z += r.z;
        w += r.w;
        return *this;
    }

    Vector4& operator-=(const Vector4& r)
    {
        x -= r.x;
        y -= r.y;
        z -= r.z;
        w -= r.w;
        return *this;
    }

    Vector4& operator*=(const Vector4& r)
    {
        x *= r.x;
        y *= r.y;
        z *= r.z;
        w *= r.w;
        return *this;
    }

    Vector4& operator/=(const Vector4& r)
    {
        x /= r.x;
        y /= r.y;
        z /= r.z;
        w /= r.w;
        return *this;
    }

    Vector4 operator-() const { return Vector4(-x, -y, -z, -w); }
};

inline Vector4 operator+(Vector4 a, const Vector4& b) { return a += b; }
inline Vector4 operator-(Vector4 a, const Vector4& b) { return a -= b; }
inline Vector4 operator*(Vector4 a, const Vector4& b) { return a *= b; }
inline Vector4 operator/(Vector4 a, const Vector4& b) { return a /= b; }

inline std::ostream& operator<<(std::ostream& out, const Vector4& r)
{
    return out << "(" << r.x << ", " << r.y << ", " << r.z << ", " << r.w << ")";
}

}
