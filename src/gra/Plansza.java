package gra;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;


public class Plansza extends Canvas {

    private String s=null;
    private String komunikat = "start";
    private int pas = 2;
    private int predkosc = 20;
    private int punkty = 0;
    private int level = 1;
    private int nrZnaku = new Znak().losujZnak();
    private int timeCounter = 0;
    private int timeLimit = 500;
    private int wysKrzaka = 0;
    private boolean isNewLevel = false;

    private BufferedImage background = null;
    private BufferedImage background2 = null;
    private BufferedImage autko = null;
    private BufferedImage krzak = null;
    private BufferedImage znak = null;

    private boolean repaintInProgress = false; // TESTOWO


    /* Swing Timer */
    private Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(isNewLevel) {
                timeCounter++;
                myRepaint();
            }
            else if(predkosc != 0 || nrZnaku == 5) { //autko jedzie albo jest znak STOP
                s = null;
                timeCounter++;
                if (timeCounter % 100 == 0) {  // co 1 sekundę
                    predkosc--;
                    if (predkosc < 0) predkosc = 0;
                    punkty += (predkosc / 10); // bonusik punktowy

                    switch(level) {   // obsługa przejścia na wyższe poziomy /// DO POPRAWY
                        case 1:
                            if(punkty >= 100) {
                                secondLevel();
                                komunikat = "Brawo!";
                            }
                            break;
                        case 2:
                            if(punkty >= 100) {
                                thirdLevel();
                                komunikat = "Brawo!";
                            }
                            break;
                        case 3:
                            if(punkty >= 100) {
                                isNewLevel = true;
                                level = 4;
                                myRepaint();
                            }
                            break;
                        default:
                            break;
                    } //switch level

                    myRepaint();
                } // co 1 sekundę

                if (timeCounter == timeLimit) {
                    // obsługa sprawdzenia poprawnej reakcji na znak
                    if (new Znak().sprawdzZnak(nrZnaku, pas, predkosc)) {
                        punkty += 100;
                        komunikat = "Dobrze!";
                    } else {
                        // obsługa popełnienia błędu: trzy szanse / koniec gry?
                        komunikat = "Błąd!";
                        punkty -= 100;
                        if(punkty < 0) punkty = 0;
                    }
                    myRepaint();
                } // warunek tC >= tL
                else if (timeCounter - timeLimit == 100) { // jedna sekunda przerwy między znakami
                    nrZnaku = new Znak().losujZnak();
                    timeCounter = 0;
                    myRepaint();
                }
                if (timeCounter % 5 == 0) {   // co 50 ms
                    if (predkosc > 0) {
                        wysKrzaka += 3;
                    }
                    if (predkosc > 30) {
                        wysKrzaka += 3;
                    }
                    if (predkosc > 60) {
                        wysKrzaka += 3;
                    }
                    if (predkosc > 100) {
                        wysKrzaka += 3;
                    }
                    if (predkosc > 130) {
                        wysKrzaka += 3;
                    }
                    myRepaint();
                    if (wysKrzaka >= 900) wysKrzaka = 0;
                }// koniec akcji co 50 ms
            } // if predkosc != 0
            else {
                s = "Dodaj gazu!";
                myRepaint();
            }
        } // time action event
    });

    Plansza() {
        super();
        Font f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);
        setIgnoreRepaint(true);

        /*   WCZYTANIE OBRAZÓW   */

        /* Wczytanie obrazu tła */
        try {
            background = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            s = "nie wczytano tła";
        }

        /* Wczytanie obrazu tła 2 */
        try {
            background2 = ImageIO.read(new File("background2.jpg"));
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                //isNewLevel = false;
                if(ke.getKeyCode() == 37) {    // lewa strzałka
                    pas--;
                    if (pas < 0) pas = 0;
                }
                else if(ke.getKeyCode() == 39) {  // prawa strzałka
                    pas++;
                    if (pas > 2) pas = 2;
                }
                else if(ke.getKeyCode() == 38) { // strzałka w górę
                    predkosc++;
                    if (predkosc > 160) predkosc = 160;
                }
                else if(ke.getKeyCode() == 40) { //strzałka w dół
                    predkosc--;
                    if (predkosc < 0) predkosc = 0;
                }
                myRepaint();
            }
        });
    } //Plansza()


    public void myRepaint() {   ///
        if(repaintInProgress) return;   ///
        repaintInProgress = true;  ///
        BufferStrategy strategy = getBufferStrategy();  ///
        Graphics g = strategy.getDrawGraphics();  ///

        Graphics2D g2 = (Graphics2D) g;
        Dimension wym = this.getSize();

        int autko_w = autko.getWidth(null);
        int autko_h = autko.getHeight(null);

        Font fd = new Font("Calibri", Font.BOLD, 60);
        g2.setFont(fd);

        if(isNewLevel) {
            g2.drawImage(background2, 0, 0, null);
            if(level < 4) {
                g2.drawString("NOWY POZIOM: "+level, 375, 450); //komunikat specjalny
                if(timeCounter >= 200) {
                    isNewLevel = false;
                    timeCounter = 0;
                }
            }
            else {
                g2.drawString("BRAWO, ZAKOŃCZYŁEŚ GRĘ", 250, 400); //komunikat specjalny
                g2.drawString("Z WYNIKIEM: "+punkty, 375, 500); //komunikat specjalny
            }
        } // zmiana poziomu
        else {
            g2.drawImage(background, 0, 0, null);
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

            g2.drawImage(znak, 900, 100, null);
            if (timeCounter < timeLimit)
                g2.drawString(Integer.toString((timeLimit - timeCounter) / 100), 1000, 600);
            else if(timeCounter >= timeLimit) g2.drawString(komunikat, 950,600);
            if(s != null) g2.drawString(s, 950, 700); //komunikat specjalny
        } // gdy gra działą

        if(g != null) g.dispose();  ///
        strategy.show();  ///
        Toolkit.getDefaultToolkit().sync(); ///
        repaintInProgress = false;  ///
    } //myRepaint

    public void firstLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 500; // 5 sekund na reakcję
        this.timer.start();
    }

    public void secondLevel() {
        this.level = 2;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 400; // 4 sekundy na reakcję
        isNewLevel = true;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
    }

    public void thirdLevel() {
        this.level = 3;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 300; // 3 sekundy na reakcję
        isNewLevel = true;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
    }

    public void resetLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 500; // 5 sekund na reakcję
        isNewLevel = true;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
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

