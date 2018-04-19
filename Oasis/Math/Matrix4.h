#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Math/Vector4.h"
#include "Oasis/Math/Vector3.h"

#include <cmath>
#include <ostream>

namespace Oasis
{

struct Quaternion;

struct OASIS_API Matrix4
{
    static const Matrix4 ZERO;
    static const Matrix4 IDENTITY;

    float m00, m10, m20, m30;
    float m01, m11, m21, m31;
    float m02, m12, m22, m32;
    float m03, m13, m23, m33;

    static Matrix4 Identity()
    {
        Matrix4 m;
        m.m00 = 1;
        m.m11 = 1;
        m.m22 = 1;
        m.m33 = 1;
        return m;
    }

    static Matrix4 Scale(const Vector3& s)
    {
        Matrix4 m;
        m.m00 = s.x;
        m.m11 = s.y;
        m.m22 = s.z;
        m.m33 = 1;
        return m;
    }

    static Matrix4 Translation(const Vector3& t)
    {
        Matrix4 m = Identity();
        m.m03 = t.x;
        m.m13 = t.y;
        m.m23 = t.z;
        return m;
    }

    static Matrix4 RotationX(float rad)
    {
        Matrix4 m = Identity();
        float c = std::cos(rad);
        float s = std::sin(rad);
        m.m11 = c;
        m.m12 = -s;
        m.m21 = s;
        m.m22 = c;
        return m;
    }

    static Matrix4 RotationY(float rad)
    {
        Matrix4 m = Identity();
        float c = std::cos(rad);
        float s = std::sin(rad);
        m.m00 = c;
        m.m02 = s;
        m.m20 = -s;
        m.m22 = c;
        return m;
    }

    static Matrix4 RotationZ(float rad)
    {
        Matrix4 m = Identity();
        float c = std::cos(rad);
        float s = std::sin(rad);
        m.m00 = c;
        m.m01 = -s;
        m.m10 = s;
        m.m11 = c;
        return m;
    }

    static Matrix4 Perspective(float fov, float ar, float near, float far)
    {
        Matrix4 m;
        fov = 1.0 / std::tan(fov * 0.5);
        m.m00 = fov / ar;
        m.m11 = fov;
        m.m22 = (far + near) / (near - far);
        m.m23 = 2 * far * near / (near - far);
        m.m32 = -1;
        return m;
    }

    static Matrix4 LookAt(const Vector3& eye, const Vector3& target, const Vector3& up)
    {
        Matrix4 m;
        Vector3 f = (target - eye).Normalized();
        Vector3 s = f.Cross(up).Normalized();
        Vector3 y = s.Cross(f).Normalized();
        m.m00 = s.x; m.m01 = s.y; m.m02 = s.z;
        m.m10 = y.x; m.m11 = y.y; m.m12 = y.z;
        m.m20 = -f.x; m.m21 = -f.y; m.m22 = -f.z;
        m.m33 = 1;
        return m *= Translation(-eye);
    }

    static Matrix4 FromQuaternion(const Quaternion& r);

    Matrix4(float m00, float m10, float m20, float m30,
            float m01, float m11, float m21, float m31,
            float m02, float m12, float m22, float m32,
            float m03, float m13, float m23, float m33)
        : m00(m00), m10(m10), m20(m20), m30(m30)
        , m01(m01), m11(m11), m21(m21), m31(m31)
        , m02(m02), m12(m12), m22(m22), m32(m32)
        , m03(m03), m13(m13), m23(m23), m33(m33) {}

    Matrix4(const Vector4& m0, const Vector4& m1, const Vector4& m2, const Vector4& m3)
        : m00(m0.x), m10(m0.y), m20(m0.z), m30(m0.w)
        , m01(m1.x), m11(m1.y), m21(m1.z), m31(m1.w)
        , m02(m2.x), m12(m2.y), m22(m2.z), m32(m2.w)
        , m03(m3.x), m13(m3.y), m23(m3.z), m33(m3.w) {}

    Matrix4(float value = 0)
    {
        for (int i = 0; i < 16; i++) (*this)[i] = value;
    }

    static Matrix4 FromArray(const float m[])
    {
        Matrix4 out;
        for (int i = 0; i < 16; i++) out[i] = m[i];
        return out;
    }

    Matrix4(const Matrix4& r)
    {
        for (int i = 0; i < 16; i++) (*this)[i] = r[i];
    }

    bool operator==(const Matrix4& r) const
    {
        for (int i = 0; i < 16; i++) if ((*this)[i] != r[i]) return false;
        return true;
    }
    bool operator!=(const Matrix4& r) const { return !(*this == r); }

    Matrix4& operator=(const Matrix4& r)
    {
        if (this == &r) return *this;

        for (int i = 0; i < 16; i++) (*this)[i] = r[i];

        return *this;
    }

    const Vector4& Column(int index) const { return reinterpret_cast<const Vector4*>(&m00)[index]; }
    Vector4& Column(int index) { return reinterpret_cast<Vector4*>(&m00)[index]; }

