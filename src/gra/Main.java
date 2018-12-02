package gra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame {

    Main(String nazwa) {
        super(nazwa);
        setSize(1280,1024);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame start = new Main("Ruch Drogowy");

                JButton b_start = new JButton("ROZPOCZNIJ GRĘ");
                start.add(b_start);
                start.setVisible(true);

                Okno o = new Okno("Rozgrywka");
                //Plansza plan1 = new Plansza();
                //o.add(plan1);
                o.setVisible(false);

                b_start.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        start.setVisible(false);
                        o.setVisible(true);


                    } //actionEvent
                }); //actionListener
            } //run
        }); //invokeLater

    }

}

