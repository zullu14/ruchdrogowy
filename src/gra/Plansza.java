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
    private int pas = 2;
    private int predkosc = 20;
    private int punkty = 0;
    private int level = 1;
    private Font f;

    Plansza() {
        super();
        f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                s = ke.paramString();
                if(ke.getKeyCode() == 37) {    // lewa strzałka
                    pas--;
                    if (pas < 0) pas = 0;
                    punkty++;
                }
                else if(ke.getKeyCode() == 39) {  // prawa strzałka
                    pas++;
                    if (pas > 2) pas = 2;
                    punkty++;
                }
                else if(ke.getKeyCode() == 38) { // strzałka w górę
                    predkosc++;
                    if (predkosc > 160) predkosc = 160;    // zmienić tak, żeby bez dodawania gazu tracił prędkość z czasem
                }
                else if(ke.getKeyCode() == 40) { //strzałka w dół
                    predkosc--;
                    if (predkosc < 1) predkosc = 1;
                }
                else punkty -= 1;

                repaint();
            }
        });
    } //Plansza()

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension wym = this.getSize();

        /* Wczytanie obrazu tła */
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            s = "nie wczytano tła";
        }
        g2.drawImage(background, 0, 0, null);


        /* Wczytanie obrazu autka */
        BufferedImage autko = null;
        try {
            autko = ImageIO.read(new File("autko.png"));
        } catch (IOException e) {
            s = "nie wczytano autka";
        }
        int autko_w = autko.getWidth(null);
        int autko_h = autko.getHeight(null);

        if (pas == 0) { //pas lewy
            g2.drawImage(autko,wym.width/2 -autko_w -80, wym.height -autko_h -predkosc+20, null);
        }
        else if (pas == 1) { //pas środkowy
            g2.drawImage(autko, wym.width / 2 + -60, wym.height - autko_h -predkosc+20, null);
        }
        else if (pas == 2) { //pas prawy
            g2.drawImage(autko, wym.width / 2 + 60, wym.height - autko_h -predkosc+20, null);
        }

        /* Wczytywanie obrazów znaków drogowych */
        BufferedImage znak = null;
        try {
            znak = ImageIO.read(new File("znak"+1+".png"));
        } catch (IOException e) {
            s = "nie wczytano znaku";
        }
        g2.drawImage(znak, 900, 100, null);


        g2.drawString("PUNKTY: "+punkty, 100, 100);
        g2.drawString("PRĘDKOŚĆ: "+predkosc, 100, 200);
    } //paint

    public void resetLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        //wyzerowanie timera
    }

    public void freezeLevel() {
        // zatrzymanie timera
    }

    public void resumeLevel() {
        // puszczenie timera dalej
    }
}
