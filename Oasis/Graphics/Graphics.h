#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Graphics/HardwareBuffer.h"
#include "Oasis/Graphics/VertexFormat.h"

#include "Oasis/Graphics/Geometry.h"
#include "Oasis/Graphics/IndexBuffer.h"
#include "Oasis/Graphics/VertexBuffer.h"

#include "Oasis/Math/Vector4.h"

#include <string>

namespace Oasis
{

class IndexBuffer;
class Geometry;
class Shader;
class Texture;
class Texture2D;
class VertexBuffer;

enum Primitive
{
    PRIMITIVE_LINE_LIST,
    PRIMITIVE_LINE_STRIP,
    PRIMITIVE_TRIANGLE_LIST,
    PRIMITIVE_TRIANGLE_STRIP,

    PRIMITIVE_COUNT
};

class OASIS_API Graphics
{
public:
    virtual ~Graphics() {}

    virtual int GetMaxTextureCount() const = 0;

    virtual void PreRender() = 0;
    virtual void PostRender() = 0;

    virtual void SetViewport(int x, int y, int w, int h) = 0;

    virtual void SetClearColor(const Vector4& color) = 0;
    virtual void Clear() = 0;

    virtual void SetGeometry(Geometry* geom) = 0;
    virtual void SetShader(Shader* shader) = 0;

    virtual void SetTexture(int unit, Texture* tex) = 0;

    virtual void DrawArrays(Primitive prim, int start, int primCount) = 0;
    virtual void DrawIndexed(Primitive prim, int start, int primCount) = 0;
/*
    virtual void ClearUniform(const std::string& name) = 0;
    virtual void SetUniformInt(const std::string& name, int value) = 0;
    virtual void SetUniformFloat(const std::string& name, float value) = 0;
    virtual void SetUniformVector2(const std::string& name, const Vector2& value) = 0;
    virtual void SetUniformVector3(const std::string& name, const Vector3& value) = 0;
    virtual void SetUniformVector4(const std::string& name, const Vector4& value) = 0;
    virtual void SetUniformMatrix3(const std::string& name, const Matrix3& value) = 0;
    virtual void SetUniformMatrix4(const std::string& name, const Matrix4& value) = 0;
*/
    virtual IndexBuffer* CreateIndexBuffer(int numElements, BufferUsage usage = BUFFER_USAGE_DYNAMIC) = 0;

    virtual VertexBuffer* CreateVertexBuffer(int numElements, const VertexFormat& format, BufferUsage usage = BUFFER_USAGE_DYNAMIC) = 0;

    virtual Geometry* CreateGeometry() = 0;

    virtual Shader* CreateShader(const std::string& vertexSource, const std::string& fragmentSource) = 0;

    virtual Texture2D* CreateTexture2D() = 0;
};

}
