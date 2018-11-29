package gra;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Plansza extends Canvas {

    public String s="halko";
    private int pas = 1;
    private Font f;

    Plansza() {
        super();
        f = new Font("Calibri", Font.BOLD, 20);
        setFont(f);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                s = ke.paramString();
                if(ke.getKeyCode() == 37) {
                    s = "lewa strzałka";
                    pas = 0;
                }
                        else if(ke.getKeyCode() == 39) {
                            s = "prawa strzałka";
                            pas = 1;
                }
                        else s = "debil";
                repaint();
            }
        });
    } //Plansza()

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension wym = new Okno("okienko").getSize();

        g2.setStroke(new BasicStroke(6.0f));
        g2.setColor(Color.blue);
        Line2D linia = new Line2D.Double(wym.getWidth()/2, 0,wym.getWidth()/2, wym.getHeight());
        g2.draw(linia);

        /* Create a BufferedImage */
        BufferedImage autko = null;
        try {
            autko = ImageIO.read(new File("autko.jpg"));
        } catch (IOException e) {
            s = "nie wczytano autka";
        }
        int autko_w = autko.getWidth(null);
        int autko_h = autko.getHeight(null);

        if(pas == 0) { // wybrano strzałkę w lewo
            g2.drawImage(autko,wym.width/2 -autko_w -10, wym.height -autko_h -50, null);
        }
        else if(pas == 1) // wybrano strzałkę w prawo
            g2.drawImage(autko, wym.width/2 +10, wym.height -autko_h -50, null);

        g2.drawString(s, 100, 100);
    }
}
