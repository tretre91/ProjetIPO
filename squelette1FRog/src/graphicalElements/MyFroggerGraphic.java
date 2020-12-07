package graphicalElements;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class MyFroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
    private ArrayList<Element> elementsToDisplay;
    private int pixelByCase = 16;
    private int width;
    private int height;
    private IFrog frog;
    private JFrame frame;

    public MyFroggerGraphic(int width, int height) {
        this.width = width;
        this.height = height;
        elementsToDisplay = new ArrayList<>();

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

        JFrame frame = new JFrame("Frogger");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2d);

        for (Element e : elementsToDisplay) {
            g2d.setColor(e.color);
            if(e.color == Color.orange) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillOval(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            } else if (e.color == Game.transitionLaneColor || e.color == Game.waterColor) {
                g2d.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase);
            } else {
                g2d.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
            }
        }
        if (frog != null && frog.getScore() != -1) {
            g2d.setFont(new Font("Verdana", Font.PLAIN, 15));
            g2d.setColor(Color.white);
            g2d.drawString("Score : " + frog.getScore(), 0, 15);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> frog.move(Direction.up);
            case KeyEvent.VK_DOWN -> frog.move(Direction.down);
            case KeyEvent.VK_LEFT -> frog.move(Direction.left);
            case KeyEvent.VK_RIGHT -> frog.move(Direction.right);
        }
    }

    public void clear() {
        this.elementsToDisplay.clear();
    }

    public void add(Element e) {
        this.elementsToDisplay.add(e);
    }

    public void setFrog(IFrog frog) {
        this.frog = frog;
    }

    public void endGameScreen(String s) {
        String[] lines = s.split("\n");
        Font font = new Font("Verdana", Font.BOLD, 20);
        int startingPos = (lines.length * (font.getSize() + 2)) / -2;
        frame.remove(this);
        for (String str: lines) {
            JLabel label = new JLabel(str);
            label.setFont(font);
            label.setLocation(0, startingPos);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setSize(this.getSize());
            frame.getContentPane().add(label);
            startingPos += font.getSize() + 2;
        }
        frame.repaint();
    }

}
