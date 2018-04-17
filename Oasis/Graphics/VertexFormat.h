#pragma once

#include "Oasis/Oasis.h"

#include <vector>

namespace Oasis
{

enum Attribute
{
    ATTRIBUTE_POSITION,
    ATTRIBUTE_NORMAL,
    ATTRIBUTE_TANGENT,
    ATTRIBUTE_TEXTURE,
    ATTRIBUTE_COLOR,

    ATTRIBUTE_COUNT
};

OASIS_API int GetAttributeSize(Attribute attrib);

class OASIS_API VertexFormat
{
public:
    static const VertexFormat POSITION;
    static const VertexFormat NORMAL;
    static const VertexFormat TANGENT;
    static const VertexFormat TEXTURE;
    static const VertexFormat COLOR;

    VertexFormat();
    VertexFormat(const VertexFormat& other);
    VertexFormat& operator=(const VertexFormat& other);

    bool operator==(const VertexFormat& other) const;
    bool operator!=(const VertexFormat& other) const;

    VertexFormat& AddAttribute(Attribute attrib);

    Attribute GetAttribute(int index) const;

    int GetOffset(Attribute attrib) const;

    int GetAttributeCount() const;
    int GetSize() const;
private:
    std::vector<Attribute> m_elements;
    int m_size;
};

}

