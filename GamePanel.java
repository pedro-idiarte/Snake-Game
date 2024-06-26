import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // GGAME STATES

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int playState1 = 2;
    public final int playState2 = 3;
    public final int pauseState = 4;
    public final int gameOverState = 5;

    // TITLE STATE
    int commandNum = 0;

    // SCREEN PAINEL

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    // UNIT SIZES AND DELAY

    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int pointsEaten=-1;
    int pointX;
    int pointY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        gameState = titleState;

        if (gameState == titleState) {
            gameState = titleState;
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
            
        }

        if (gameState == playState) {
            gameState = playState;
            newPoint();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (gameState == titleState) {
            drawTitleScreen(g);
        }

        
        if (gameState == playState) {

            if (running) {

                /*
                 * GRID
                 * for (int i=0 ;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                 * g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                 * g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
                 * }
                 */

            

                g.setColor(Color.red);
                g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(45, 180, 0));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                        // Random Colors for Snake
                        // g.setColor(new
                        // Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        // g.setColor(new Color((255),(100),(108)));

                        
                    }
                }
                g.setColor(Color.red);
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + pointsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + pointsEaten)) / 2,
                        g.getFont().getSize());
            } else {
                gameState = gameOverState;
                gameOver(g);

            }

            if (gameState == pauseState) {
                drawPauseSreen(g);
            }

        }

    }

    public void drawTitleScreen(Graphics g) {
        if (gameState == titleState) {

            /*
             * //SHADOW OF TEXT
             * g.setColor(Color.gray);
             * g.setFont(new Font("Ink Free", Font.BOLD, 90));
             * FontMetrics metrics3 = getFontMetrics(g.getFont());
             * g.drawString("Snake Game", (SCREEN_WIDTH -
             * metrics3.stringWidth("Snake Game")) / 2, SCREEN_HEIGHT /3 );
             */
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD, 90));
            FontMetrics metrics4 = getFontMetrics(g.getFont());
            g.drawString("Snake Game", (SCREEN_WIDTH - metrics4.stringWidth("Snake Game")) / 2, SCREEN_HEIGHT / 4);

            // NO BORDER GAME MODE
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics5 = getFontMetrics(g.getFont());
            g.drawString("No Border Mode", (SCREEN_WIDTH - metrics5.stringWidth("No Border Mode")) / 2,
                    SCREEN_HEIGHT / 1 - 200);
            if (commandNum == 0) {
                g.drawString(">", SCREEN_WIDTH / 2 - 180, SCREEN_HEIGHT / 1 - 200);

            }
            // BORDER GAME MODE
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics6 = getFontMetrics(g.getFont());
            g.drawString("Border Mode", (SCREEN_WIDTH - metrics6.stringWidth("Border Mode")) / 2,
                    SCREEN_HEIGHT / 1 - 150);
            if (commandNum == 1) {
                g.drawString(">", SCREEN_WIDTH / 2 - 155, SCREEN_HEIGHT / 1 - 150);

            }
            // BUFF GAME MODE
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics7 = getFontMetrics(g.getFont());
            g.drawString("Buffed Mode", (SCREEN_WIDTH - metrics7.stringWidth("Buffed Mode")) / 2,
                    SCREEN_HEIGHT / 1 - 100);
            if (commandNum == 2) {
                g.drawString(">", SCREEN_WIDTH / 2 - 155, SCREEN_HEIGHT / 1 - 100);

            }
            // QUIT
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics8 = getFontMetrics(g.getFont());
            g.drawString("Quit", (SCREEN_WIDTH - metrics8.stringWidth("Quit")) / 2, SCREEN_HEIGHT / 1 - 50);
            if (commandNum == 3) {
                g.drawString(">", SCREEN_WIDTH / 2 - 80, SCREEN_HEIGHT / 1 - 50);

            }

            drawSnakeTitleState(g);

        }

    }

    public void drawPauseSreen(Graphics g) {

        // SCORE
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + pointsEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + pointsEaten)) / 2,
                g.getFont().getSize());

        // PAUSE SCREEN
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("PAUSED", (SCREEN_WIDTH - metrics2.stringWidth("PAUSED")) / 2, SCREEN_HEIGHT / 2);

        // TIMER STOP
        timer.stop();

    }

    public void newPoint() {
        
        pointX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        pointY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        
    }

    public void move() {

        if (gameState == playState) {

            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            switch (direction) {
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }
        }
    }

    public void checkPoint() {
        if ((x[0] == pointX) && (y[0] == pointY)) {
            bodyParts++;
            pointsEaten++;
            newPoint();
        }

    }

    public void checkCollisions() {

        if (gameState == playState) {

            // CHECK IF HEAD COLIDES WITH BODY

            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }
            // CHECKS IF HEAD COLIDES WITH LEFT BORDER
            if (x[0] < 0) {
                running = false;
            }
            // CHECKS IF HEAD COLIDES WITH RIGHT BORDER
            if (x[0] > SCREEN_WIDTH) {
                running = false;
            }
            // CHECKS IF HEAD COLIDES WITH TOP BORDER
            if (y[0] < 0) {
                running = false;
            }
            // CHECKS IF HEAD COLIDES WITH BOTTOM BORDER
            if (y[0] > SCREEN_HEIGHT) {
                running = false;
            }
            if (!running) {
                timer.stop();
            }
        }
    }

    public void drawSnakeTitleState(Graphics g) {

        if (gameState == titleState) {
            // FRUIT TITLESTATE
            g.setColor(Color.red);
            g.fillOval(370, 260, UNIT_SIZE, UNIT_SIZE);

            // SNAKE TITLESTATE
            g.setColor(Color.green);
            g.fillRect(250, 260, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(45, 180, 0));
            g.fillRect(225,260, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(45, 180, 0));
            g.fillRect(200,260, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(45, 180, 0));
            g.fillRect(175, 260, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(45, 180, 0));
            g.fillRect(150, 260, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(45, 180, 0));
            g.fillRect(125, 260, UNIT_SIZE, UNIT_SIZE);
        }

    }

    public void gameOver(Graphics g) {
        if (gameState == gameOverState) {

            // SCORE
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + pointsEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + pointsEaten)) / 2,
                    g.getFont().getSize());

            // GAMEOVER TEXT
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

            //BACK TO MENU

            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkPoint();
            checkCollisions();
        }
        repaint();
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameState == titleState) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            commandNum--;
                            if (commandNum < 0) {
                                commandNum = 3;
                            }
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            commandNum++;
                            if (commandNum > 3) {
                                commandNum = 0;
                            }
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        if (commandNum == 0) {
                            gameState = titleState;
                        }
                        if (commandNum == 1) {
                            gameState=playState;
                            
                        }
                        if (commandNum == 2) {
                            gameState = titleState;
                        }
                        if (commandNum == 3) {
                            System.exit(0);
                        }
                }
            }
            if (gameState == playState) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (gameState != gameOverState) {
                            if (gameState == playState) {
                                gameState = pauseState;
                                drawPauseSreen(getGraphics());
                            }
                        }
                        break;
                }
            } 
            else if (gameState == pauseState) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameState = playState;
                    timer.start();
                }
            }
            /*if(gameState == gameOverState){
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGame();
                }
            }*/

        }

    }
}