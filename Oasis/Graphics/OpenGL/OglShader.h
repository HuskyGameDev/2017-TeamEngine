#pragma once

#include "Oasis/Graphics/Shader.h"
#include "Oasis/Graphics/VertexFormat.h"

#include <GL/glew.h>

namespace Oasis
{

class OASIS_API OglShader : public Shader
{
public:
    static int GetAttributeIndex(Attribute attrib);
    static const std::string& GetAttributeName(Attribute attrib);

    OglShader(const std::string& vs, const std::string& fs);
    ~OglShader();

    void Release();

    GLuint GetId() const;

    bool IsValid() const;

    const std::string& GetErrorMessage() const;

    const std::string& GetVertexSource() const;
    const std::string& GetFragmentSource() const;

private:
    bool Compile(GLuint id, GLuint type, const std::string& typeName, const std::string& source);
    bool Link(GLuint vs, GLuint fs);

    GLuint m_id;
    bool m_valid;
    std::string m_errorMessage;
    std::string m_vSource;
    std::string m_fSource;
};

}
