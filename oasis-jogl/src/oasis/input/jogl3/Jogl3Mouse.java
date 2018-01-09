package oasis.input.jogl3;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import oasis.core.OasisException;
import oasis.core.jogl3.Jogl3Window;
import oasis.input.Mouse;

public class Jogl3Mouse implements Mouse, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int MAX_BUTTONS = 32; 
    
    private Jogl3Window display; 
    
    private boolean[] down; 
    private int[] buttons; 
    private int x, y; 
    private int lastX, lastY; 
    private ScrollDirection dir = ScrollDirection.NONE; 
    
    private Robot robot; 
    
    private int curX, curY; 
    private ScrollDirection curDir = ScrollDirection.NONE; 
    
    private Cursor basicCursor, blankCursor; 
    
    public Jogl3Mouse(Jogl3Window display) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new OasisException("AWT robot creation failed"); 
        } 
        
        basicCursor = display.getCanvas().getCursor(); 
        blankCursor = display.getCanvas().getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank"); 
        display.getCanvas().setCursor(null); 
        
        this.display = display; 
        down = new boolean[MAX_BUTTONS]; 
        buttons = new int[MAX_BUTTONS]; 
        x = y = lastX = lastY = 0; 
    }
    
    public void setCursorVisible(boolean visible) {
        display.getCanvas().setCursor(visible ? basicCursor : blankCursor); 
    }
    
    public void update() {
        for (int i = 0; i < MAX_BUTTONS; i++) {
            if (down[i]) {
                buttons[i]++; 
            }
            else {
                buttons[i] = 0; 
            }
        }
        lastX = x; 
        lastY = y; 
        x = curX; 
        y = curY; 
        dir = curDir; 
        
        curDir = ScrollDirection.NONE; 
    }
    
    private void resetPosition() {
        lastX = x; 
        lastY = y; 
    }
    
    @Override
    public void setPosition(float x, float y) {
        this.x = (int)x; 
        this.y = (int)(display.getHeight() - (int)y - 1); 
        if (display.getCanvas().hasFocus()) robot.mouseMove(display.getX() + this.x, display.getY() + this.y); 
        resetPosition(); 
    }

    @Override
    public void center() {
        setPosition(display.getWidth() / 2.0f, display.getHeight() / 2.0f); 
    }

    @Override
    public float getX() {
        return x; 
    }

    @Override
    public float getY() {
        return y; 
    }

    @Override
    public float getDx() {
        return x - lastX; 
    }

    @Override
    public float getDy() {
        return y - lastY; 
    }

    @Override
    public boolean isButtonDown(int button) {
        return buttons[button] > 0; 
    }

    @Override
    public boolean isButtonJustDown(int button) {
        return buttons[button] == 1; 
    }

    @Override
    public ScrollDirection getScroll() {
        return dir; 
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {
        down[e.getButton()] = true; 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        down[e.getButton()] = false; 
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        curX = e.getX(); 
        curY = e.getY(); 
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        curX = e.getX(); 
        curY = e.getY(); 
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int d = e.getWheelRotation(); 
        
        if (d < 0) {
            curDir = ScrollDirection.UP; 
        }
        else if (d > 0) {
            curDir = ScrollDirection.DOWN; 
        }
        else {
            curDir = ScrollDirection.NONE; 
        }
    }

}
