package entity;

import java.awt.*;
import java.util.Random;

public class Cat {

    private boolean looking = false;

    private int timer = 0;

    Random random = new Random();

    public void update() {

        timer++;

        if(timer > 90) {

            int chance = random.nextInt(100);

            if(chance < 40) {

                looking = !looking;
            }

            timer = 0;
        }
    }

    public boolean isLooking() {

        return looking;
    }

    public void reset() {

        looking = false;

        timer = 0;
    }

    public void draw(Graphics2D g2) {

        if(looking) {

            g2.setColor(new Color(255,120,120));
        }
        else {

            g2.setColor(new Color(180,180,180));
        }

        g2.fillOval(250, 150, 300, 300);

        g2.setColor(Color.BLACK);

        g2.fillOval(340, 260, 25, 25);

        g2.fillOval(440, 260, 25, 25);

        if(looking) {

            g2.drawString("ANGRY", 360, 130);
        }
        else {

            g2.drawString("HAPPY", 360, 130);
        }
    }
}