package org.yourorghere;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.media.opengl.GLAutoDrawable;

public class Listener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

    static Point location = new Point(0, 0);

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Data.iterate = !Data.iterate;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Data.showPheromone = !Data.showPheromone;
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            Data.showBestChain = !Data.showBestChain;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            Data.showAnts = !Data.showAnts;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Data.clear = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            Data.inverse = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int sign = e.getWheelRotation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                Data.newPoint = true;
                break;
            case 3:
                Setting.readData();
                ACO.setting.show();
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        location = e.getPoint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void display(GLAutoDrawable drawable) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
