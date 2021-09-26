import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTPanel extends JPanel implements MouseListener {
    final int SCREEN_WIDTH = 600;
    final int UNIT_DIMENSION = 20; // Makes a n x n board
    final int UNIT_SIZE = SCREEN_WIDTH / UNIT_DIMENSION;
    final int GAME_UNITS = SCREEN_WIDTH * SCREEN_WIDTH / UNIT_SIZE / UNIT_SIZE;
    final int[] x = new int[SCREEN_WIDTH / UNIT_SIZE];
    final int[] y = new int[SCREEN_WIDTH / UNIT_SIZE];
    int[] TTT = new int[GAME_UNITS];
    int win = 3; // 3 = game is playing, 2 = tie, 1 = Player 1 wins, 0 = Player 2 wins.
    private int round = 0;
    private boolean check = true;

    TTTPanel() {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_WIDTH));
        this.setBackground(Color.black);
        this.setVisible(true);
        this.setFocusable(true);
        System.out.println(TTT.length);
        for (int i = 0; i < TTT.length; i++) {
            TTT[i] = 2;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        for (int i = 1; i <= UNIT_DIMENSION; i++) {
            g2.drawLine(UNIT_SIZE * i, 0, UNIT_SIZE * i, SCREEN_WIDTH);
            g2.drawLine(0, UNIT_SIZE * i, SCREEN_WIDTH, UNIT_SIZE * i);
        }
        for (int i = 0; i < TTT.length; i++) {
            if (TTT[i] != 2) {
                drawShape(g, ((i % UNIT_DIMENSION + 1) * 2 - 1) * UNIT_SIZE / 2,
                        (((i / UNIT_DIMENSION) + 1) * 2 - 1) * UNIT_SIZE / 2, TTT[i]);
            }
        }
        winScreen(g);
    }

    public void drawShape(Graphics g, int x, int y, int turn) {
        if (turn == 0) {
            cross(g, x, y);
        } else {
            circle(g, x, y);
        }
    }

    public void cross(Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine((UNIT_SIZE/8) + x - (UNIT_SIZE / 2), (UNIT_SIZE/8) + y - (UNIT_SIZE / 2), -(UNIT_SIZE/8) + x + (UNIT_SIZE / 2),
                -(UNIT_SIZE/8) + y + (UNIT_SIZE / 2));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(-(UNIT_SIZE/8) + x + (UNIT_SIZE / 2), (UNIT_SIZE/8) + y - (UNIT_SIZE / 2), (UNIT_SIZE/8) + x - (UNIT_SIZE / 2),
                -(UNIT_SIZE/8) + y + (UNIT_SIZE / 2));
    }

    public void circle(Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval((UNIT_SIZE/8) + x - (UNIT_SIZE / 2), (UNIT_SIZE/8) + y - (UNIT_SIZE / 2), UNIT_SIZE - (UNIT_SIZE/4), UNIT_SIZE - (UNIT_SIZE/4));
    }

    public void turn() {
        // 0 = player 1, 1 = player 2
        round++;
        round %= 2;
    }

    public void checkDub() {
        int index;
        for (int i = 0; i < UNIT_DIMENSION; i++) {
            for (int j = 2; j < UNIT_DIMENSION - 2; j++) {
                index = UNIT_DIMENSION * i + j;
                if(TTT[index - 2] == TTT[index - 1] &&
                   TTT[index - 1] == TTT[index] &&
                   TTT[index] == TTT[index + 1] &&
                   TTT[index + 1] == TTT[index + 2] &&
                   TTT[index] != 2){
                    win = TTT[index];
                    System.out.println("ROW");
                }
            }
        }
        for (int i = 0; i < UNIT_DIMENSION; i++) {
            for (int j = 0; j < UNIT_DIMENSION - 4; j++) {
                index = UNIT_DIMENSION * (2 + j) + i;
                if(TTT[index - 2 * UNIT_DIMENSION] == TTT[index - 1 * UNIT_DIMENSION] &&
                   TTT[index - 1 * UNIT_DIMENSION] == TTT[index] &&
                   TTT[index] == TTT[index + 1 * UNIT_DIMENSION] &&
                   TTT[index + 1 * UNIT_DIMENSION] == TTT[index + 2 * UNIT_DIMENSION] &&
                   TTT[index] != 2){
                    win = TTT[index];
                    System.out.println("COLUMN");
                }
            }
        }
        for (int i = 0; i < UNIT_DIMENSION - 4; i++) {
            for (int j = 2; j < UNIT_DIMENSION - 2; j++) {
                index = UNIT_DIMENSION * 2 + UNIT_DIMENSION * i + j;
                if (TTT[index] == TTT[index - UNIT_DIMENSION - 1] && 
                    TTT[index] == TTT[index - 2 * UNIT_DIMENSION - 2] && 
                    TTT[index] == TTT[index + UNIT_DIMENSION + 1] && 
                    TTT[index] == TTT[index + 2 * UNIT_DIMENSION + 2] &&
                    TTT[index] != 2) {
                    win = TTT[index];
                    System.out.println("DIAGONAL");
                }
                if (TTT[index] == TTT[index - UNIT_DIMENSION + 1] && 
                    TTT[index] == TTT[index - 2 * UNIT_DIMENSION + 2] && 
                    TTT[index] == TTT[index + UNIT_DIMENSION - 1] && 
                    TTT[index] == TTT[index + 2 * UNIT_DIMENSION - 2] &&
                    TTT[index] != 2) {
                    win = TTT[index];
                    System.out.println("DIAGONAL");
                }
            }
        }
    }

    public void winScreen(Graphics g) {
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics metric = getFontMetrics(g.getFont());
        if (check) {
            asdfasdf: if (true) {
                for (int i = 0; i < 9; i++) {
                    if (TTT[i] == 2) {
                        break asdfasdf;
                    }
                }
                g.setColor(new Color(0xCBC3E3));
                g.fillRect(0, 0, 600, 600);
                g.setColor(new Color(0x301934));
                g.drawString("Tie!", (SCREEN_WIDTH - metric.stringWidth("Tie!")) / 2,
                        300 - (g.getFont().getSize() / 2));
            }
            switch (win) {
                case 0:
                    g.setColor(new Color(0xFF7F7F));
                    g.fillRect(0, 0, 600, 600);
                    g.setColor(new Color(0x530000));
                    g.drawString("Player 1 Wins!", (SCREEN_WIDTH - metric.stringWidth("Player 1 Wins!")) / 2,
                            300 - (g.getFont().getSize() / 2));
                    check = false;
                    break;
                case 1:
                    g.setColor(new Color(0xADD8E6));
                    g.fillRect(0, 0, 600, 600);
                    g.setColor(new Color(0x00008B));
                    g.drawString("Player 2 Wins!", (SCREEN_WIDTH - metric.stringWidth("Player 2 Wins!")) / 2,
                            300 - (g.getFont().getSize() / 2));

                    check = false;
                    break;
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (check) {
            int x = (int) (e.getX() / UNIT_SIZE);
            int y = (int) (e.getY() / UNIT_SIZE);
            int index = x + UNIT_DIMENSION * y;

            if (TTT[index] == 2) {
                System.out.println("Player " + (round + 1) + ": Cell" + index);
                TTT[index] = round;
                turn();
            }

            System.out.println(TTT[index]);

            repaint();
            checkDub();
            System.out.println("Win: " + win);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