    const float& operator[](int index) const { return (&m00)[index]; }
    float& operator[](int index) { return (&m00)[index]; }

    Matrix4 Transpose() const
    {
        Matrix4 out;
        out.m00 = m00;
        out.m01 = m10;
        out.m02 = m20;
        out.m03 = m30;
        out.m10 = m01;
        out.m11 = m11;
        out.m12 = m21;
        out.m13 = m31;
        out.m20 = m02;
        out.m21 = m12;
        out.m22 = m22;
        out.m23 = m32;
        out.m30 = m03;
        out.m31 = m13;
        out.m32 = m23;
        out.m33 = m33;
        return out;
    }

    Matrix4& operator+=(float s)
    {
        m00 += s;
        m10 += s;
        m20 += s;
        m30 += s;
        m01 += s;
        m11 += s;
        m21 += s;
        m31 += s;
        m02 += s;
        m12 += s;
        m22 += s;
        m32 += s;
        m03 += s;
        m13 += s;
        m23 += s;
        m33 += s;

        return *this;
    }

    Matrix4& operator-=(float s)
    {
        m00 -= s;
        m10 -= s;
        m20 -= s;
        m30 -= s;
        m01 -= s;
        m11 -= s;
        m21 -= s;
        m31 -= s;
        m02 -= s;
        m12 -= s;
        m22 -= s;
        m32 -= s;
        m03 -= s;
        m13 -= s;
        m23 -= s;
        m33 -= s;

        return *this;
    }

    Matrix4& operator*=(float s)
    {
        m00 *= s;
        m10 *= s;
        m20 *= s;
        m30 *= s;
        m01 *= s;
        m11 *= s;
        m21 *= s;
        m31 *= s;
        m02 *= s;
        m12 *= s;
        m22 *= s;
        m32 *= s;
        m03 *= s;
        m13 *= s;
        m23 *= s;
        m33 *= s;

        return *this;
    }

    Matrix4& operator*=(const Matrix4& r)
    {
        Matrix4 out;

        out.m00 = m00 * r.m00 + m01 * r.m10 + m02 * r.m20 + m03 * r.m30;
        out.m10 = m10 * r.m00 + m11 * r.m10 + m12 * r.m20 + m13 * r.m30;
        out.m20 = m20 * r.m00 + m21 * r.m10 + m22 * r.m20 + m23 * r.m30;
        out.m30 = m30 * r.m00 + m31 * r.m10 + m32 * r.m20 + m33 * r.m30;

        out.m01 = m00 * r.m01 + m01 * r.m11 + m02 * r.m21 + m03 * r.m31;
        out.m11 = m10 * r.m01 + m11 * r.m11 + m12 * r.m21 + m13 * r.m31;
        out.m21 = m20 * r.m01 + m21 * r.m11 + m22 * r.m21 + m23 * r.m31;
        out.m31 = m30 * r.m01 + m31 * r.m11 + m32 * r.m21 + m33 * r.m31;

        out.m02 = m00 * r.m02 + m01 * r.m12 + m02 * r.m22 + m03 * r.m32;
        out.m12 = m10 * r.m02 + m11 * r.m12 + m12 * r.m22 + m13 * r.m32;
        out.m22 = m20 * r.m02 + m21 * r.m12 + m22 * r.m22 + m23 * r.m32;
        out.m32 = m30 * r.m02 + m31 * r.m12 + m32 * r.m22 + m33 * r.m32;

        out.m03 = m00 * r.m03 + m01 * r.m13 + m02 * r.m23 + m03 * r.m33;
        out.m13 = m10 * r.m03 + m11 * r.m13 + m12 * r.m23 + m13 * r.m33;
        out.m23 = m20 * r.m03 + m21 * r.m13 + m22 * r.m23 + m23 * r.m33;
        out.m33 = m30 * r.m03 + m31 * r.m13 + m32 * r.m23 + m33 * r.m33;

        return *this = out;
    }

