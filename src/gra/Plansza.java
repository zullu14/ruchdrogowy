package gra;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.Timer;

public class Plansza extends Canvas {

    public String s="halko";
    private int pas = 2;
    private int predkosc = 20;
    private int punkty = 0;
    private int level = 1;
    private int nrZnaku = 1;
    private int timeCounter = 0;
    private Font f;

    /* Swing Timer */
    Timer timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            timeCounter++;
            if(timeCounter == 40) {
                if(nrZnaku == 1) nrZnaku = 2;
                else nrZnaku = 1;
                timeCounter = 0;
                repaint();
            }
        }
    });

    Plansza() {
        super();
        f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);

        timer.setInitialDelay(1000);

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
                    if (predkosc < 0) predkosc = 0;
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
            znak = ImageIO.read(new File("znak"+nrZnaku+".png"));
        } catch (IOException e) {
            s = "nie wczytano znaku";
        }
        g2.drawImage(znak, 900, 100, null);


        g2.drawString("PUNKTY: "+punkty, 100, 100);
        g2.drawString("PRĘDKOŚĆ: "+predkosc, 100, 200);
    } //paint

    public void firstLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timer.start();
    }

    public void resetLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timer.restart();
    }

    public void freezeLevel() {
        this.timer.stop();
    }

    public void resumeLevel() {
        this.timer.start();
    }
}
