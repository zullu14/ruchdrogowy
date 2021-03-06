package gra;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;


/**
 * Klasa, która steruje rozgrywką: rysuje pole gry, obsługuje akcje gracza, losuje nowe zdarzenia,
 * podlicza punkty, wywołuje kolejne poziomy.
 */
public class Plansza extends Canvas {

    private String s=null;
    private String komunikat = "start";
    private int pas = 2;
    private int predkosc = 20;
    private int punkty = 0;
    private int level = 1;
    private int eventCounter = 0;
    private int nrZnaku = new Znak().losujZnak();
    private int timeCounter = 0;
    private int timeLimit = 500;
    private int wysKrzaka = 0;
    private boolean isNewLevel = false;
    private boolean isLastLevel = false;
    private boolean isWrong = false;

    private BufferedImage background = null;
    private BufferedImage background2 = null;
    private BufferedImage autko = null;
    private BufferedImage krzak = null;
    private BufferedImage znak = null;

    private boolean repaintInProgress = false;

    /* Swing Timer */
    /**
     * Timer wywołujący zdarzenie co 10 ms. Na podstawie aktualnej sytuacji gry ustawia
     * odpowiednie flagi, zmienne i losuje nowe zdarzenia.
     */
    private Timer timer = new Timer(10, actionEvent -> {
        /* Jezeli komunikat o nowym poziomie jest aktywny, nic nie rób */
        if(isNewLevel) {
            timeCounter++;
            myRepaint();
        }
        /* Jezeli komunikat o popełnieniu błędu jest aktywny, nic nie rób */
        else if(isWrong) {
            timeCounter++;
            myRepaint();
        }
        else if(predkosc != 0 || nrZnaku == 5) { //autko jedzie albo jest znak STOP
            s = null;
            timeCounter++;
            if (timeCounter % 100 == 0) {  // co 1 sekundę
                predkosc--;
                if (predkosc < 0) predkosc = 0;
                punkty += (predkosc / 10); // bonus punktowy

                /* obsługa przejścia na wyższe poziomy */
                switch(level) {
                    case 1:
                        if(eventCounter >= 6) {
                            secondLevel();
                            komunikat = "Brawo!";
                        }
                        break;
                    case 2:
                        if(eventCounter >= 8) {
                            thirdLevel();
                            komunikat = "Brawo!";
                        }
                        break;
                    case 3:
                        if(eventCounter >= 10) {
                            isNewLevel = true;
                            isLastLevel = true;
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
                eventCounter++;
                if (new Znak().sprawdzZnak(nrZnaku, pas, predkosc)) {
                    punkty += 100;
                    komunikat = "Dobrze!";
                } else {
                    // obsługa popełnienia błędu: trzy szanse / koniec gry?
                    komunikat = "Błąd!";
                    punkty -= 100;
                    if(punkty < 0) punkty = 0;
                    isWrong = true;
                    timeCounter = 0;
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
    });

    /**
     * Konstruktor tworzy nowe pole graficzne gry, wczytuje obrazy,
     * obsługuje zdarzenia naciśnięcia klawiszy strzałek.
     */
    Plansza() {
        super();
        Font f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);
        setIgnoreRepaint(true); // zamiast metody repaint() będzie wykorzystana własna metoda myRepaint()

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


    /**
     * Metoda rysująca pole graficzne gry.
     * Wykorzystano BufferStrategy w celu płynnego zmieniania obrazu.
     */
    private void myRepaint() {
        if(repaintInProgress) return;
        repaintInProgress = true;
        BufferStrategy strategy = getBufferStrategy();
        Graphics g = strategy.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        Dimension wym = this.getSize();

        int autko_w = autko.getWidth(null);
        int autko_h = autko.getHeight(null);

        Font fd = new Font("Calibri", Font.BOLD, 60);
        g2.setFont(fd);

        if(isNewLevel) {
            g2.drawImage(background2, 0, 0, null);
            if (isLastLevel) {
                g2.drawString("BRAWO, ZAKOŃCZYŁEŚ GRĘ", 250, 400); //komunikat specjalny
                g2.drawString("Z WYNIKIEM: "+punkty, 375, 500); //komunikat specjalny
            } else {
                g2.drawString("NOWY POZIOM: "+level, 375, 450); //komunikat specjalny
                if(timeCounter >= 200) {
                    isNewLevel = false;
                    timeCounter = 0;
                }
            }
        } // zmiana poziomu
        else if(isWrong) {
            g2.drawImage(background2, 0, 0, null);
            g2.drawString("BŁĄD!", 500, 400); //komunikat specjalny
            fd = new Font("Calibri", Font.BOLD, 50);
            g2.setFont(fd);
            g2.drawString(new Znak().getKomunikat(nrZnaku), 100, 500); //komunikat specjalny
            if(timeCounter >= 200) {
                isWrong = false;
                timeCounter = timeLimit+1;  //powrót do działania programowego
            }
        }
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

    /** Ustawienie parametrów dla pierwszego poziomu gry. */
    public void firstLevel() {
        this.level = 1;
        this.punkty = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 500; // 5 sekund na reakcję
        this.timer.start();
    }

    /** Ustawienie parametrów dla drugiego poziomu gry. */
    public void secondLevel() {
        this.level = 2;
        this.eventCounter = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 400; // 4 sekundy na reakcję
        isNewLevel = true;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
    }

    /** Ustawienie parametrów dla trzeciego poziomu gry. */
    public void thirdLevel() {
        this.level = 3;
        this.eventCounter = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 300; // 3 sekundy na reakcję
        isNewLevel = true;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
    }

    /** Ustawienie parametrów po zresetowanniu gry z pozycji menu. */
    public void resetLevel() {
        this.level = 1;
        this.punkty = 0;
        this.eventCounter = 0;
        this.pas = 2;
        this.predkosc = 20;
        this.timeLimit = 500; // 5 sekund na reakcję
        isNewLevel = true;
        isLastLevel = false;
        timeCounter = 0;
        myRepaint();
        this.timer.restart();
        nrZnaku = new Znak().losujZnak();
    }

    /** Zatrzymanie Timera po otwarciu okna menu. */
    public void freezeLevel() {
        this.timer.stop();
    }

    /** Wznowienie Timera po zamknięciu okna menu. */
    public void resumeLevel() {
        this.timer.start();
    }

    /** @return przekazanie wartości pola level. */
    public int getLevel() {
        return level;
    }

    /** @return przekazanie wartości pola prędkość. */
    public int getPredkosc() {
        return predkosc;
    }

    /** @return przekazanie wartości pola punkty. */
    public int getPunkty() {
        return punkty;
    }
}