    /**
     * Inverse matrx4
     * modified from https://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix
     */
    Matrix4 Inverse() const
    {
        const Matrix4& m = *this;
        float inv[16];
        float det;

        inv[0] = m[5]  * m[10] * m[15] -
                m[5]  * m[11] * m[14] -
                m[9]  * m[6]  * m[15] +
                m[9]  * m[7]  * m[14] +
                m[13] * m[6]  * m[11] -
                m[13] * m[7]  * m[10];

       inv[4] = -m[4]  * m[10] * m[15] +
                 m[4]  * m[11] * m[14] +
                 m[8]  * m[6]  * m[15] -
                 m[8]  * m[7]  * m[14] -
                 m[12] * m[6]  * m[11] +
                 m[12] * m[7]  * m[10];

       inv[8] = m[4]  * m[9] * m[15] -
                m[4]  * m[11] * m[13] -
                m[8]  * m[5] * m[15] +
                m[8]  * m[7] * m[13] +
                m[12] * m[5] * m[11] -
                m[12] * m[7] * m[9];

       inv[12] = -m[4]  * m[9] * m[14] +
                  m[4]  * m[10] * m[13] +
                  m[8]  * m[5] * m[14] -
                  m[8]  * m[6] * m[13] -
                  m[12] * m[5] * m[10] +
                  m[12] * m[6] * m[9];

       inv[1] = -m[1]  * m[10] * m[15] +
                 m[1]  * m[11] * m[14] +
                 m[9]  * m[2] * m[15] -
                 m[9]  * m[3] * m[14] -
                 m[13] * m[2] * m[11] +
                 m[13] * m[3] * m[10];

       inv[5] = m[0]  * m[10] * m[15] -
                m[0]  * m[11] * m[14] -
                m[8]  * m[2] * m[15] +
                m[8]  * m[3] * m[14] +
                m[12] * m[2] * m[11] -
                m[12] * m[3] * m[10];

       inv[9] = -m[0]  * m[9] * m[15] +
                 m[0]  * m[11] * m[13] +
                 m[8]  * m[1] * m[15] -
                 m[8]  * m[3] * m[13] -
                 m[12] * m[1] * m[11] +
                 m[12] * m[3] * m[9];

       inv[13] = m[0]  * m[9] * m[14] -
                 m[0]  * m[10] * m[13] -
                 m[8]  * m[1] * m[14] +
                 m[8]  * m[2] * m[13] +
                 m[12] * m[1] * m[10] -
                 m[12] * m[2] * m[9];

       inv[2] = m[1]  * m[6] * m[15] -
                m[1]  * m[7] * m[14] -
                m[5]  * m[2] * m[15] +
                m[5]  * m[3] * m[14] +
                m[13] * m[2] * m[7] -
                m[13] * m[3] * m[6];

       inv[6] = -m[0]  * m[6] * m[15] +
                 m[0]  * m[7] * m[14] +
                 m[4]  * m[2] * m[15] -
                 m[4]  * m[3] * m[14] -
                 m[12] * m[2] * m[7] +
                 m[12] * m[3] * m[6];

       inv[10] = m[0]  * m[5] * m[15] -
                 m[0]  * m[7] * m[13] -
                 m[4]  * m[1] * m[15] +
                 m[4]  * m[3] * m[13] +
                 m[12] * m[1] * m[7] -
                 m[12] * m[3] * m[5];

       inv[14] = -m[0]  * m[5] * m[14] +
                  m[0]  * m[6] * m[13] +
                  m[4]  * m[1] * m[14] -
                  m[4]  * m[2] * m[13] -
                  m[12] * m[1] * m[6] +
                  m[12] * m[2] * m[5];

       inv[3] = -m[1] * m[6] * m[11] +
                 m[1] * m[7] * m[10] +
                 m[5] * m[2] * m[11] -
                 m[5] * m[3] * m[10] -
                 m[9] * m[2] * m[7] +
                 m[9] * m[3] * m[6];

       inv[7] = m[0] * m[6] * m[11] -
                m[0] * m[7] * m[10] -
                m[4] * m[2] * m[11] +
                m[4] * m[3] * m[10] +
                m[8] * m[2] * m[7] -
                m[8] * m[3] * m[6];

       inv[11] = -m[0] * m[5] * m[11] +
                  m[0] * m[7] * m[9] +
                  m[4] * m[1] * m[11] -
                  m[4] * m[3] * m[9] -
                  m[8] * m[1] * m[7] +
                  m[8] * m[3] * m[5];

       inv[15] = m[0] * m[5] * m[10] -
                 m[0] * m[6] * m[9] -
                 m[4] * m[1] * m[10] +
                 m[4] * m[2] * m[9] +
                 m[8] * m[1] * m[6] -
                 m[8] * m[2] * m[5];

        det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

        if (det == 0) return Matrix4(); // set to zeros

        det = 1.0 / det;

        for (int i = 0; i < 16; i++) inv[i] *= det;

        return FromArray(inv);
    }

    Matrix4 operator-() const { return Matrix4(*this) *= -1; }
};

inline Matrix4 operator*(Matrix4 a, const Matrix4& b) { return a *= b; }
inline Matrix4 operator*(Matrix4 a, float b) { return a *= b; }
inline Matrix4 operator*(float a, Matrix4 b) { return b *= a; }

inline Matrix4 operator+(Matrix4 a, float b) { return a += b; }
inline Matrix4 operator+(float a, Matrix4 b) { return b += a; }

inline Matrix4 operator-(Matrix4 a, float b) { return a -= b; }
inline Matrix4 operator-(float a, Matrix4 b) { return -b += a; }

inline std::ostream& operator<<(std::ostream& out, const Matrix4& r)
{
    return out << "(" << r.m00 << ", " << r.m01 << ", " << r.m02 << ", " << r.m03 << ")" << std::endl
               << "(" << r.m10 << ", " << r.m11 << ", " << r.m12 << ", " << r.m13 << ")" << std::endl
               << "(" << r.m20 << ", " << r.m21 << ", " << r.m22 << ", " << r.m23 << ")" << std::endl
               << "(" << r.m30 << ", " << r.m31 << ", " << r.m32 << ", " << r.m33 << ")";
}

}
