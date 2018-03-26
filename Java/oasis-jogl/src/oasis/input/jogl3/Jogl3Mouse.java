package oasis.input.jogl3;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MouseInfo;
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
    
    private final Object lock = new Object(); 
    
    private Jogl3Window display; 
    
    private boolean[] down; 
    private int[] buttons; 
    private int dx, dy; 
    private int x, y; 
    private ScrollDirection dir = ScrollDirection.NONE; 
    
    private Robot robot; 
    
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
        x = y = dx = dy = 0; 
    }
    
    public void setCursorVisible(boolean visible) {
        display.getCanvas().setCursor(visible ? basicCursor : blankCursor); 
    }
    
    public void update() {
        synchronized (lock) {
            for (int i = 0; i < MAX_BUTTONS; i++) {
                if (down[i]) {
                    buttons[i]++; 
                }
                else {
                    buttons[i] = 0; 
                }
            }
            Point p = MouseInfo.getPointerInfo().getLocation(); 
            int curX = p.x - display.getX(); 
            int curY = p.y - display.getY(); 
            dx = curX - x; 
            dy = curY - y; 
            x = curX; 
            y = curY; 
            dir = curDir; 
            
            curDir = ScrollDirection.NONE; 
        }
    }
    
//    private void resetPosition() {
//        lastX = x; 
//        lastY = y; 
//    }
    
    @Override
    public void setPosition(float newX, float newY) {
        synchronized (lock) {
            Point p = MouseInfo.getPointerInfo().getLocation(); 
            int curX = p.x - display.getX(); 
            int curY = p.y - display.getY(); 
            int oldX = x; 
            int oldY = x; 
            
            this.x = (int)newX; 
            this.y = (int)(display.getHeight() - (int)newY - 1); 
            if (display.getCanvas().hasFocus()) robot.mouseMove(display.getX() + this.x, display.getY() + this.y); 
//            resetPosition(); 
            
            this.dx = curX - oldX; 
            this.dy = curY - oldY; 
            
//            lastX += -(curX - oldX); 
//            lastY += -(curY - oldY); 
        }
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
        return dx; 
    }

    @Override
    public float getDy() {
        return dy; 
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
        synchronized (lock) {
            down[e.getButton()] = true; 
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (lock) {
            down[e.getButton()] = false; 
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        synchronized (lock) {
//            curX = e.getX(); 
//            curY = e.getY(); 
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (lock) {
//            curX = e.getX(); 
//            curY = e.getY(); 
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        synchronized (lock) {
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

}
