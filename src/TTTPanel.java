import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTPanel extends JPanel implements MouseListener {
    final int SCREEN_DIMENSION = 600;
    final int NUM_ROW = 5;
    final int UNIT_SIZE = SCREEN_DIMENSION/NUM_ROW;
    int[] TTT = new int[NUM_ROW * NUM_ROW];
    int win = 3; // 3 = game is playing, 2 = tie, 1 = Player 1 wins, 0 = Player 2 wins.
    private int round = 0;
    private boolean check = true;

    TTTPanel() {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(SCREEN_DIMENSION, SCREEN_DIMENSION));
        this.setBackground(Color.black);
        this.setVisible(true);
        this.setFocusable(true);
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
        for (int i = 1; i <= (SCREEN_DIMENSION/UNIT_SIZE); i++) {
            g2.drawLine(UNIT_SIZE * i, 0, UNIT_SIZE * i, SCREEN_DIMENSION);
            g2.drawLine(0, UNIT_SIZE * i, SCREEN_DIMENSION, UNIT_SIZE * i);
        }

        for (int i = 0; i < TTT.length; i++) {
            if (TTT[i] != 2) {
                drawShape(g, ((i % NUM_ROW + 1) * 2 - 1) * (UNIT_SIZE/2), (((i / NUM_ROW) + 1) * 2 - 1) * (UNIT_SIZE/2), TTT[i]);
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
        for (int i = 0; i < NUM_ROW; i++) {
            for(int j = 0; j < NUM_ROW-2; j++){
                if (TTT[(NUM_ROW)*i+j] == TTT[(NUM_ROW)*i+j+1] && TTT[(NUM_ROW)*i+j+1] == TTT[(NUM_ROW)*i+j+2] && (TTT[(NUM_ROW)*i+j] != 2)) {
                    win = TTT[(NUM_ROW)*i+j];
                } 
                else if (TTT[(NUM_ROW)*j+i] == TTT[(NUM_ROW)*(j+1)+i] && TTT[(NUM_ROW)*(j+1)+i] == TTT[(NUM_ROW)*(j+2)+i] && TTT[(NUM_ROW)*j+i]!= 2) {
                    win = TTT[(NUM_ROW)*j+i];
                }
                //else if ()
            }
        }

        for(int i = 1; i < NUM_ROW-1; i++){
            for (int j=1; j< NUM_ROW-1; j++){
                if (TTT[NUM_ROW*i+j]==TTT[NUM_ROW*(i+1)+j-1] && TTT[NUM_ROW*i+j]==TTT[NUM_ROW*(i-1)+j+1] && TTT[NUM_ROW*i+j]!=2){
                    win = TTT[NUM_ROW*i+j];
                }
                if (TTT[NUM_ROW*i+j]==TTT[NUM_ROW*(i-1)+j-1] && TTT[NUM_ROW*i+j]==TTT[NUM_ROW*(i+1)+j+1] && TTT[NUM_ROW*i+j]!=2){
                    win = TTT[NUM_ROW*i+j];
                }
            }
        }
    }

    public void winScreen(Graphics g) {
        g.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics metric = getFontMetrics(g.getFont());
        if (check) {
            asdfasdf: if (true) {
                for (int i = 0; i < TTT.length; i++) {
                    if (TTT[i] == 2) {
                        break asdfasdf;
                    }
                }
                g.setColor(new Color(0xCBC3E3));
                g.fillRect(0, 0, 600, 600);
                g.setColor(new Color(0x301934));
                g.drawString("Tie!", (SCREEN_DIMENSION - metric.stringWidth("Tie!")) / 2,
                        300 - (g.getFont().getSize() / 2));
            }
            switch (win) {
                case 0:
                    g.setColor(new Color(0xFF7F7F));
                    g.fillRect(0, 0, 600, 600);
                    g.setColor(new Color(0x530000));
                    g.drawString("Player 1 Wins!", (SCREEN_DIMENSION - metric.stringWidth("Player 1 Wins!")) / 2,
                            300 - (g.getFont().getSize() / 2));
                    check = false;
                    break;
                case 1:
                    g.setColor(new Color(0xADD8E6));
                    g.fillRect(0, 0, 600, 600);
                    g.setColor(new Color(0x00008B));
                    g.drawString("Player 2 Wins!", (SCREEN_DIMENSION - metric.stringWidth("Player 2 Wins!")) / 2,
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
            int index = x + NUM_ROW * y;

            if (TTT[index] == 2) {
                System.out.println("Player " + (round + 1) + ": Cell" + index);
                TTT[index] = round;
                turn();
            }

            repaint();
            checkDub();
            System.out.println("Win: " + win);
        }
    }
    /*@Override
    public void mouseClicked(MouseEvent e) {
        if (check) {
            int x = (int) (e.getX() / UNIT_SIZE);
            int y = (int) (e.getY() / UNIT_SIZE);

            // int m = (int)((x - 1) / UNIT_SIZE) * 10 + (int)((y - 1) / UNIT_SIZE);
            String coords = "";
            for(int i = 0; i < TTT.length; i++){
                coords += (i % NUM_ROW + 1);
                coords += (i / NUM_ROW + 1);
            }
            // 3x3 Coords String coords = "112131122232132333";

            Boolean doTurn = true;
            big:for (int i = 0; i < TTT.length; i++) {
                if (x < Integer.parseInt(coords.substring(i * 2, i * 2 + 1))
                        && y < Integer.parseInt(coords.substring(i * 2 + 1, i * 2 + 2))) {
                    if (TTT[i] != 2){
                        doTurn = false;
                        break big;
                    }
                    System.out.println("Player " + (round + 1) + ": Cell" + i);
                    TTT[i] = round;
                    break big;
                }
            }
            if(doTurn){
                turn();
            }
            asdf: if (true) {
                if (x < 1 && y < 1) {
                    if (TTT[0] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 0");
                    TTT[0] = round;
                } else if (x < 2 && y < 1) {
                    if (TTT[1] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 1");
                    TTT[1] = round;
                } else if (x < 3 && y < 1) {
                    if (TTT[2] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 2");
                    TTT[2] = round;
                } else if (x < 1 && y < 2) {
                    if (TTT[3] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 3");
                    TTT[3] = round;
                } else if (x < 2 && y < 2) {
                    if (TTT[4] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 4");
                    TTT[4] = round;
                } else if (x < 3 && y < 2) {
                    if (TTT[5] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 5");
                    TTT[5] = round;
                } else if (x < 1 && y < 3) {
                    if (TTT[6] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 6");
                    TTT[6] = round;
                } else if (x < 2 && y < 3) {
                    if (TTT[7] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 7");
                    TTT[7] = round;
                } else if (x < 3 && y < 3) {
                    if (TTT[8] != 2)
                        break asdf;
                    System.out.println("Player " + (round + 1) + ": Cell 8");
                    TTT[8] = round;
                }
                turn();
            }
            repaint();
            checkDub();
            System.out.println("Win: " + win);
        }
    }*/

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
