package gra;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Okno extends JFrame {

    Okno(String nazwa){
        super(nazwa);
        setResizable(false);
       // setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.out.println("GAME OVER");
            }
        });

        setSize(1280,1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
