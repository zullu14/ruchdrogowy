package gra;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class Okno extends JFrame {

    final private int window_H = 1280;
    final private int window_W = 1024; // odniesienie dla wszystkich wymiarów
    public int eventOperator = 0;  // zmienna ta bedzie ustawiana w zaleznosci od zdarzeń


    Okno(String nazwa){
        super(nazwa);
        setResizable(false);
        setLayout(new BorderLayout());

        add(new Plansza(), BorderLayout.CENTER);
        JPanel menubar =new JPanel();
        menubar.setLayout(new BoxLayout(menubar, BoxLayout.LINE_AXIS));
        menubar.add(Box.createHorizontalGlue());
        menubar.add(new MenuButton("MENU"));
        add(menubar, BorderLayout.PAGE_END);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.out.println("GAME OVER");
            }
        });


        setSize(window_H,window_W);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class MenuButton extends JButton {
        MenuButton(String nazwa) {
            super(nazwa);
            setMinimumSize(new Dimension(200, 100));
            setPreferredSize(new Dimension(200, 100));
            setMaximumSize(new Dimension(200,100));

            setAlignmentX(Component.RIGHT_ALIGNMENT);
            setAlignmentY(Component.BOTTOM_ALIGNMENT);
        }
    }
}
