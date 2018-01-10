package oasis.graphics;

import java.nio.Buffer;

public interface NativeTexture {

    void upload(int x, int y, int z, int width, int height, int depth, Buffer data); 
    
    void release(); 
    
}
