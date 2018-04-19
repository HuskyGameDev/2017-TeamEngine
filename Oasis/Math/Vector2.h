#pragma once

#include "Oasis/Oasis.h"

#include <cmath>
#include <ostream>

namespace Oasis
{

struct OASIS_API Vector2
{
    static const Vector2 ZERO;
    static const Vector2 UP;
    static const Vector2 DOWN;
    static const Vector2 LEFT;
    static const Vector2 RIGHT;

    float x, y;

    Vector2() : x(0), y(0) {}

    Vector2(float a) : x(a), y(a) {}

    Vector2(float a, float b) : x(a), y(b) {}

    Vector2(const Vector2& r) : x(r.x), y(r.y) {}

    Vector2& operator=(const Vector2& r)
    {
        if (this == &r) return *this;
        x = r.x;
        y = r.y;
        return *this;
    }

    bool operator==(const Vector2& r) const { return x == r.x && y == r.y; }
    bool operator!=(const Vector2& r) const { return !(*this == r); }

    const float& operator[](int index) const { return (&x)[index]; };
    float& operator[](int index) { return (&x)[index]; };

    float LengthSq() const { return x * x + y * y; }
    float Length() const { return std::sqrt(LengthSq()); }

    float Dot(const Vector2& r) const { return x * r.x + y * r.y; }

    Vector2 Normalized() const
    {
        float len = Length();

        if (len != 0)
        {
            float inv = 1 / len;
            return Vector2(x * inv, y * inv);
        }

        return *this;
    }

    Vector2& operator+=(const Vector2& r)
    {
        x += r.x;
        y += r.y;
        return *this;
    }

    Vector2& operator-=(const Vector2& r)
    {
        x -= r.x;
        y -= r.y;
        return *this;
    }

    Vector2& operator*=(const Vector2& r)
    {
        x *= r.x;
        y *= r.y;
        return *this;
    }

    Vector2& operator/=(const Vector2& r)
    {
        x /= r.x;
        y /= r.y;
        return *this;
    }

    Vector2 operator-() const { return Vector2(-x, -y); }
};

inline Vector2 operator+(Vector2 a, const Vector2& b) { return a += b; }
inline Vector2 operator-(Vector2 a, const Vector2& b) { return a -= b; }
inline Vector2 operator*(Vector2 a, const Vector2& b) { return a *= b; }
inline Vector2 operator/(Vector2 a, const Vector2& b) { return a /= b; }

inline std::ostream& operator<<(std::ostream& out, const Vector2& r)
{
    return out << "(" << r.x << ", " << r.y << ")";
}

}
