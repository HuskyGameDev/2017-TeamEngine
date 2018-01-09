package oasis.graphics.texture;

import java.nio.ByteBuffer;

import oasis.graphics.Texture.Codec;
import oasis.math.Vector4;

public class Rgba32fCodec implements Codec {

    @Override
    public void setPixelInt(ByteBuffer buffer, int rgba) {
        buffer.putFloat(TextureUtil.getRgbaRed(rgba)); 
        buffer.putFloat(TextureUtil.getRgbaGreen(rgba)); 
        buffer.putFloat(TextureUtil.getRgbaBlue(rgba)); 
        buffer.putFloat(TextureUtil.getRgbaAlpha(rgba)); 
    }

    @Override
    public int getPixelInt(ByteBuffer buffer) {
        return TextureUtil.getRgbaFromVector4(buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat()); 
    }

    @Override
    public void setPixelVector4(ByteBuffer buffer, Vector4 in) {
        buffer.putFloat(in.x); 
        buffer.putFloat(in.y); 
        buffer.putFloat(in.z); 
        buffer.putFloat(in.w); 
    }

    @Override
    public void getPixelVector4(ByteBuffer buffer, Vector4 out) {
        out.x = buffer.getFloat(); 
        out.y = buffer.getFloat(); 
        out.z = buffer.getFloat(); 
        out.w = buffer.getFloat(); 
    }

}
