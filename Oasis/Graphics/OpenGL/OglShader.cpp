#include "Oasis/Graphics/OpenGL/OglShader.h"

#include "Oasis/Graphics/VertexFormat.h"

#include <iostream>

using namespace std;

namespace Oasis
{

static const int OGL_ATTRIBUTE_INDEX[ATTRIBUTE_COUNT] =
{
    0, 1, 2, 3, 4
};

static const string OGL_ATTRIBUTE_NAME[ATTRIBUTE_COUNT] =
{
    "a_Position",
    "a_Normal",
    "a_Bitangent",
    "a_Texture",
    "a_Color"
};

int OglShader::GetAttributeIndex(Attribute attrib)
{
    return OGL_ATTRIBUTE_INDEX[attrib];
}

const string& OglShader::GetAttributeName(Attribute attrib)
{
    return OGL_ATTRIBUTE_NAME[attrib];
}

OglShader::OglShader(const string& vs, const string& fs)
    : m_id(0)
    , m_valid(true)
    , m_errorMessage()
    , m_vSource(vs)
    , m_fSource(fs)
{
    cout << "Vertex source: " << vs << endl;
    cout << "Fragment source: " << fs << endl;

    m_id = glCreateProgram();
    GLuint vId = glCreateShader(GL_VERTEX_SHADER);
    GLuint fId = glCreateShader(GL_FRAGMENT_SHADER);

    m_valid &= Compile(vId, GL_VERTEX_SHADER, "vertex shader", m_vSource);
    m_valid &= Compile(fId, GL_FRAGMENT_SHADER, "fragment shader", m_fSource);
    m_valid &= Link(vId, fId);

    glDeleteShader(vId);
    glDeleteShader(fId);
}

OglShader::~OglShader()
{
    Release();
}

void OglShader::Release()
{
    if (m_id)
    {
        glDeleteProgram(m_id);
        m_id = 0;
    }
}

GLuint OglShader::GetId() const
{
    return m_id;
}

bool OglShader::IsValid() const
{
    return m_valid;
}

const string& OglShader::GetErrorMessage() const
{
    return m_errorMessage;
}

const string& OglShader::GetVertexSource() const
{
    return m_vSource;
}

const string& OglShader::GetFragmentSource() const
{
    return m_fSource;
}

bool OglShader::Compile(GLuint id, GLuint type, const string& typeName, const string& source)
{
    const char* src = source.c_str();
    int srcLength = source.length();
    GLint status;

    glShaderSource(id, 1, &src, &srcLength);
    glCompileShader(id);

    glGetShaderiv(id, GL_COMPILE_STATUS, &status);
    if (status != GL_TRUE)
    {
        char text[512];
        GLsizei length;
        glGetShaderInfoLog(id, 512, &length, text);

        m_errorMessage += text;
        m_errorMessage += "\n";

        cout << "Error compiling " << typeName << " : " << text << endl;
        return false;
    }

    cout << "Compiling " << typeName << " success!" << endl;
    return true;
}

bool OglShader::Link(GLuint vs, GLuint fs)
{
    glAttachShader(m_id, vs);
    glAttachShader(m_id, fs);

    // bind attribs
    for (int i = 0; i < ATTRIBUTE_COUNT; i++)
    {
        glBindAttribLocation(m_id, OGL_ATTRIBUTE_INDEX[i], OGL_ATTRIBUTE_NAME[i].c_str());
        cout << "Binding attrib location " << OGL_ATTRIBUTE_INDEX[i] << " " << OGL_ATTRIBUTE_NAME[i] << endl;
    }

    glLinkProgram(m_id);

    GLint status;
    glGetProgramiv(m_id, GL_LINK_STATUS, &status);
    if (status != GL_TRUE)
    {
        char text[512];
        GLsizei length;
        glGetProgramInfoLog(m_id, 512, &length, text);

        m_errorMessage += text;
        m_errorMessage += "\n";

        cout << "Error linking shader : " << text << endl;

        glDeleteShader(m_id);
        m_id = 0;

        return false;
    }

    cout << "Linking shader success!" << endl;
    return true;
}


}