#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/Matrix4.h"
#include "Oasis/Math/Vector2.h"
#include "Oasis/Math/Vector3.h"

#include <cmath>

namespace Oasis
{

struct OASIS_API Matrix3
{
    static const Matrix3 ZERO;
    static const Matrix3 IDENTITY;

    float m00, m10, m20;
    float m01, m11, m21;
    float m02, m12, m22;

    static Matrix3 Identity()
    {
        Matrix3 m;
        m.m00 = 1;
        m.m11 = 1;
        m.m22 = 1;
        return m;
    }

    static Matrix3 Scale(const Vector2& s)
    {
        Matrix3 m;
        m.m00 = s.x;
        m.m11 = s.y;
        m.m22 = 1;
        return m;
    }

    static Matrix3 Translation(const Vector2& t)
    {
        Matrix3 m = Identity();
        m.m02 = t.x;
        m.m12 = t.y;
        return m;
    }

    static Matrix3 Rotation(float rad)
    {
        Matrix3 m = Identity();
        float c = std::cos(rad);
        float s = std::sin(rad);
        m.m00 = c;
        m.m01 = -s;
        m.m10 = s;
        m.m11 = c;
        return m;
    }

    Matrix3(float m00, float m10, float m20,
            float m01, float m11, float m21,
            float m02, float m12, float m22)
        : m00(m00), m10(m10), m20(m20)
        , m01(m01), m11(m11), m21(m21)
        , m02(m02), m12(m12), m22(m22) {}

    Matrix3(const Vector3& m0, const Vector3& m1, const Vector3& m2)
        : m00(m0.x), m10(m0.y), m20(m0.z)
        , m01(m1.x), m11(m1.y), m21(m1.z)
        , m02(m2.x), m12(m2.y), m22(m2.z) {}

    Matrix3(float value = 0)
    {
        for (int i = 0; i < 9; i++) (*this)[i] = value;
    }

    static Matrix3 FromArray(const float m[])
    {
        Matrix3 out;
        for (int i = 0; i < 9; i++) out[i] = m[i];
        return out;
    }

    Matrix3(const Matrix3& r)
    {
        for (int i = 0; i < 9; i++) (*this)[i] = r[i];
    }

    Matrix3(const Matrix4& r)
        : m00(r.m00), m10(r.m10), m20(r.m20)
        , m01(r.m01), m11(r.m11), m21(r.m21)
        , m02(r.m02), m12(r.m12), m22(r.m22) {}

    Matrix3& operator=(const Matrix3& r)
    {
        if (this == &r) return *this;

        for (int i = 0; i < 9; i++) (*this)[i] = r[i];

        return *this;
    }

    bool operator==(const Matrix3& r) const
    {
        for (int i = 0; i < 9; i++) if ((*this)[i] != r[i]) return false;
        return true;
    }
    bool operator!=(const Matrix3& r) const { return !(*this == r); }

    const Vector3& Column(int index) const { return reinterpret_cast<const Vector3*>(&m00)[index]; }
    Vector3& Column(int index) { return reinterpret_cast<Vector3*>(&m00)[index]; }

    const float& operator[](int index) const { return (&m00)[index]; }
    float& operator[](int index) { return (&m00)[index]; }

    Matrix4 Transpose() const
    {
        Matrix4 out;
        out.m00 = m00;
        out.m01 = m10;
        out.m02 = m20;
        out.m10 = m01;
        out.m11 = m11;
        out.m12 = m21;
        out.m20 = m02;
        out.m21 = m12;
        out.m22 = m22;
        return out;
    }

    Matrix3& operator*=(const Matrix3& r)
    {
        Matrix3 out;

        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20;
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20;
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20;

        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21;
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21;
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21;

        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22;
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22;
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22;

        return *this = out;
    }

    Matrix3& operator+=(float s)
    {
        m00 += s;
        m10 += s;
        m20 += s;
        m01 += s;
        m11 += s;
        m21 += s;
        m02 += s;
        m12 += s;
        m22 += s;
        return *this;
    }

    Matrix3& operator-=(float s)
    {
        m00 -= s;
        m10 -= s;
        m20 -= s;
        m01 -= s;
        m11 -= s;
        m21 -= s;
        m02 -= s;
        m12 -= s;
        m22 -= s;
        return *this;
    }

    Matrix3& operator*=(float s)
    {
        m00 *= s;
        m10 *= s;
        m20 *= s;
        m01 *= s;
        m11 *= s;
        m21 *= s;
        m02 *= s;
        m12 *= s;
        m22 *= s;
        return *this;
    }

    Matrix3 operator-() const { return Matrix3(*this) *= -1; }
};

inline Matrix3 operator*(Matrix3 a, const Matrix3& b) { return a *= b; }
inline Matrix3 operator*(Matrix3 a, float b) { return a *= b; }
inline Matrix3 operator*(float a, Matrix3 b) { return b *= a; }

inline Matrix3 operator+(Matrix3 a, float b) { return a += b; }
inline Matrix3 operator+(float a, Matrix3 b) { return b += a; }

inline Matrix3 operator-(Matrix3 a, float b) { return a -= b; }
inline Matrix3 operator-(float a, Matrix3 b) { return -b += a; }

inline std::ostream& operator<<(std::ostream& out, const Matrix3& r)
{
    return out << "(" << r.m00 << ", " << r.m01 << ", " << r.m02 << ")" << std::endl
               << "(" << r.m10 << ", " << r.m11 << ", " << r.m12 << ")" << std::endl
               << "(" << r.m20 << ", " << r.m21 << ", " << r.m22 << ")";
}

}
