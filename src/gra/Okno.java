package gra;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Okno extends JFrame {

    final private int window_H = 1280;
    final private int window_W = 1024; // odniesienie dla wszystkich wymiarów


    Okno(String nazwa){
        super(nazwa);
        setResizable(false);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.out.println("GAME OVER");
            }
        });
        setSize(window_H,window_W);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //Okno()

} //class Okno
