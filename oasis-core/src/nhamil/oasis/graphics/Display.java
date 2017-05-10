package nhamil.oasis.graphics;

public interface Display {

    String getTitle();
    void setTitle(String title);
    
    int getWidth();
    int getHeight();
    void setSize(int width, int height);
    
    void show();
    void hide();
    boolean isVisible();
    
    boolean isResizable();
    void setResizable(boolean resize);
    
    boolean isFullscreen();
    boolean canFullscreen();
    void setFullscreen(boolean full);
    
    boolean isClosed();
    
}
