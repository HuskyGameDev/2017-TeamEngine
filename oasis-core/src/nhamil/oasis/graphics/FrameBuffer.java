package nhamil.oasis.graphics;

public interface FrameBuffer {

    public enum AttachmentType {
        None, 
        Texture, 
        RenderBuffer;
    }
    
    void dispose();
    
    boolean isValid();
    
    void setClearColor(ColorRgba color);
    void clear();
    
    void bind();
    void unbind();
    
    int getWidth();
    int getHeight();
    void setSize(int width, int height);
    
    void setColorAttachmentType(AttachmentType type);
    void setDepthAttachmentType(AttachmentType type);
    
    AttachmentType getColorAttachmentType();
    AttachmentType getDepthAttachmentType();
    
    Texture getColorTexture();
    Texture getDepthTexture();
    
}
