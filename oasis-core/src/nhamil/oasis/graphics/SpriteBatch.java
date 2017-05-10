package nhamil.oasis.graphics;

public interface SpriteBatch {

    int maxSprites();
    
    void setShader(Shader shader);
    
    void begin();
    void end();
    
    void drawText(SpriteFont font, ColorRgba color, String text, float x, float y);
    void drawText(SpriteFont font, ColorRgba color, String text, float x, float y, float originX, float originY, float scaleX, float scaleY, float rot, boolean flipX, boolean flipY);
    
    void draw(Sprite sprite);
    void draw(TextureRegion region, float x, float y, float w, float h);
    void draw(TextureRegion region, ColorRgba color, float x, float y, float w, float h);
    void draw(TextureRegion region, ColorRgba color, float x, float y, float w, float h, boolean flipX, boolean flipY);
    void draw(TextureRegion region, ColorRgba color, float x, float y, float w, float h, float originX, float originY, float scaleX, float scaleY, float rot, boolean flipX, boolean flipY);
    
}
