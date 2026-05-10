package game;

import entity.Brush;
import entity.Cat;
import manager.InputManager;
import manager.ScoreManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

    public final int WIDTH = 800;
    public final int HEIGHT = 600;

    Thread gameThread;

    Cat cat = new Cat();

    Brush brush = new Brush();

    InputManager input = new InputManager();

    ScoreManager scoreManager = new ScoreManager();

    GameState gameState = GameState.MENU;

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setBackground(new Color(255,245,240));

        this.setDoubleBuffered(true);

        this.addMouseListener(input);

        this.addMouseMotionListener(input);

        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if(gameState == GameState.MENU) {

                    if(e.getKeyCode() == KeyEvent.VK_SPACE) {

                        gameState = GameState.PLAYING;
                    }
                }

                if(gameState == GameState.GAME_OVER) {

                    if(e.getKeyCode() == KeyEvent.VK_R) {

                        restartGame();
                    }
                }
            }
        });
    }

    public void startGameThread() {

        gameThread = new Thread(this);

        gameThread.start();
    }

    @Override
    public void run() {

        while(gameThread != null) {

            update();

            repaint();

            try {
                Thread.sleep(16);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if(gameState == GameState.PLAYING) {

            cat.update();

            brush.setPosition(input.mouseX, input.mouseY);

            if(input.brushing) {

                if(cat.isLooking()) {

                    gameState = GameState.GAME_OVER;
                }
                else {

                    scoreManager.addScore(1);
                }
            }
        }
    }

    public void restartGame() {

        scoreManager.reset();

        cat.reset();

        gameState = GameState.PLAYING;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(gameState == GameState.MENU) {

            drawMenu(g2);
        }

        if(gameState == GameState.PLAYING) {

            drawGameplay(g2);
        }

        if(gameState == GameState.GAME_OVER) {

            drawGameOver(g2);
        }
    }

    public void drawMenu(Graphics2D g2) {

        g2.setColor(Color.BLACK);

        g2.setFont(new Font("Arial", Font.BOLD, 50));

        g2.drawString("Brush Jjaemu", 220, 200);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));

        g2.drawString("Press SPACE to Start", 240, 320);
    }

    public void drawGameplay(Graphics2D g2) {

        cat.draw(g2);

        brush.draw(g2);

        drawHUD(g2);
    }

    public void drawHUD(Graphics2D g2) {

        g2.setFont(new Font("Arial", Font.BOLD, 25));

        g2.setColor(Color.BLACK);

        g2.drawString("Score: " + scoreManager.getScore(), 20, 40);

        if(cat.isLooking()) {

            g2.setColor(Color.RED);

            g2.drawString("STOP! CAT IS LOOKING", 480, 40);
        }
        else {

            g2.setColor(new Color(0,150,0));

            g2.drawString("SAFE TO BRUSH", 540, 40);
        }
    }

    public void drawGameOver(Graphics2D g2) {

        g2.setColor(Color.RED);

        g2.setFont(new Font("Arial", Font.BOLD, 60));

        g2.drawString("GAME OVER", 190, 220);

        g2.setFont(new Font("Arial", Font.PLAIN, 35));

        g2.drawString(
                "Final Score: " + scoreManager.getScore(),
                250,
                320
        );

        g2.drawString(
                "Press R to Restart",
                240,
                400
        );
    }
}