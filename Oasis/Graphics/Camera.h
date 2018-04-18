#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/Transform.h"

namespace Oasis
{

class OASIS_API Camera
{
public:
    Camera();
    ~Camera();

private:
    Transform m_transform;
};

}
