package gra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

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
                JPanel controlPanel = new JPanel();
                controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
                controlPanel.add(Box.createRigidArea(new Dimension(0,350)));
                JButton b_start = new ChoiceButton("ROZPOCZNIJ");
                controlPanel.add(b_start);
                start.add(controlPanel, BorderLayout.CENTER);
                start.setVisible(true);

                Okno o = new Okno("Rozgrywka");
                Plansza poleGry = new Plansza();
                o.add(poleGry, BorderLayout.CENTER);

                JPanel menubar =new JPanel();
                menubar.setLayout(new BoxLayout(menubar, BoxLayout.LINE_AXIS));
                menubar.add(Box.createHorizontalGlue());
                JButton mButton = new MenuButton("MENU");
                menubar.add(mButton);
                o.add(menubar, BorderLayout.PAGE_END);
                o.setVisible(false);

                JFrame oknoMenu = new Main("MENU");
                JPanel menuPanel = new JPanel();
                menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
                JButton resumeB = new ChoiceButton("WRÓĆ DO GRY");
                JButton newGameB = new ChoiceButton("NOWA GRA");
                JButton showStatsB= new ChoiceButton("RANKING");
                JButton finishB = new ChoiceButton("ZAKOŃCZ GRĘ");
                menuPanel.add(Box.createRigidArea(new Dimension(0, 60)));
                menuPanel.add(resumeB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(newGameB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(showStatsB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(finishB);
                oknoMenu.add(menuPanel, BorderLayout.CENTER);

                b_start.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        start.setVisible(false);
                        o.setVisible(true);
                        poleGry.firstLevel();


                    } //actionEvent
                }); //actionListener

                mButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        poleGry.freezeLevel();
                        o.setVisible(false);
                        oknoMenu.setVisible(true);

                    }
                }); //actionListener

                resumeB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        o.setVisible(true);
                        poleGry.resumeLevel();
                    }
                });

                newGameB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        o.setVisible(true);
                        poleGry.resetLevel();
                    }
                });

                showStatsB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        // dodać obsługę wyświetlania wyników
                    }
                });

                finishB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        System.exit(0);
                    }
                });

            } //run
        }); //invokeLater

    } //main

} //class Main

