#include "Oasis/Graphics/VertexFormat.h"

namespace Oasis
{

const VertexFormat VertexFormat::POSITION = VertexFormat().AddAttribute(ATTRIBUTE_POSITION);
const VertexFormat VertexFormat::NORMAL = VertexFormat().AddAttribute(ATTRIBUTE_NORMAL);
const VertexFormat VertexFormat::TANGENT = VertexFormat().AddAttribute(ATTRIBUTE_TANGENT);
const VertexFormat VertexFormat::TEXTURE = VertexFormat().AddAttribute(ATTRIBUTE_TEXTURE);
const VertexFormat VertexFormat::COLOR = VertexFormat().AddAttribute(ATTRIBUTE_COLOR);

int GetAttributeSize(Attribute attrib)
{
    switch (attrib)
    {
    case ATTRIBUTE_TANGENT: return 3;
    case ATTRIBUTE_POSITION: return 3;
    case ATTRIBUTE_NORMAL: return 3;
    case ATTRIBUTE_COLOR: return 4;
    case ATTRIBUTE_TEXTURE: return 2;
    default: return 0;
    }
}

VertexFormat::VertexFormat()
    : m_elements()
    , m_size(0) {}

VertexFormat::VertexFormat(const VertexFormat& other)
    : m_elements(other.m_elements)
    , m_size(other.m_size) {}

VertexFormat& VertexFormat::operator=(const VertexFormat& other)
{
    if (this == &other) return *this;

    m_elements = other.m_elements;
    m_size = other.m_size;
    return *this;
}

bool VertexFormat::operator==(const VertexFormat& other) const
{
    return m_elements == other.m_elements;
}

bool VertexFormat::operator!=(const VertexFormat& other) const
{
    return !(*this == other);
}

VertexFormat& VertexFormat::AddAttribute(Attribute attrib)
{
    m_elements.push_back(attrib);
    m_size += GetAttributeSize(attrib);

    return *this;
}

Attribute VertexFormat::GetAttribute(int index) const
{
    return m_elements[index];
}

int VertexFormat::GetAttributeCount() const
{
    return m_elements.size();
}

int VertexFormat::GetSize() const
{
    return m_size;
}

int VertexFormat::GetOffset(Attribute attrib) const
{
    int off = 0;

    for (unsigned i = 0; i < m_elements.size(); i++)
    {
        if (m_elements[i] == attrib) return off;

        off += GetAttributeSize(m_elements[i]);
    }

    return 0;
}

}
