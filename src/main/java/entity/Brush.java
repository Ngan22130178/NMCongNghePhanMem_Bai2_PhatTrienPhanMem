package entity;

import java.awt.*;

public class Brush {

    private int x = 100;

    private int y = 100;

    public void setPosition(int x, int y) {

        this.x = x;

        this.y = y;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(new Color(139,69,19));

        g2.fillRect(x, y, 60, 18);

        g2.setColor(new Color(100,149,237));

        g2.fillRect(x + 50, y, 20, 18);
    }
}