package gra;

import java.awt.*;
import javax.swing.*;

/**
 * Klasa określająca wygląd okna rozgrywki.
 */
public class Okno extends JFrame {

    final private int window_H = 1280;
    final private int window_W = 1024;

    /**
     * Konstruktor tworzący okno dla pola gry.
     * @param nazwa tytuł okna.
     */
    Okno(String nazwa){
        super(nazwa);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(window_H,window_W);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //Okno()
} //class Okno
