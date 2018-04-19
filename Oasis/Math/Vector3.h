#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Math/Vector2.h"

#include <cmath>

namespace Oasis
{

struct OASIS_API Vector3
{
    static const Vector3 ZERO;
    static const Vector3 UP;
    static const Vector3 DOWN;
    static const Vector3 LEFT;
    static const Vector3 RIGHT;
    static const Vector3 FORWARD;
    static const Vector3 BACKWARD;

    float x, y, z;

    Vector3() : x(0), y(0), z(0) {}

    Vector3(float a) : x(a), y(a), z(a) {}

    Vector3(float a, float b) : x(a), y(b), z(0) {}

    Vector3(const Vector2& a) : x(a.x), y(a.y), z(0) {}

    Vector3(float a, float b, float c) : x(a), y(b), z(c) {}

    Vector3(const Vector2& ab, float c) : x(ab.x), y(ab.y), z(c) {}

    Vector3(float a, const Vector2& bc) : x(a), y(bc.x), z(bc.y) {}

    Vector3(const Vector3& r) : x(r.x), y(r.y), z(r.z) {}

    bool operator==(const Vector3& r) const { return x == r.x && y == r.y && z == r.z; }
    bool operator!=(const Vector3& r) const { return !(*this == r); }

    Vector3& operator=(const Vector3& r)
    {
        if (this == &r) return *this;
        x = r.x;
        y = r.y;
        z = r.z;
        return *this;
    }

    const float& operator[](int index) const { return (&x)[index]; };
    float& operator[](int index) { return (&x)[index]; };

    float LengthSq() const { return x * x + y * y + z * z; }
    float Length() const { return std::sqrt(LengthSq()); }

    float Dot(const Vector3& r) const { return x * r.x + y * r.y + z * r.z; }

    Vector3 Normalized() const
    {
        float len = Length();

        if (len != 0)
        {
            float inv = 1 / len;
            return Vector3(x * inv, y * inv, z * inv);
        }

        return *this;
    }

    Vector3 Cross(const Vector3& r) const
    {
        float x_ = y * r.z - z * r.y;
        float y_ = z * r.x - x * r.z;
        float z_ = x * r.y - y * r.x;
        return Vector3(x_, y_, z_);
    }

    Vector3& operator+=(const Vector3& r)
    {
        x += r.x;
        y += r.y;
        z += r.z;
        return *this;
    }

    Vector3& operator-=(const Vector3& r)
    {
        x -= r.x;
        y -= r.y;
        z -= r.z;
        return *this;
    }

    Vector3& operator*=(const Vector3& r)
    {
        x *= r.x;
        y *= r.y;
        z *= r.z;
        return *this;
    }

    Vector3& operator/=(const Vector3& r)
    {
        x /= r.x;
        y /= r.y;
        z /= r.z;
        return *this;
    }

    Vector3 operator-() const { return Vector3(-x, -y, -z); }
};

inline Vector3 operator+(Vector3 a, const Vector3& b) { return a += b; }
inline Vector3 operator-(Vector3 a, const Vector3& b) { return a -= b; }
inline Vector3 operator*(Vector3 a, const Vector3& b) { return a *= b; }
inline Vector3 operator/(Vector3 a, const Vector3& b) { return a /= b; }

inline std::ostream& operator<<(std::ostream& out, const Vector3& r)
{
    return out << "(" << r.x << ", " << r.y << ", " << r.z << ")";
}

}
