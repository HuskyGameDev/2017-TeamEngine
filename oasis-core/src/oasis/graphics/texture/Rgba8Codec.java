package oasis.graphics.texture;

import java.nio.ByteBuffer;

import oasis.graphics.Texture; 
import oasis.math.Vector4;

public class Rgba8Codec implements Texture.Codec {

    @Override
    public void setPixelInt(ByteBuffer buffer, int rgba) {
        buffer.putInt(rgba); 
    }

    @Override
    public int getPixelInt(ByteBuffer buffer) {
        return buffer.getInt(); 
    }

    @Override
    public void setPixelVector4(ByteBuffer buffer, Vector4 in) {
        setPixelInt(buffer, TextureUtil.getRgbaFromVector4(in)); 
    }

    @Override
    public void getPixelVector4(ByteBuffer buffer, Vector4 out) {
        TextureUtil.getVector4FromRgba(getPixelInt(buffer), out); 
    }

}
