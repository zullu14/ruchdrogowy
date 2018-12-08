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

    private String s="halko";
    private String komunikat = "start";
    private int pas = 2;
    private int predkosc = 20;
    private int punkty = 0;
    private int level = 1;
    private int nrZnaku = 1;
    private int timeCounter = 0;
    private int timeLimit = 500;
    private int wysKrzaka = 0;

    private BufferedImage background = null;
    private BufferedImage autko = null;
    private BufferedImage krzak = null;
    private BufferedImage znak = null;

    /* Swing Timer */
    private Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            timeCounter++;
            if(timeCounter % 100 == 0) {  // co 1 sekundę
                predkosc--;
                repaint(1000, 600, 250, 300); //narysuj tylko pole pod znakiem
            }

            if(predkosc<0) predkosc = 0;

            if (timeCounter == timeLimit) {
                // obsługa sprawdzenia poprawnej reakcji na znak
                if(new Znak().sprawdzZnak(nrZnaku, pas, predkosc)) {
                    punkty++;
                    komunikat = "Dobrze!";
                }
                else {
                    // obsługa popełnienia błędu: trzy szanse / koniec gry?
                    komunikat = "Błąd!";
                }
                repaint(1000, 600, 250, 300); //narysuj tylko pole pod znakiem
            } // warunek tC >= tL
            else if(timeCounter-timeLimit == 100) { // jedna sekunda przerwy między znakami
                nrZnaku = new Znak().losujZnak();
                timeCounter = 0;
                repaint(900, 100, 350, 800); //narysuj tylko nowy znak i pole pod nim
            }
            if (timeCounter % 5 == 0) {   // co 50 ms
                if (predkosc > 0) {
                    wysKrzaka+=3;
                }
                if (predkosc > 30) {
                    wysKrzaka+=3;
                }
                if (predkosc > 60) {
                    wysKrzaka+=3;
                }
                if (predkosc > 100) {
                    wysKrzaka+=3;
                }
                if (predkosc > 130) {
                    wysKrzaka+=3;
                }
                repaint(0, 0, 450, 900); //narysuj tylko obszar krzaków
                if (wysKrzaka >= 900) wysKrzaka = 0;
            }  // koniec akcji co 50 ms
        } // time action event
    });

    Plansza() {
        super();
        Font f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);

        /*   WCZYTANIE OBRAZÓW   */

        /* Wczytanie obrazu tła */
        try {
            background = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            s = "nie wczytano tła";
        }

        /* Wczytanie obrazu autka */
        try {
            autko = ImageIO.read(new File("autko.png"));
        } catch (IOException e) {
            s = "nie wczytano autka";
        }

        /*Wczytanie obrazu krzaka */
        try {
            krzak = ImageIO.read(new File("krzak.png"));
        } catch (IOException e) {
            s = "nie wczytano krzaka";
        }

        timer.setInitialDelay(1000);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                s = ke.paramString();
                if(ke.getKeyCode() == 37) {    // lewa strzałka
                    pas--;
                    if (pas < 0) pas = 0;
                    //punkty++;
                }
                else if(ke.getKeyCode() == 39) {  // prawa strzałka
                    pas++;
                    if (pas > 2) pas = 2;
                    //punkty++;
                }
                else if(ke.getKeyCode() == 38) { // strzałka w górę
                    predkosc++;
                    if (predkosc > 160) predkosc = 160;    // zmienić tak, żeby bez dodawania gazu tracił prędkość z czasem
                }
                else if(ke.getKeyCode() == 40) { //strzałka w dół
                    predkosc--;
                    if (predkosc < 0) predkosc = 0;
                }
                //else punkty -= 1;

                repaint(450, 600, 400, 300);
            }
        });
    } //Plansza()


    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension wym = this.getSize(); //przeniesione na zewnątrz metody

        g2.drawImage(background, 0, 0, null);

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

        /* Rysowanie krzaka */

        int wysKrzaka2 = wysKrzaka+200;
        if(wysKrzaka>=700) wysKrzaka2 = wysKrzaka-700;
        int wysKrzaka3 = wysKrzaka+600;
        if(wysKrzaka>=300) wysKrzaka3 = wysKrzaka-300;
        g2.drawImage(krzak, 80, wysKrzaka, null);
        g2.drawImage(krzak, 190, wysKrzaka2, null);
        g2.drawImage(krzak, 300, wysKrzaka, null);
        g2.drawImage(krzak, 100, wysKrzaka3, null);

        /* Wczytywanie obrazów znaków drogowych */
        try {
            znak = ImageIO.read(new File("znak"+nrZnaku+".png"));
        } catch (IOException e) {
            s = "nie wczytano znaku";
        }

        Font fd = new Font("Calibri", Font.BOLD, 60);
        g2.setFont(fd);
        g2.drawImage(znak, 900, 100, null);
        if (timeCounter < timeLimit)
        g2.drawString(Integer.toString((timeLimit - timeCounter) / 100), 1000, 600);
        else if(timeCounter == timeLimit) g2.drawString(komunikat, 950,600);
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

    public int getLevel() {
        return level;
    }

    public int getPredkosc() {
        return predkosc;
    }

    public int getPunkty() {
        return punkty;
    }
}

