package nhamil.oasis.core;

import nhamil.oasis.core.test.TestEngine;

public class Config {

    public Class<? extends Engine> engine = TestEngine.class;
    public float fps = 60.0f;
    public float ups = 60.0f;
    
}
