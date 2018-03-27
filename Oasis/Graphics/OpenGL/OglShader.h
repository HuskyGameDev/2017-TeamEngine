#pragma once

#include "Oasis/Graphics/Shader.h"
#include "Oasis/Graphics/VertexFormat.h"

#include "Oasis/Math/MathUtil.h"

#include <GL/glew.h>

#include <unordered_map>

namespace Oasis
{

class OglShader;

struct OASIS_API UniformValue
{
    UniformValue() : location(0), type(UNIFORM_UNKNOWN) {}
    UniformValue(GLuint loc, Uniform type) : location(loc), type(type) {}
    UniformValue(const UniformValue& uv) : location(uv.location), type(uv.type) {}
    UniformValue& operator=(const UniformValue& uv)
    {
        if (this == &uv) return *this;
        location = uv.location;
        type = uv.type;
        return *this;
    }

    GLuint location;
    Uniform type;
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
