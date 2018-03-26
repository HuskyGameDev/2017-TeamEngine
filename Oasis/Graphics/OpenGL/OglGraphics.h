#pragma once

#include "Oasis/Graphics/Graphics.h"

#include "Oasis/Graphics/OpenGL/OglGeometry.h"
//#include "Oasis/Graphics/OpenGL/OglIndexBuffer.h"
#include "Oasis/Graphics/OpenGL/OglVertexBuffer.h"

namespace Oasis
{

class OASIS_API OglGraphics : public Graphics
{
public:
    OglGraphics();
    ~OglGraphics();

    int GetMaxTextureCount() const;

    void PreRender();
    void PostRender();

    void SetViewport(int x, int y, int w, int h);

    void SetClearColor(const Vector4& color);
    void Clear();

    void SetGeometry(Geometry* geom);
    void SetShader(Shader* shader);

    void SetTexture(int unit, Texture* tex);

    void DrawArrays(Primitive prim, int start, int primCount);
    void DrawIndexed(Primitive prim, int start, int primCount);

    IndexBuffer* CreateIndexBuffer(int numElements, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    VertexBuffer* CreateVertexBuffer(int numElements, const VertexFormat& format, BufferUsage usage = BUFFER_USAGE_DYNAMIC);
    Geometry* CreateGeometry();
    Shader* CreateShader(const std::string& vs, const std::string& fs);
    Texture2D* CreateTexture2D();

private:
    void BindGeometry();

    OglGeometry* m_geometry;
};

}
