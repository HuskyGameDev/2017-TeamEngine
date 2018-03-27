#pragma once

#include "Oasis/Graphics/Shader.h"
#include "Oasis/Graphics/VertexFormat.h"

#include "Oasis/Math/MathUtil.h"

#include <GL/glew.h>

#include <unordered_map>

namespace Oasis
{

class OglShader;

class OASIS_API UniformValue
{
public:
    UniformValue() : m_dirty(true), m_id(0), m_type(UNIFORM_UNKNOWN), m_matrix4Value(0) {}
    UniformValue(GLuint id, Uniform type) : m_dirty(true), m_id(id), m_type(type), m_matrix4Value(0) {}
    UniformValue(const UniformValue& uv) : m_dirty(true), m_id(uv.m_id), m_type(uv.m_type), m_matrix4Value(uv.m_matrix4Value) {}
    UniformValue& operator=(const UniformValue& uv)
    {
        if (this == &uv) return *this;
        m_dirty = uv.m_dirty;
        m_id = uv.m_id;
        m_type = uv.m_type;
        m_matrix4Value = uv.m_matrix4Value;
        return *this;
    }

    GLuint GetId() const { return m_id; }
    Uniform GetUniform() const { return m_type; }

    bool SetValue(int value);
    bool SetValue(float value);
    bool SetValue(const Vector2& value);
    bool SetValue(const Vector3& value);
    bool SetValue(const Vector4& value);
    bool SetValue(const Matrix3& value);
    bool SetValue(const Matrix4& value);

    void Clear() { m_matrix4Value = 0; m_dirty = true; }

private:
    friend class OglShader;

    bool m_dirty;
    GLuint m_id;
    Uniform m_type;
    union
    {
        int m_intValue;
        float m_floatValue;
        Vector2 m_vector2Value;
        Vector3 m_vector3Value;
        Vector4 m_vector4Value;
        Matrix3 m_matrix3Value;
        Matrix4 m_matrix4Value;
    };
};

class OASIS_API OglShader : public Shader
{
public:
    static int GetAttributeIndex(Attribute attrib);
    static const std::string& GetAttributeName(Attribute attrib);

    static Uniform GetUniformType(GLenum type);

    OglShader(const std::string& vs, const std::string& fs);
    ~OglShader();

    void Release();

    GLuint GetId() const;

    bool IsValid() const;

    const std::string& GetErrorMessage() const;

    const std::string& GetVertexSource() const;
    const std::string& GetFragmentSource() const;

    UniformValue* GetUniformValue(const std::string& name);

private:
    bool Compile(GLuint id, GLuint type, const std::string& typeName, const std::string& source);
    bool Link(GLuint vs, GLuint fs);

    void FindUniforms();

    GLuint m_id;
    bool m_valid;
    std::string m_errorMessage;
    std::string m_vSource;
    std::string m_fSource;

    std::unordered_map<std::string, UniformValue> m_uniformValues;
};

}
