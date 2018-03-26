#include "Oasis/Graphics/OpenGL/OglGraphics.h"

#include "Oasis/Core/Window.h"

#include "Oasis/Graphics/OpenGL/OglShader.h"

#include <GL/glew.h>

#include <iostream>
#include <string>

using namespace std;

namespace Oasis
{

static const string OGL_VS = ""
    "#version 120 \n"
    "attribute vec3 a_Position; \n"
    "void main() { \n"
    "  gl_Position = vec4(a_Position, 1.0); \n"
    "} \n";

static const string OGL_FS = ""
    "#version 120 \n"
    "void main() { \n"
    "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0); \n"
    "} \n";

static const GLuint OGL_PRIMITIVE[PRIMITIVE_COUNT] =
{
    GL_LINES,
    GL_LINE_STRIP,
    GL_TRIANGLES,
    GL_TRIANGLE_STRIP
};

void OglGraphics::BindGeometry()
{
    if (!m_geometry) return;

    int attribs[ATTRIBUTE_COUNT];

    for (int i = 0; i < ATTRIBUTE_COUNT; i++) attribs[i] = -1;

    for (int i = 0; i < m_geometry->GetVertexBufferCount(); i++)
    {
        OglVertexBuffer* vb = (OglVertexBuffer*) m_geometry->GetVertexBuffer(i);
        const VertexFormat& format = vb->GetFormat();

        for (int j = 0; j < format.GetAttributeCount(); j++)
        {
            Attribute attrib = format.GetAttribute(j);

            if (attribs[attrib] == -1)
            {
                attribs[attrib] = i;
            }
        }
    }

    for (int i = 0; i < ATTRIBUTE_COUNT; i++)
    {
        if (attribs[i] == -1)
        {
            glDisableVertexAttribArray(OglShader::GetAttributeIndex((Attribute) i));
            cout << "Disabling " << OglShader::GetAttributeName((Attribute) i) << endl;
            continue;
        }

        OglVertexBuffer* vb = (OglVertexBuffer*) m_geometry->GetVertexBuffer(attribs[i]);
        cout << "Binding " << vb->GetId() << endl;
        glBindBuffer(GL_ARRAY_BUFFER, vb->GetId());

        cout << "Enabling " << OglShader::GetAttributeName((Attribute) i) << endl;
        glEnableVertexAttribArray(OglShader::GetAttributeIndex((Attribute) i));

        cout << "attrib pointer "
            << i << " "
            << GetAttributeSize((Attribute) i) << " "
            << vb->GetFormat().GetSize() * sizeof (float) << " "
            << (vb->GetFormat().GetOffset((Attribute) i) * sizeof (float)) << endl;
        glVertexAttribPointer(i, GetAttributeSize((Attribute) i), GL_FLOAT, GL_FALSE, vb->GetFormat().GetSize() * sizeof (float), (void*) (vb->GetFormat().GetOffset((Attribute) i) * sizeof (float)));
    }
}

OglGraphics::OglGraphics()
    : m_geometry(NULL)
{
    OglShader* shader = (OglShader*) CreateShader(OGL_VS, OGL_FS);

    /*GLuint shader, vert, frag;

    shader = glCreateProgram();
    vert = glCreateShader(GL_VERTEX_SHADER);
    frag = glCreateShader(GL_FRAGMENT_SHADER);

    const char* vs = OGL_VS.c_str();
    int vsLength = OGL_VS.length();

    const char* fs = OGL_FS.c_str();
    int fsLength = OGL_FS.length();

    glShaderSource(vert, 1, &vs, &vsLength);
    glCompileShader(vert);

    glShaderSource(frag, 1, &fs, &fsLength);
    glCompileShader(frag);

    glAttachShader(shader, vert);
    glAttachShader(shader, frag);

    for (int i = 0; i < ATTRIBUTE_COUNT; i++)
    {
        glBindAttribLocation(shader, OGL_ATTRIBUTE_INDEX[i], OGL_ATTRIBUTE_NAME[i].c_str());
        cout << "Binding attrib location " << OGL_ATTRIBUTE_INDEX[i] << " " << OGL_ATTRIBUTE_NAME[i] << endl;
    }

    glLinkProgram(shader);*/

    glUseProgram(shader->GetId());
}

OglGraphics::~OglGraphics()
{

}

int OglGraphics::GetMaxTextureCount() const
{
    return 16;
}

void OglGraphics::PreRender()
{
    SetViewport(0, 0, Engine::GetWindow()->GetWidth(), Engine::GetWindow()->GetHeight());
    SetClearColor(0);
    Clear();
}

void OglGraphics::PostRender()
{

}

void OglGraphics::SetViewport(int x, int y, int w, int h)
{
    glViewport(x, y, w, h);
}

void OglGraphics::SetClearColor(const Vector4& color)
{
    glClearColor(color.x, color.y, color.z, color.w);
}

void OglGraphics::Clear()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
}

void OglGraphics::SetGeometry(Geometry* geom)
{
    m_geometry = (OglGeometry*) geom;
}

void OglGraphics::SetShader(Shader* shader)
{

}

void OglGraphics::SetTexture(int unit, Texture* tex)
{

}

void OglGraphics::DrawArrays(Primitive prim, int start, int primCount)
{
    if (!m_geometry) return;

    BindGeometry();

    // TODO primitive size
    glDrawArrays(OGL_PRIMITIVE[prim], start, primCount * 3);
}

void OglGraphics::DrawIndexed(Primitive prim, int start, int primCount)
{

}

IndexBuffer* OglGraphics::CreateIndexBuffer(int numElements, BufferUsage usage)
{
    return NULL;
}

VertexBuffer* OglGraphics::CreateVertexBuffer(int numElements, const VertexFormat& format, BufferUsage usage)
{
    return new OglVertexBuffer(numElements, format, usage);
}

Geometry* OglGraphics::CreateGeometry()
{
    return new OglGeometry();
}

Shader* OglGraphics::CreateShader(const std::string& vs, const std::string& fs)
{
    return new OglShader(vs, fs);
}

Texture2D* OglGraphics::CreateTexture2D()
{
    return NULL;
}


}
