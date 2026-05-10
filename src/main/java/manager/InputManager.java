package manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputManager extends MouseAdapter {

    public int mouseX;

    public int mouseY;

    public boolean brushing = false;

    @Override
    public void mouseDragged(MouseEvent e) {

        mouseX = e.getX();

        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseX = e.getX();

        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {

        brushing = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        brushing = false;
    }
}